package com.example.tripplannr.application_layer.search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.tripplannr.R;
import com.example.tripplannr.application_layer.util.InjectorUtils;
import com.example.tripplannr.domain_layer.TripLocation;
import com.example.tripplannr.application_layer.search.SearchViewModel.LocationField;
import com.example.tripplannr.application_layer.util.Utilities;
import com.example.tripplannr.data_access_layer.repositories.VasttrafikRepository;
import com.example.tripplannr.domain_layer.TripLocation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.tripplannr.application_layer.search.SearchViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.application_layer.search.SearchViewModel.LocationField.ORIGIN;

public class SearchFragment extends Fragment {

    private AutoCompleteTextView toTextField, fromTextField;
    private TextView nowTextView;
    private ImageView locIconView, swapIconView;
    private Button timeButton, searchButton;
    private String name;
    private SearchViewModel searchViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchViewModel = InjectorUtils.getSearchViewModel(getContext(), getActivity());
        setObservers();
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

    private void setObservers() {
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
        searchViewModel.getAddressMatches().observe(this, new Observer<List<TripLocation>>() {
            @Override
            public void onChanged(List<TripLocation> tripLocations) {

                showAddressSuggestions(tripLocations);
            }
        });
    }

    private void showAddressSuggestions(List<TripLocation> tripLocations) {
        String[] addresses = new String[tripLocations.size()];
        for (int i = 0; i < tripLocations.size(); i++) {
            TripLocation location = tripLocations.get(i);
            addresses[i] = location.getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_dropdown_item_1line, addresses);
        if (searchViewModel.getFocusedLocationField() == ORIGIN)
            fromTextField.setAdapter(adapter);
        else toTextField.setAdapter(adapter);
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
            timeText += dateString + ", " + String.format(Locale.getDefault(), "%02d:%02d",
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
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
        setToFieldListeners();
        setFromFieldListeners();
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
                if (validateForm()) {
                    ((InputMethodManager) Objects.requireNonNull(Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE)))
                            .hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    searchViewModel.setTime(Calendar.getInstance(),
                            Objects.requireNonNull(searchViewModel.getTimeIsDeparture().getValue()));
                    searchViewModel.obtainTrips(fromTextField.getText().toString(), toTextField.getText().toString());
                    Navigation.findNavController(v).navigate(R.id.action_navigation_search_to_navigation_trip_results);
                }
            }
        });
        nowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewModel.setTime(Calendar.getInstance(),
                        Objects.requireNonNull(searchViewModel.getTimeIsDeparture().getValue()));
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

    private boolean validateForm() {
        return !toTextField.getText().toString().isEmpty() && !fromTextField.getText().toString().isEmpty();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setFromFieldListeners() {
        fromTextField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchViewModel.setFocusedLocationField(ORIGIN);
                return false;
            }
        });
        fromTextField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) return setLocationOnEnter(fromTextField);
                else {
                    //vasttrafikRepository.getMatching(fromTextField.getText().toString());
                    searchViewModel.autoComplete(fromTextField.getText().toString());
                    return true;
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setToFieldListeners() {
        toTextField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchViewModel.setFocusedLocationField(DESTINATION);
                return false;
            }
        });
        toTextField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) return setLocationOnEnter(toTextField);
                else {
                   //vasttrafikRepository.getMatching(toTextField.getText().toString());
                    searchViewModel.autoComplete(toTextField.getText().toString());
                    return false;
                }
            }
        });
    }

    private boolean setLocationOnEnter(EditText textField) {
        Location location = getLocation(textField.getText().toString());
        searchViewModel.setLocation(location, textField.getText().toString());
        hideKeyboardFrom(Objects.requireNonNull(getContext()),
                Objects.requireNonNull(getView()));
        return true;
    }

    // TODO: Move this to appropriate class
    private Location getLocation(String address) {
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses;
        Location location = null;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                location = new Location("");
                location.setLatitude(addresses.get(0).getLatitude());
                location.setLongitude(addresses.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    // Swap origin and destination
    private void swapLocations() {
        searchViewModel.setSwappingLocations(true);
        TripLocation origin = searchViewModel.getOrigin().getValue();
        TripLocation destination = searchViewModel.getDestination().getValue();
        LocationField tempField = searchViewModel.getFocusedLocationField();
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
        searchViewModel.setFocusedLocationField(tempField);
        searchViewModel.setSwappingLocations(false);
    }

    private void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
