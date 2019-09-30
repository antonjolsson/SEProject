package com.example.tripplannr.trip;

import android.graphics.Point;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.tripplannr.model.Location;
import com.example.tripplannr.model.ModeOfTransport;
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
        buildFakeTrip();
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

    private void buildFakeTrip() {
        Route route1 = new Route.Builder()
                .origin(new Location("Chalmers", new Point()))
                .destination(new Location("Hjalmar", new Point()))
                .mode(ModeOfTransport.BUS)
                .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30)))
                .build();

        Route route2 = new Route.Builder()
                .origin(new Location("Hjalmar", new Point()))
                .destination(new Location("Lindholmen", new Point()))
                .mode(ModeOfTransport.TRAM)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(30), LocalDateTime.now().plusMinutes(60)))
                .build();

        Route route3 = new Route.Builder()
                .origin(new Location("Lindholmen", new Point()))
                .destination(new Location("Lindholmspiren", new Point()))
                .mode(ModeOfTransport.WALK)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(60), LocalDateTime.now().plusMinutes(65)))
                .build();

        Route route4 = new Route.Builder()
                .origin(new Location("Göteborg", new Point()))
                .destination(new Location("Fredrikshavn", new Point()))
                .mode(ModeOfTransport.FERRY)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(60), LocalDateTime.now().plusMinutes(65)))
                .build();

        mTripsLiveData.setValue(new ArrayList<>(Arrays.asList(
                new Trip.Builder()
                        .name("Chalmers, Lindholmspiren")
                        .origin(new Location("Chalmers", new Point()))
                        .destination(new Location("Lindholmspiren", new Point()))
                        .routes(new ArrayList<Route>(Arrays.asList(route1, route2, route3)))
                        .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusMinutes(65)))
                        .build()
        , new Trip.Builder()
                        .name("Göteborg, Fredrikshavn")
                        .origin(new Location("Göteborg", new Point()))
                        .destination(new Location("Fredrikshavn", new Point()))
                        .routes(new ArrayList<Route>(Arrays.asList(route4)))
                        .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusMinutes(65)))
                        .build())
        ));
    }

}
