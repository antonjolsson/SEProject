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
import androidx.navigation.Navigation;

import com.example.tripplannr.R;
import com.example.tripplannr.model.api.VasttrafikApi;
import com.example.tripplannr.model.api.VasttrafikRepository;
import com.example.tripplannr.model.tripdata.TripLocation;
import com.example.tripplannr.viewmodel.TripViewModel;
import com.example.tripplannr.viewmodel.TripViewModel.LocationField;
import com.example.tripplannr.model.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.ORIGIN;

public class SearchFragment extends Fragment {

    private EditText toTextField, fromTextField;
    private TextView nowTextView;
    private ImageView locIconView, swapIconView;
    private Button timeButton, searchButton;
    private String name;
    private TripViewModel model;
    private VasttrafikRepository vasttrafikRepository = new VasttrafikRepository();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO remove test call
        getMatching("berg");
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
                Navigation.findNavController(v).navigate(R.id.action_navigation_search_to_navigation_trip_results);
            }
        });
        nowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setTime(Calendar.getInstance(),
                        Objects.requireNonNull(model.getTimeIsDeparture().getValue()));
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

    private void getMatching(final String pattern) {
        vasttrafikRepository
                .getVasttrafikService()
                .getToken("Basic ajUyMVJTb3BVVXFIVlR5X0VqOGl1TWRsWXBnYTpzNV9ncUZZR0p2b2pydjhRb2NfNDRVcGpWYm9h",
                        "application/x-www-form-urlencoded", "client_credentials")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println(response.code());
                        System.out.println(response.body());
                        try {
                            sendPatternRequest(new JSONObject(response.body().string()).getString("access_token"), pattern);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }

    private void sendPatternRequest(String token, String pattern) {
        vasttrafikRepository
                .getVasttrafikService()
                .getName(pattern, "json","Bearer " + token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Thread.sleep(2000);
                            if(response.code() >= 200 && response.code() <= 299) {
                                String body = response.body().string();
                                // TODO do something with response
                                List<TripLocation> matches = new VasttrafikApi().getMatching(body);
                            }
                        } catch (IOException | InterruptedException ignored) {} catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }

}
