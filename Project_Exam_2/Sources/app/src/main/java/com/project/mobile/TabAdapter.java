package com.project.mobile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public TabAdapter(Context context, @NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PlacesMap mapFragment = new PlacesMap();
                return mapFragment;
            case 1:
                PlacesList placesFragment = new PlacesList();
                return placesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.totalTabs;
    }
}
