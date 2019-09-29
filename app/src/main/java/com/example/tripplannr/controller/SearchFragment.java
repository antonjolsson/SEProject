package com.example.tripplannr.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.model.TripLocation;
import com.example.tripplannr.model.TripViewModel;

import java.util.Objects;

import static com.example.tripplannr.model.TripViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.model.TripViewModel.LocationField.ORIGIN;

public class SearchFragment extends Fragment {

    private EditText toTextField, fromTextField;
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

    }

}
