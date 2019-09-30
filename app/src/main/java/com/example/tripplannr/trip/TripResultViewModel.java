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
import java.util.Collections;
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
                .origin(new Location("Lillhagens Station", "A", new Point()))
                .destination(new Location("Brunnsparken", "A", new Point()))
                .mode(ModeOfTransport.BUS)
                .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusMinutes(18)))
                .build();
        Route route2 = new Route.Builder()
                .origin(new Location("Brunnsparken", "A", new Point()))
                .destination(new Location("Brunnsparken", "C", new Point()))
                .mode(ModeOfTransport.WALK)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(16), LocalDateTime.now().plusMinutes(16)))
                .build();
        Route route3 = new Route.Builder()
                .origin(new Location("Brunnsparken", "C", new Point()))
                .destination(new Location("Masthuggstorget", "B", new Point()))
                .mode(ModeOfTransport.TRAM)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(21), LocalDateTime.now().plusMinutes(30)))
                .build();
        Route route4 = new Route.Builder()
                .origin(new Location("Masthuggstorget", "B", new Point()))
                .destination(new Location("Danmarksterminalen", "Gate A", new Point()))
                .mode(ModeOfTransport.WALK)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(30), LocalDateTime.now().plusMinutes(32)))
                .build();
        Route route5 = new Route.Builder()
                .origin(new Location("Danmarksterminalen", "Gate A", new Point()))
                .destination(new Location("Fredrikshavn", "Gate B", new Point()))
                .mode(ModeOfTransport.FERRY)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(58), LocalDateTime.now().plusDays(1)))
                .build();
        Trip trip = new Trip.Builder()
                .name("Göteborg, Fredrikshavn")
                .origin(new Location("Lillhagens Station", "A", new Point()))
                .destination(new Location("Fredrikshavn", "Gate B", new Point()))
                .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusDays(1).plusMinutes(58)))
                .routes(Arrays.asList(route1, route2, route3, route4, route5))
                .build();
        mTripsLiveData.setValue(Collections.singletonList(trip));

    }

}
