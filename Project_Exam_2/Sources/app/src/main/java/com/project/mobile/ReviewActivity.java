package com.project.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewActivity extends AppCompatActivity {
    static Place place;
    static ReviewActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        instance = this;

        Intent placeIntent = getIntent();
        String placeID = placeIntent.getStringExtra("PlaceID");
        String placeName = placeIntent.getStringExtra("PlaceName");
        String placeAddress = placeIntent.getStringExtra("PlaceAddress");
        String placeTypes = placeIntent.getStringExtra("PlaceTypes");
        place = new Place(placeID, placeName, placeTypes, placeAddress);

        QueryUtils.getInstance().getPlaceDetails(placeID,
                getResources().getString(R.string.google_ip_key));
    }

    public void favoritePlace(View view) {
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("favorites");
        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mFirebaseDatabase.child(Uid).push().setValue(place);
    }

    static void setupActivity(String open, String address, String phone, String website, String rating) {
        ((TextView)instance.findViewById(R.id.reviewTitle)).setText(place.getName());
        ((TextView)instance.findViewById(R.id.reviewSite)).setText(website);
        ((TextView)instance.findViewById(R.id.reviewTypes)).setText(place.getTypes());
        ((TextView)instance.findViewById(R.id.reviewRating)).setText(String.valueOf(rating));
        ((TextView)instance.findViewById(R.id.reviewOpen)).setText(open);
        ((TextView)instance.findViewById(R.id.reviewAddress)).setText(address);
        ((TextView)instance.findViewById(R.id.reviewPhone)).setText(phone);
    }
}