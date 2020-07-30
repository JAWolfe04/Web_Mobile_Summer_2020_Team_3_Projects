package com.project.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    favoritesAdaptor mAdapter;
    List<Place> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ListView favoritesListView = findViewById(R.id.favoriteList);
        mAdapter = new favoritesAdaptor(this, new ArrayList<Place>());
        favoritesListView.setAdapter(mAdapter);

        favoritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Place currentPlace = mAdapter.getItem(position);

                Intent intent = new Intent(view.getContext(), ReviewActivity.class);
                intent.putExtra("PlaceID", currentPlace.getId());
                intent.putExtra("PlaceName", currentPlace.getName());
                intent.putExtra("PlaceAddress", currentPlace.getAddress());
                intent.putExtra("PlaceTypes", currentPlace.getTypes());
                startActivity(intent);
            }
        });

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("favorites");
        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFirebaseDatabase.child(Uid).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                places.clear();
                for(DataSnapshot place : snapshot.getChildren()) {
                    places.add(place.getValue(Place.class));
                }
                mAdapter.clear();
                mAdapter.addAll(places);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}