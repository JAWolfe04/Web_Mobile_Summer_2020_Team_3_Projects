package com.project.mobile;

import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.ActivityCompat;
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

public class QueryUtils extends ActivityCompat {
    String LOG_TAG = QueryUtils.class.getSimpleName();
    GoogleMap map;

    private QueryUtils() {}

    private static class Singleton {
        private static final QueryUtils instance = new QueryUtils();
    }

    public static QueryUtils getInstance() {
        return Singleton.instance;
    }

    public void getPlaces(String searchTerm, double lat, double lng, String key, GoogleMap map) {
        String query = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                lat + "," + lng + "&rankby=distance&type=address&keyword=" + searchTerm + "&key=" + key;
        this.map = map;
        PlacesAsyncTask asyncTask = new PlacesAsyncTask();
        asyncTask.execute(query);
    }

    public PlaceDetails getPlaceDetails(String ID) {
        PlaceDetails details = new PlaceDetails();

        try{
        } catch (JsonIOException e) {
            Log.e(LOG_TAG, "JSON Exception:  ", e);
        }

        return details;
    }

    public List<Review> getReviews() {
        List<Review> reviews = new ArrayList<>();

        try{
        } catch (JsonIOException e) {
            Log.e(LOG_TAG, "JSON Exception:  ", e);
        }

        return reviews;
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

        Log.e(LOG_TAG, query);
        return queryResult;
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
                Log.e(LOG_TAG, queryResult);
                JSONObject jsonObject = new JSONObject(queryResult);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for(int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject result = jsonArray.getJSONObject(i);
                    JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                    places.add(new Place(
                            result.getString("id"),
                            location.getDouble("lat"),
                            location.getDouble("lng"),
                            result.getString("name"),
                            result.getJSONArray("types").toString()));

                    map.addMarker(new MarkerOptions().position(new LatLng(
                            location.getDouble("lat"),
                            location.getDouble("lng"))));
                }
            } catch (JsonIOException | JSONException e) {
                Log.e(LOG_TAG, "JSON Exception:  ", e);
            }
        }
    }
}
