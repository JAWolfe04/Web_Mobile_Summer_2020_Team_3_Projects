package com.project.mobile;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class PlacesMap extends Fragment {
    View rootView;
    MapView mMapView;
    static GoogleMap map;
    static double latitude = 0, longitude = 0;

    public PlacesMap() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                final LocationManager userCurrentLocation = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                LocationListener userCurrentLocationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) { }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) { }

                    @Override
                    public void onProviderEnabled(String s) { }

                    @Override
                    public void onProviderDisabled(String s) { }
                };
                LatLng userCurrentLocationCorodinates = null;
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat
                        .checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    //show message or ask permissions from the user.
                    return;
                }

                //Getting the current location of the user.
                userCurrentLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                        userCurrentLocationListener);
                Location location = userCurrentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                userCurrentLocationCorodinates = new LatLng(latitude, longitude);

                //Setting the zoom level of the map.
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(userCurrentLocationCorodinates, 11));
            }
        });

        return rootView;
    }

    public void searchMap(String term, String key) {
        QueryUtils.getInstance().getPlaces(term, latitude, longitude, key, map);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
