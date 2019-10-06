package com.example.tripplannr.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.model.TripLocation;
import com.example.tripplannr.model.TripViewModel;
import com.example.tripplannr.model.TripViewModel.LocationField;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static com.example.tripplannr.model.TripViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.model.TripViewModel.LocationField.ORIGIN;

public class SearchFragment extends Fragment {

    private EditText toTextField, fromTextField;
    private ImageView locIconView, swapIconView;
    private Button timeButton;
    private String name;
    private TripViewModel model;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).
                get(TripViewModel.class);
        setModelObservers();
    }

    private void setModelObservers() {
        model.getOrigin().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                if (tripLocation == null) return;
                name = formatLocationName(tripLocation.getName());
                fromTextField.setText(name);
            }
        });
        model.getDestination().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                if (tripLocation == null) return;
                name = formatLocationName(tripLocation.getName());
                toTextField.setText(name);
            }
        });
        model.getDesiredTime().observe(this, new Observer<Calendar>() {
            @Override
            public void onChanged(Calendar calendar) {
                setTimeButtonText(calendar);
            }
        });
    }

    private void setTimeButtonText(Calendar calendar) {
        String dateString;
        Calendar today = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        if (today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && today.get(Calendar.MONTH) ==
            calendar.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) ==
                calendar.get(Calendar.DAY_OF_MONTH))
            dateString = "today";
        else if (tomorrow.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                tomorrow.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                tomorrow.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))
            dateString = "tomorrow";
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.getDefault());
            dateString = dateFormat.format(calendar.getTime());
        }
        String timeText = Objects.requireNonNull(model.getTimeIsDeparture().getValue()) ? "DEP. " :
                "ARR. ";
        timeText += dateString + ", " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                calendar.get(Calendar.MINUTE);
        timeButton.setText(timeText);
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

        initControls(view);
        setControlListeners();
        return view;
    }

    private void initControls(View view) {
        toTextField = Objects.requireNonNull(view).findViewById(R.id.toText);
        fromTextField = Objects.requireNonNull(view).findViewById(R.id.fromText);
        locIconView = Objects.requireNonNull(view).findViewById(R.id.locationIconView);
        swapIconView = Objects.requireNonNull(view).findViewById(R.id.swapIconView);
        timeButton = Objects.requireNonNull(view).findViewById(R.id.timeButton);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setControlListeners() {
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
        locIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getCurrTripLocation();
            }
        });
        swapIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapLocations();
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.showTimeControls();
            }
        });
    }

    private void swapLocations() {
        TripLocation origin = model.getOrigin().getValue();
        TripLocation destination = model.getDestination().getValue();
        LocationField locationField = model.getFocusedLocationField();
        model.setFocusedLocationField(DESTINATION);
        if (origin != null) model.setLocation(origin.getLocation(), origin.getName());
        else {
            model.setLocation(null, null);
            toTextField.setText("TO");
        }
        model.setFocusedLocationField(ORIGIN);
        if (destination != null)
            model.setLocation(destination.getLocation(), destination.getName());
        else {
            model.setLocation(null, null);
            fromTextField.setText("FROM");
        }
        model.setFocusedLocationField(locationField);
    }

}
