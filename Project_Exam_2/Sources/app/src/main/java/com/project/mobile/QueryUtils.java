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

}
