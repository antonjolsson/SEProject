package com.example.tripplannr.trip;

import android.graphics.Point;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.tripplannr.model.Location;
import com.example.tripplannr.model.Route;
import com.example.tripplannr.model.TravelTimes;
import com.example.tripplannr.model.Trip;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TripResultViewModel extends ViewModel implements IClickHandler<Trip> {

    private MutableLiveData<List<Trip>> mTripsLiveData = new MutableLiveData<>();

    private MutableLiveData<Trip> mTripLiveData = new MutableLiveData<>();

    public TripResultViewModel() {
        super();
        mTripsLiveData.setValue(new ArrayList<>(Arrays.asList(
                new Trip("Chalmers, Lindholmen"
                        , new ArrayList<Route>()
                        , new Location("Chalmers", new Point(2, 2))
                        , new Location("Lindholmen", new Point(3, 3))
                        , new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusHours(2), 2))
                , new Trip("Chalmers, Lindholmen"
                , new ArrayList<Route>()
                , new Location("Chalmers", new Point(2, 2))
                , new Location("Lindholmen", new Point(3, 3))
                , new TravelTimes(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(4), 2))
        )));
    }

    public LiveData<Trip> getTripLiveData() {
        return mTripLiveData;
    }

    public LiveData<List<Trip>> getTripsLiveData() {
        return mTripsLiveData;
    }

    @Override
    public void onClick(Trip trip) {
        mTripLiveData.setValue(trip);
    }
}
