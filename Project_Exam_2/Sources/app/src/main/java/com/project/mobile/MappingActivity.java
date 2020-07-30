package com.project.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Locale;

public class MappingActivity extends AppCompatActivity {
    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;
    TabAdapter adapter;
    EditText searchBox;
    ImageButton micButton;
    boolean micToggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);

        micButton = findViewById(R.id.mic);
        searchBox = findViewById(R.id.searchBox);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                micButton.getBackground().setColorFilter(getResources().getColor(R.color.micActive),
                        PorterDuff.Mode.SRC_ATOP);
                searchBox.setText("");
                searchBox.setHint("Listening...");
                micToggle = true;
            }

            @Override
            public void onBeginningOfSpeech() { }

            @Override
            public void onRmsChanged(float v) { }

            @Override
            public void onBufferReceived(byte[] bytes) { }

            @Override
            public void onEndOfSpeech() { }

            @Override
            public void onError(int i) {
                micButton.getBackground().setColorFilter(getResources().getColor(R.color.micInactive),
                        PorterDuff.Mode.SRC_ATOP);
                searchBox.setHint("Search...");
                micToggle = false;
                Toast.makeText(MappingActivity.this, "Unable to capture voice. Error: " + i,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle bundle) {
                micButton.getBackground().setColorFilter(getResources().getColor(R.color.micInactive),
                        PorterDuff.Mode.SRC_ATOP);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                searchBox.setHint("Search...");
                searchBox.setText(data.get(0));
                micToggle = false;
            }

            @Override
            public void onPartialResults(Bundle bundle) { }

            @Override
            public void onEvent(int i, Bundle bundle) { }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("Results"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new TabAdapter(this,getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void getVoiceInput(View view) {
        if(micToggle) {
            speechRecognizer.stopListening();
        } else {
            speechRecognizer.startListening(speechRecognizerIntent);
        }
    }

    public void search(View view) {
        EditText searchBox = findViewById(R.id.searchBox);
        PlacesMap placesMapFrag = (PlacesMap) adapter.getItem(0);
        PlacesList placesListFrag = (PlacesList) adapter.getItem(1);

        QueryUtils.getInstance().getPlaces(searchBox.getText().toString(),
                placesMapFrag.latitude, placesMapFrag.longitude,
                getResources().getString(R.string.google_ip_key),
                placesMapFrag.map, placesListFrag.mAdapter);
    }

    public void favorites(View view) {
        startActivity(new Intent(this, FavoriteActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
    }
}