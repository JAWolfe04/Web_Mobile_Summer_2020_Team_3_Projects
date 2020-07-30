package com.project.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class favoritesAdaptor extends ArrayAdapter<Place> {
    public favoritesAdaptor(@NonNull Context context, List<Place> places) {
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

        TextView placeView = listItemView.findViewById(R.id.placeName);
        placeView.setText(currentPlace.getName());

        return listItemView;
    }
}
