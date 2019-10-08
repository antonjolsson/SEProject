package com.example.tripplannr.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.model.tripdata.TripLocation;
import com.example.tripplannr.viewmodel.TripViewModel;
import com.example.tripplannr.viewmodel.TripViewModel.LocationField;
import com.example.tripplannr.model.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.ORIGIN;

public class SearchFragment extends Fragment {

    private EditText toTextField, fromTextField;
    private TextView nowTextView;
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
        nowTextView = Objects.requireNonNull(view).findViewById(R.id.nowTextView);
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
                if (Utilities.isNow(calendar)) {
                    nowTextView.setVisibility(View.INVISIBLE);
                    nowTextView.setEnabled(false);
                }
                else {
                    nowTextView.setVisibility(View.VISIBLE);
                    nowTextView.setEnabled(true);
                }
            }
        });
    }

    private void setTimeButtonText(Calendar calendar) {
        String timeText = Objects.requireNonNull(model.getTimeIsDeparture().getValue()) ? "Dep. " :
                "Arr. ";
        if (Utilities.isNow(calendar)) timeText += "now";
        else {
            String dateString;
            if (Utilities.isToday(calendar)) dateString = "today";
            else if (Utilities.isTomorrow(calendar)) dateString = "tomorrow";
            else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
                        Locale.getDefault());
                dateString = dateFormat.format(calendar.getTime());
            }
            timeText += dateString + ", " + setNumOfZeroes(calendar.get(Calendar.HOUR_OF_DAY)) + ":" +
                    setNumOfZeroes(calendar.get(Calendar.MINUTE));
        }
        timeButton.setText(timeText);
    }

    // Makes sure time always has format hh:mm
    private String setNumOfZeroes(int time) {
        String timeAsString = String.valueOf(time);
        return timeAsString.length() < 2 ? '0' + timeAsString : timeAsString;
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
            }
        });
        nowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setTime(Calendar.getInstance(),
                        Objects.requireNonNull(model.getTimeIsDeparture().getValue()));
            }
        });
        nowTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewUtilities.setAlphaLevels(v, event);
                return false;
            }
        });
    }

    // Swap origin and destination
    private void swapLocations() {
        TripLocation origin = model.getOrigin().getValue();
        TripLocation destination = model.getDestination().getValue();
        LocationField locationField = model.getFocusedLocationField();
        model.setFocusedLocationField(DESTINATION);
        if (origin != null) model.setLocation(origin.getLocation(), origin.getName());
        else {
            model.setLocation(null, null);
            toTextField.setText("To");
        }
        model.setFocusedLocationField(ORIGIN);
        if (destination != null)
            model.setLocation(destination.getLocation(), destination.getName());
        else {
            model.setLocation(null, null);
            fromTextField.setText("From");
        }
        model.setFocusedLocationField(locationField);
    }

}
