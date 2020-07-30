package com.project.mobile;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    String LOG_TAG = QueryUtils.class.getSimpleName();
    GoogleMap map;
    PlacesAdapter mAdapter;

    private QueryUtils() { }

    private static class Singleton {
        private static final QueryUtils instance = new QueryUtils();
    }

    public static QueryUtils getInstance() {
        return Singleton.instance;
    }

    public void getPlaces(String searchTerm, double lat, double lng, String key, GoogleMap map, PlacesAdapter mAdapter) {
        String query = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                lat + "," + lng + "&rankby=distance&type=address&keyword=" + searchTerm + "&key=" + key;
        this.map = map;
        this.mAdapter = mAdapter;
        PlacesAsyncTask asyncTask = new PlacesAsyncTask();
        asyncTask.execute(query);
    }

    public void getPlaceDetails(String ID, String key) {
        String query = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" +
                ID + "&fields=opening_hours/weekday_text,formatted_address,rating,website," +
                "formatted_phone_number&key=" + key;

        DetailsAsyncTask asyncTask = new DetailsAsyncTask();
        asyncTask.execute(query);
    }

    private String getQuery(String query) {
        HttpURLConnection urlConnection = null;
        String queryResult = "";

        try {
            URL url = new URL(query);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int code = urlConnection.getResponseCode();
            if(code != 200)
                Log.e(LOG_TAG, "Invalid Response:  " + code);

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while((line = reader.readLine()) != null)
                queryResult += line;

        } catch(Exception e) {
            Log.e(LOG_TAG, "Exception:  ", e);
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

        return queryResult;
    }

    private class DetailsAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return getQuery(strings[0]);
        }

        @Override
        protected void onPostExecute(String queryResult) {
            try{
                JSONObject result = new JSONObject(queryResult).getJSONObject("result");
                String times = "";
                JSONArray timesArray = result.getJSONObject("opening_hours")
                        .getJSONArray("weekday_text");
                for(int i = 0; i < timesArray.length(); ++i) {
                        times += timesArray.getString(i) + "\n";
                }
                ReviewActivity.setupActivity(
                        times,
                        result.getString("formatted_address"),
                        result.getString("formatted_phone_number"),
                        result.getString("website"),
                        "Rating: " + result.getString("rating") + "/5"
                );
            } catch (JsonIOException | JSONException e) {
                Log.e(LOG_TAG, "JSON Exception:  ", e);
            }
        }
    }

    private class PlacesAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return getQuery(strings[0]);
        }

        @Override
        protected void onPostExecute(String queryResult) {
            try{
                List<Place> places = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(queryResult);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for(int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject result = jsonArray.getJSONObject(i);
                    JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                    String types ="";
                    JSONArray typesArray = result.getJSONArray("types");
                    for(int j = 0; j < typesArray.length(); ++j) {
                        if(j == 0)
                            types += typesArray.getString(0).replace('_', ' ');
                        else
                            types += ", " + typesArray.getString(j).replace('_', ' ');
                    }
                    places.add(new Place(
                            result.getString("place_id"),
                            result.getString("name"),
                            types,
                            result.getString("vicinity")
                    ));

                    map.addMarker(new MarkerOptions().position(new LatLng(
                            location.getDouble("lat"),
                            location.getDouble("lng"))));
                }
                mAdapter.clear();

                if(places != null && !places.isEmpty())
                    mAdapter.addAll(places);

            } catch (JsonIOException | JSONException e) {
                Log.e(LOG_TAG, "JSON Exception:  ", e);
            }
        }
    }
}
