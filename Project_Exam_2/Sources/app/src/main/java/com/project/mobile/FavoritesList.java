package com.project.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FavoritesList extends Fragment {
    public FavoritesList() {}

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ListView favoritesListView = getView().findViewById(R.id.favoriteList);
        final ListAdapter mAdapter = new favoritesAdaptor(getContext(), new ArrayList<Place>());
        favoritesListView.setAdapter(mAdapter);

        favoritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Place currentPlace = (Place)mAdapter.getItem(position);

                Intent intent = new Intent(getContext(), ReviewActivity.class);
                intent.putExtra("PlaceID", currentPlace.getId());
                startActivity(intent);
            }
        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Log.e("User", auth.getCurrentUser().toString());
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("users");

        return inflater.inflate(R.layout.fragment_favoritelist, container, false);
    }
}
