package com.project.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PlacesList extends Fragment {
    public PlacesList() { }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ListView placesListView = getView().findViewById(R.id.placeList);
        final ListAdapter mAdapter = new PlacesAdapter(getContext(), new ArrayList<Place>());
        placesListView.setAdapter(mAdapter);

        placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Place currentPlace = (Place)mAdapter.getItem(position);

                Intent intent = new Intent(getContext(), ReviewActivity.class);
                intent.putExtra("PlaceID", currentPlace.getId());
                startActivity(intent);
            }
        });

        return inflater.inflate(R.layout.fragment_resultlist, container, false);
    }
}