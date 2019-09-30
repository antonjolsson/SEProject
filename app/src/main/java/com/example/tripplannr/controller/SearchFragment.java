package com.example.tripplannr.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.model.TripLocation;
import com.example.tripplannr.model.TripViewModel;
import com.example.tripplannr.model.TripViewModel.LocationField;

import java.util.Objects;

import static com.example.tripplannr.model.TripViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.model.TripViewModel.LocationField.ORIGIN;

public class SearchFragment extends Fragment {

    private EditText toTextField, fromTextField;
    private ImageView locIconView, swapIconView;
    private String name;
    private TripViewModel model;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).
                get(TripViewModel.class);
        model.getOrigin().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                name = formatLocationName(tripLocation.getName());
                fromTextField.setText(name);
                if (model.getAddressQuery().getValue() != null &&
                        model.getAddressQuery().getValue()) {
                    model.setAddressQuery(false);
                    model.flattenFocLocStack();
                }
            }
        });
        model.getDestination().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                name = formatLocationName(tripLocation.getName());
                toTextField.setText(name);
            }
        });
    }

    private String formatLocationName(String name) {
        return name.replaceAll(",(\\s\\d+)+", ",").
                replaceAll(",(\\s\\D+)+,(\\s\\D+)+", ",$1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_frag, container, false);

        toTextField = Objects.requireNonNull(view).findViewById(R.id.toText);
        fromTextField = Objects.requireNonNull(view).findViewById(R.id.fromText);
        locIconView = Objects.requireNonNull(view).findViewById(R.id.locationIconView);
        swapIconView = Objects.requireNonNull(view).findViewById(R.id.swapIconView);
        setListeners();
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {
        toTextField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                model.setFocusedLocationField(DESTINATION);
                return false;
            }
        });
        fromTextField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                model.setFocusedLocationField(ORIGIN);
                return false;
            }
        });
        locIconView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                model.getCurrTripLocation();
                return false;
            }
        });
        swapIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapLocations();
            }
        });
    }

    private void swapLocations() {
        TripLocation origin = model.getOrigin().getValue();
        TripLocation destination = model.getDestination().getValue();
        LocationField locationField = model.getFocusedLocationField();
        model.setFocusedLocationField(DESTINATION);
        assert origin != null;
        model.setLocation(origin.getLocation(), origin.getName());
        model.setFocusedLocationField(ORIGIN);
        assert destination != null;
        model.setLocation(destination.getLocation(), destination.getName());
        model.setFocusedLocationField(locationField);
    }

}
