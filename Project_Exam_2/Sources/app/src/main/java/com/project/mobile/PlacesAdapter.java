package com.project.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PlacesAdapter extends ArrayAdapter<Place> {
    public PlacesAdapter(@NonNull Context context, List<Place> places) {
        super(context, 0, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.fragment_place, parent, false);
        }

        Place currentPlace = getItem(position);

        TextView placeNameView = listItemView.findViewById(R.id.placeName);
        placeNameView.setText(currentPlace.getName());

        TextView placeAddressView = listItemView.findViewById(R.id.placeAddress);
        placeAddressView.setText(currentPlace.getAddress());

        TextView placeTypesView = listItemView.findViewById(R.id.placeTypes);
        placeTypesView.setText(currentPlace.getTypes());

        return listItemView;
    }
}
