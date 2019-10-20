package com.example.tripplannr.application_layer.trip;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.data_access_layer.repositories.TripRepository;
import com.example.tripplannr.data_access_layer.repositories.VasttrafikRepository;
import com.example.tripplannr.domain_layer.Route;
import com.example.tripplannr.domain_layer.Trip;

import java.util.List;

public class TripResultViewModel extends ViewModel implements IClickHandler<Trip> {

    private LiveData<List<Trip>> mTripsLiveData;

    private LiveData<Boolean> isLoading;

    private LiveData<Integer> statusCode;

    private MutableLiveData<Trip> mTripLiveData = new MutableLiveData<>();
    private MutableLiveData<Route> mRouteLiveData = new MutableLiveData<>();

    private TripRepository tripRepository;

    public TripResultViewModel(TripRepository tripRepository, VasttrafikRepository vasttrafikRepository) {
        super();
        this.tripRepository = tripRepository;
        isLoading = vasttrafikRepository.isLoading();
        mTripsLiveData = vasttrafikRepository.getData();
        statusCode = vasttrafikRepository.getStatusCode();
    }

    public MutableLiveData<Trip> getTripLiveData() {
        return mTripLiveData;
    }

    public LiveData<List<Trip>> getTripsLiveData() {
        return mTripsLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    @Override
    public void onClick(Trip trip) {
        mTripLiveData.setValue(trip);
    }

    public void saveTrip(Trip trip) {
        System.out.println(trip.getRoutes().get(0).getTimes().getArrival());
        tripRepository.save(trip);
    }

    public List<Trip> getSavedTrips() {
        return tripRepository.findAll();
    }

    public void removeTrip(Trip trip) {
        tripRepository.delete(trip);
    }

    public LiveData<Route> getRouteLiveData() {
        return mRouteLiveData;
    }

    public void updateRoute(Route route) {
        mRouteLiveData.setValue(route);
    }

}
