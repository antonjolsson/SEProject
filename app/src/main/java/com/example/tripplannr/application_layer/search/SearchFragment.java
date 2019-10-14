package com.example.tripplannr.application_layer.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.tripplannr.R;
import com.example.tripplannr.domain_layer.TripLocation;
import com.example.tripplannr.application_layer.search.SearchViewModel.LocationField;
import com.example.tripplannr.application_layer.util.Utilities;
import com.example.tripplannr.data_access_layer.repositories.VasttafikRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static com.example.tripplannr.application_layer.search.SearchViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.application_layer.search.SearchViewModel.LocationField.ORIGIN;

public class SearchFragment extends Fragment {

    private EditText toTextField, fromTextField;
    private TextView nowTextView;
    private ImageView locIconView, swapIconView;
    private Button timeButton, searchButton;
    private String name;
    private SearchViewModel searchViewModel;
    private VasttafikRepository vasttrafikRepository = new VasttafikRepository();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO remove test call
        vasttrafikRepository.getMatching("berg");
        searchViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).
                get(SearchViewModel.class);
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
        searchViewModel.getOrigin().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                if (tripLocation == null) return;
                name = formatLocationName(tripLocation.getName());
                fromTextField.setText(name);
            }
        });
        searchViewModel.getDestination().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                if (tripLocation == null) return;
                name = formatLocationName(tripLocation.getName());
                toTextField.setText(name);
            }
        });
        searchViewModel.getDesiredTime().observe(this, new Observer<Calendar>() {
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
        String timeText = Objects.requireNonNull(searchViewModel.getTimeIsDeparture().getValue()) ? "Dep. " :
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
            timeText += dateString + ", " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                    calendar.get(Calendar.MINUTE);
        }
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
        setListeners();
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {
        toTextField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchViewModel.setFocusedLocationField(DESTINATION);
                return false;
            }
        });
        fromTextField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchViewModel.setFocusedLocationField(ORIGIN);
                return false;
            }
        });
        locIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewModel.getCurrTripLocation();
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
                searchViewModel.showTimeControls();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager) Objects.requireNonNull(Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE)))
                        .hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                searchViewModel.setTime(Calendar.getInstance(),
                        Objects.requireNonNull(searchViewModel.getTimeIsDeparture().getValue()));
                searchViewModel.obtainTrips(fromTextField.getText().toString(), toTextField.getText().toString());
                Navigation.findNavController(v).navigate(R.id.action_navigation_search_to_navigation_trip_results);
            }
        });

    }

    private void swapLocations() {
        TripLocation origin = searchViewModel.getOrigin().getValue();
        TripLocation destination = searchViewModel.getDestination().getValue();
        LocationField locationField = searchViewModel.getFocusedLocationField();
        searchViewModel.setFocusedLocationField(DESTINATION);
        if (origin != null) searchViewModel.setLocation(origin.getLocation(), origin.getName());
        else {
            searchViewModel.setLocation(null, null);
            toTextField.setText("To");
        }
        searchViewModel.setFocusedLocationField(ORIGIN);
        if (destination != null)
            searchViewModel.setLocation(destination.getLocation(), destination.getName());
        else {
            searchViewModel.setLocation(null, null);
            fromTextField.setText("From");
        }
        searchViewModel.setFocusedLocationField(locationField);
    }

}
