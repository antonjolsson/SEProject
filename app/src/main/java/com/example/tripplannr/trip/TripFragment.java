package com.example.tripplannr.trip;

import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplannr.model.Location;
import com.example.tripplannr.model.Route;
import com.example.tripplannr.model.TravelTimes;
import com.example.tripplannr.stdanica.R;
import com.example.tripplannr.model.Trip;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class TripFragment extends Fragment {

    private TripResultViewModel tripResultViewModel;

    private FragmentActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViewModel();
        mainActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        initComponents(view);
        return view;
    }

    private void goBack() {
        tripResultViewModel.getTripsLiveData().getValue().add(
                new Trip("Chalmers, Lindholmen"
                        , new ArrayList<Route>()
                        , new Location("Chakners", new Point(2, 2))
                        , new Location("Lindholmen", new Point(3, 3))
                        , new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusHours(2), 2)));
        Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_navigation_trip_fragment_to_navigation_trip_results);
    }

    private void initComponents(View view) {
        view.findViewById(R.id.testReturnButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goBack();
                    }
                });
    }


    private void initViewModel() {
        tripResultViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
        tripResultViewModel.getTripLiveData().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                onTripUpdated();
            }
        });
    }

    private void onTripUpdated() {

    }

}
