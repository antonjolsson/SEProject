package com.example.tripplannr.controller.search;

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
import androidx.navigation.Navigation;

import com.example.tripplannr.R;
import com.example.tripplannr.model.TripLocation;
import com.example.tripplannr.model.TripViewModel;
import com.example.tripplannr.model.TripViewModel.LocationField;
import com.example.tripplannr.model.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static com.example.tripplannr.model.TripViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.model.TripViewModel.LocationField.ORIGIN;

public class SearchFragment extends Fragment {

    private EditText toTextField, fromTextField;
    private ImageView locIconView, swapIconView;
    private Button timeButton, searchButton;
    private String name;
    private TripViewModel model;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).
                get(TripViewModel.class);
        setModelObservers();
    }

    private void initControls(View view) {
        toTextField = Objects.requireNonNull(view).findViewById(R.id.toText);
        fromTextField = Objects.requireNonNull(view).findViewById(R.id.fromText);
        locIconView = Objects.requireNonNull(view).findViewById(R.id.locationIconView);
        swapIconView = Objects.requireNonNull(view).findViewById(R.id.swapIconView);
        timeButton = Objects.requireNonNull(view).findViewById(R.id.timeButton);
        searchButton = Objects.requireNonNull(view).findViewById(R.id.searchButton);
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
        if (Utilities.isToday(calendar)) dateString = "today";
        else if (Utilities.isTomorrow(calendar)) dateString = "tomorrow";
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

        toTextField = Objects.requireNonNull(view).findViewById(R.id.toText);
        fromTextField = Objects.requireNonNull(view).findViewById(R.id.fromText);
        locIconView = Objects.requireNonNull(view).findViewById(R.id.locationIconView);
        swapIconView = Objects.requireNonNull(view).findViewById(R.id.swapIconView);
        initControls(view);
        setListeners();
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {
        toTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setFocusedLocationField(DESTINATION);
            }
        });
        fromTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setFocusedLocationField(ORIGIN);
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
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.obtainTrips();
                Navigation.findNavController(v).navigate(R.id.action_navigation_search_to_navigation_trip_results);
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
