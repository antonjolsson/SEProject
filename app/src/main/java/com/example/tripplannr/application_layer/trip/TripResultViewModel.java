package com.example.tripplannr.application_layer.trip;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.data_access_layer.data_sources.TripDAOImpl;
import com.example.tripplannr.data_access_layer.repositories.VasttafikRepository;
import com.example.tripplannr.domain_layer.Route;
import com.example.tripplannr.domain_layer.TravelTimes;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.data_access_layer.data_sources.VasttrafikServiceImpl;
import com.example.tripplannr.domain_layer.TripLocation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.tripplannr.domain_layer.ModeOfTransport.*;

public class TripResultViewModel extends ViewModel implements IClickHandler<Trip> {

    private VasttafikRepository vasttafikRepository = new VasttafikRepository();

    private LiveData<List<Trip>> mTripsLiveData;

    private LiveData<Boolean> isLoading;

    private MutableLiveData<Trip> mTripLiveData = new MutableLiveData<>();
    private MutableLiveData<Route> mRouteLiveData = new MutableLiveData<>();

    private TripDAOImpl tripRepository = TripDAOImpl.getInstance();

    public TripResultViewModel() {
        super();
        isLoading = vasttafikRepository.isLoading();
        mTripsLiveData = vasttafikRepository.getData();
        vasttafikRepository.loadTrips("Skogome", "Frölunda");
    }

    public LiveData<Trip> getTripLiveData() {
        return mTripLiveData;
    }

    public LiveData<List<Trip>> getTripsLiveData() {
        return mTripsLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    @Override
    public void onClick(Trip trip) {
        mTripLiveData.setValue(trip);
    }

    public boolean saveTrip(Trip trip) {
        return tripRepository.save(trip).isPresent();
    }

    public List<Trip> getSavedTrips() {
        return tripRepository.findAll();
    }

    public boolean removeTrip(Trip trip) {
        return tripRepository.delete(trip);
    }


    public void setSelectedRoute(Route route) {
        mRouteLiveData.setValue(route);
    }

    public LiveData<Route> getRouteLiveData() {
        return mRouteLiveData;
    }

}
