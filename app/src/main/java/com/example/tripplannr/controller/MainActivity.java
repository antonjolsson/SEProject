package com.example.tripplannr.controller;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.model.TripViewModel;

import static com.example.tripplannr.R.id.main_fragment_container;
import static com.example.tripplannr.model.TripViewModel.ShownFragment.*;

public class MainActivity extends FragmentActivity {

    MapFragment mapFragment;
    DateTimeFragment dateTimeFragment;
    View searchFragment;
    TripViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        model = ViewModelProviders.of(this).get(TripViewModel.class);
        setListeners();

        searchFragment = findViewById(R.id.search_fragment);

        if (findViewById(main_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            showMapFragment();
        }
    }

    private void showMapFragment() {
        if (mapFragment == null) mapFragment = new MapFragment();

        mapFragment.setArguments(getIntent().getExtras());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Add the fragment to the 'fragment_container' FrameLayout
        transaction.replace(main_fragment_container, mapFragment).commit();
        transaction.addToBackStack(null);

        searchFragment.setAlpha(1f);

    }

    private void showDateTimeFragment() {
            if (dateTimeFragment == null) dateTimeFragment = new DateTimeFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            dateTimeFragment.setArguments(getIntent().getExtras());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Add the fragment to the 'fragment_container' FrameLayout
            transaction.replace(main_fragment_container, dateTimeFragment).commit();
            transaction.addToBackStack(null);

            searchFragment.setAlpha(0.5f);
    }

    private void setListeners() {
        model.getFragments().observe(this, new Observer<TripViewModel.ShownFragment>() {
            @Override
            public void onChanged(TripViewModel.ShownFragment shownFragment) {
                if (shownFragment == MAP) showMapFragment();
                else showDateTimeFragment();
            }
        });
    }

}
