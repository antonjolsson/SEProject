package com.example.tripplannr.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.model.Trip;
import com.example.tripplannr.viewmodel.TripViewModel;

import java.util.List;
import java.util.Objects;

import static com.example.tripplannr.R.id.main_lower_container;
import static com.example.tripplannr.viewmodel.TripViewModel.ShownFragment.*;

/* Class for displaying various fragments related to the search screen */

public class MainActivity extends FragmentActivity {

    MapFragment mapFragment;
    DateTimeFragment dateTimeFragment;
    ConstraintLayout searchFragView;
    TripViewModel model;
    FrameLayout mainLowerContainer;
    FrameLayout mainUpperContainer;

    private float modFragElevation; // Default elevation for modal/most important fragments
    private float noElevation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        model = ViewModelProviders.of(this).get(TripViewModel.class);
        setListeners();

        searchFragView = findViewById(R.id.search_fragment);

        mainLowerContainer = findViewById(R.id.main_lower_container);
        mainUpperContainer = findViewById(R.id.main_upper_container);

        modFragElevation = getResources().getDimension(R.dimen.modal_fragment_elevation);
        noElevation = getResources().getDimension(R.dimen.no_elevation);

        if (findViewById(main_lower_container) != null) {
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

        // Add the fragment to the 'main_lower_container' FrameLayout
        transaction.replace(main_lower_container, mapFragment).commit();
        transaction.addToBackStack(null);

        searchFragView.setAlpha(ViewUtilities.OPAQUE_ALPHA);
        mainLowerContainer.setElevation(noElevation);
        mainUpperContainer.setElevation(modFragElevation);
        enableDisableViewGroup(searchFragView, true);

    }

    private void showDateTimeFragment() {
        if (dateTimeFragment == null) dateTimeFragment = new DateTimeFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        dateTimeFragment.setArguments(getIntent().getExtras());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Add the fragment to the 'fragment_container' FrameLayout
        transaction.replace(main_lower_container, dateTimeFragment).commit();
        transaction.addToBackStack(null);

        searchFragView.setAlpha(ViewUtilities.SEMI_TRANSPARENT_ALPHA);
        mainUpperContainer.setElevation(noElevation);
        mainLowerContainer.setElevation(modFragElevation);
        enableDisableViewGroup(searchFragView, false);
    }

    private void setListeners() {
        model.getFragments().observe(this, new Observer<TripViewModel.ShownFragment>() {
            @Override
            public void onChanged(TripViewModel.ShownFragment shownFragment) {
                if (shownFragment == MAP) showMapFragment();
                else showDateTimeFragment();
            }
        });
        model.getTrips().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                //TODO Create a ResultListActivity and init it with List<Trip>
            }
        });
    }

    // Enable or disable ViewGroup and all its children
    private void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

}
