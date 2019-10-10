package com.example.tripplannr.view.map;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.model.Trip;
import com.example.tripplannr.model.tripdata.Route;
import com.example.tripplannr.viewmodel.TripResultViewModel;

import java.util.List;
import java.util.Objects;

public class ResultMapFragment extends MapFragment {

    private TripResultViewModel tripResultViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripResultViewModel = ViewModelProviders.
                of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
    }

    @Override
    void setListeners() {
        tripResultViewModel.getTripLiveData().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                drawTrip(trip.getRoutes());
            }
        });
    }

    private void drawTrip(List<Route> routes) {

    }

}
