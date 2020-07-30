package com.project.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PlacesList extends Fragment {
    View rootView;
    static PlacesAdapter mAdapter;

    public PlacesList() { }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_resultlist, container, false);
        ListView placesListView = rootView.findViewById(R.id.placeList);
        mAdapter = new PlacesAdapter(rootView.getContext(), new ArrayList<Place>());
        placesListView.setAdapter(mAdapter);

        placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Place currentPlace = mAdapter.getItem(position);

                Intent intent = new Intent(getContext(), ReviewActivity.class);
                intent.putExtra("PlaceID", currentPlace.getId());
                intent.putExtra("PlaceName", currentPlace.getName());
                intent.putExtra("PlaceAddress", currentPlace.getAddress());
                intent.putExtra("PlaceTypes", currentPlace.getTypes());
                startActivity(intent);
            }
        });

        return rootView;
    }
}