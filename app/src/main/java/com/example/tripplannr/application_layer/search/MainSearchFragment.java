package com.example.tripplannr.application_layer.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.application_layer.trip.map.SearchMapFragment;
import com.example.tripplannr.application_layer.util.InjectorUtils;
import com.example.tripplannr.domain_layer.Trip;

import java.util.List;
import java.util.Objects;

import static com.example.tripplannr.R.id.main_lower_container;
import static com.example.tripplannr.application_layer.search.SearchViewModel.ShownFragment.MAP;

/* Class for displaying various fragments related to the search screen */

public class MainSearchFragment extends Fragment {

    private final static float SEMI_TRANSPARENT_ALPHA = 0.5f;
    private final static float OPAQUE_ALPHA = 1f;

    private SearchMapFragment searchMapFragment;
    private DateTimeFragment dateTimeFragment;
    private ConstraintLayout searchFragView;
    private SearchViewModel model;
    private FrameLayout mainLowerContainer;
    private FrameLayout mainUpperContainer;

    private float modFragElevation; // Default elevation for modal/most important fragments
    private float noElevation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        model = InjectorUtils.getSearchViewModel(getContext(), getActivity());
        View view = inflater.inflate(R.layout.main_activity, container, false);
        setListeners();

        searchFragView = view.findViewById(R.id.search_fragment);

        mainLowerContainer = view.findViewById(R.id.main_lower_container);
        mainUpperContainer = view.findViewById(R.id.main_upper_container);

        modFragElevation = getResources().getDimension(R.dimen.modal_fragment_elevation);
        noElevation = getResources().getDimension(R.dimen.no_elevation);
        if (view.findViewById(main_lower_container) != null) {
            if (savedInstanceState == null) {
                showMapFragment();
            }
        }
        return view;
    }

    private void showMapFragment() {
        if (searchMapFragment == null) searchMapFragment = new SearchMapFragment();

        searchMapFragment.setArguments(getActivity().getIntent().getExtras());

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().
                beginTransaction();

        // Add the fragment to the 'fragment_container' FrameLayout
        transaction.replace(main_lower_container, searchMapFragment).commit();
        transaction.addToBackStack(null);

        searchFragView.setAlpha(OPAQUE_ALPHA);
        mainLowerContainer.setElevation(noElevation);
        mainUpperContainer.setElevation(modFragElevation);
        enableDisableViewGroup(searchFragView, true);
    }

    private void showDateTimeFragment() {
        if (dateTimeFragment == null) dateTimeFragment = new DateTimeFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        dateTimeFragment.setArguments(getActivity().getIntent().getExtras());

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        // Add the fragment to the 'fragment_container' FrameLayout
        transaction.replace(main_lower_container, dateTimeFragment).commit();
        transaction.addToBackStack(null);

        searchFragView.setAlpha(SEMI_TRANSPARENT_ALPHA);
        mainUpperContainer.setElevation(noElevation);
        mainLowerContainer.setElevation(modFragElevation);
        enableDisableViewGroup(searchFragView, false);
    }

    private void setListeners() {
        model.getFragments().observe(this, new Observer<SearchViewModel.ShownFragment>() {
            @Override
            public void onChanged(SearchViewModel.ShownFragment shownFragment) {
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
