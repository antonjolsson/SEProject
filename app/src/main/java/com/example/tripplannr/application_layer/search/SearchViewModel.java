package com.example.tripplannr.application_layer.search;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.application_layer.util.Utilities;
import com.example.tripplannr.data_access_layer.repositories.VasttrafikRepository;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripLocation;
import com.example.tripplannr.domain_layer.TripQuery;

import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Deque;
import java.util.List;

import static com.example.tripplannr.application_layer.search.SearchViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.application_layer.search.SearchViewModel.LocationField.ORIGIN;
import static com.example.tripplannr.application_layer.search.SearchViewModel.ShownFragment.MAP;
import static com.example.tripplannr.application_layer.search.SearchViewModel.ShownFragment.TIME_CONTROLS;

public class SearchViewModel extends ViewModel {

    public enum ShownFragment {MAP, TIME_CONTROLS}

    public enum LocationField {ORIGIN, DESTINATION}

    private final MutableLiveData<TripLocation> origin = new MutableLiveData<>();
    private final MutableLiveData<TripLocation> destination = new MutableLiveData<>();
    // Address is requested by a fragment
    private final MutableLiveData<Boolean> addressQuery = new MutableLiveData<>();
    private final MutableLiveData<Calendar> desiredTime = new MutableLiveData<>();
    private final MutableLiveData<Boolean> timeIsDeparture = new MutableLiveData<>();
    private final MutableLiveData<ShownFragment> fragments = new MutableLiveData<>();
    private final MutableLiveData<List<Trip>> trips = new MutableLiveData<>();
    private final VasttrafikRepository vasttrafikRepository;

    public LiveData<List<TripLocation>> getAddressMatches() {
        return addressMatches;
    }

    private final LiveData<List<TripLocation>> addressMatches;

    // If the app is starting up, set current location as origin
    private boolean initOriginField = true;
    // Keep track of which location field is focused
    private final Deque<LocationField> focusedLocationFields = new ArrayDeque<>();

    private boolean swappingLocations;

    public SearchViewModel(VasttrafikRepository vasttrafikRepository) {
        addressMatches = vasttrafikRepository.getAddressMatches();
        this.vasttrafikRepository = vasttrafikRepository;
        focusedLocationFields.push(DESTINATION);
        timeIsDeparture.setValue(true);
        desiredTime.setValue(Calendar.getInstance());
    }

    void autoComplete(String pattern) {
        vasttrafikRepository.getMatching(pattern);
    }

    public void obtainTrips(String origin, String destination) {
        // TODO, reimplement string search?
        if(this.origin.getValue() == null)
            this.origin.setValue(new TripLocation(origin,new Location("")));
        if(this.destination.getValue() == null)
            this.destination.setValue(new TripLocation(destination, new Location("")));
        vasttrafikRepository.loadTrips(obtainQuery());
    }

    private TripQuery obtainQuery() {
        return new TripQuery.Builder()
                .origin(origin.getValue().getName())
                .destination(destination.getValue().getName())
                .originLocation(origin.getValue().getLocation())
                .destinationLocation(destination.getValue().getLocation())
                .time(Utilities.toLocalDateTime(desiredTime.getValue()))
                .timeIsDeparture(timeIsDeparture.getValue())
                .build();
    }

    public MutableLiveData<ShownFragment> getFragments() {
        return fragments;
    }

    public void setTime(Calendar desiredTime, boolean timeIsDeparture) {
        this.timeIsDeparture.setValue(timeIsDeparture);
        this.desiredTime.setValue(desiredTime);
    }

    public void showTimeControls() {
        fragments.setValue(TIME_CONTROLS);
    }

    public void showMap() {
        fragments.setValue(MAP);
    }

    public void flattenFocLocationStack() {
        while (focusedLocationFields.size() > 1)
            focusedLocationFields.remove();
    }

    public void setAddressQuery(boolean addressQuery) {
        this.addressQuery.setValue(addressQuery);
    }

    public void getCurrTripLocation() {
        focusedLocationFields.push(ORIGIN);
        addressQuery.setValue(true);
    }

    public void setFocusedLocationField(LocationField focusedLocationField) {
        focusedLocationFields.remove();
        focusedLocationFields.push(focusedLocationField);
    }

    public LocationField getFocusedLocationField() {
        return focusedLocationFields.peek();
    }

    public void setLocation(Location location, String name) {
        TripLocation tripLocation = null;
        if (location != null) tripLocation = new TripLocation(name, location, null);
        if (initOriginField) {
            origin.setValue(tripLocation);
            initOriginField = false;
        } else if (focusedLocationFields.peek() == ORIGIN)
            origin.setValue(tripLocation);
        else this.destination.setValue(tripLocation);
    }

    public MutableLiveData<Boolean> getAddressQuery() {
        return addressQuery;
    }

    public LiveData<TripLocation> getOrigin() {
        return origin;
    }

    public LiveData<TripLocation> getDestination() {
        return destination;
    }

    public MutableLiveData<Calendar> getDesiredTime() {
        return desiredTime;
    }

    public MutableLiveData<Boolean> getTimeIsDeparture() {
        return timeIsDeparture;
    }

    public MutableLiveData<List<Trip>> getTrips() {
        return trips;
    }

    public boolean isInitOriginField() {
        return initOriginField;
    }

    public void setInitOriginField(boolean initOriginField) {
        this.initOriginField = initOriginField;
    }

    public void setTempLocationField(LocationField locationField) {
        focusedLocationFields.push(locationField);
    }

    public void setSwappingLocations(boolean b) {
        swappingLocations = b;
    }

    public boolean isSwappingLocations() {
        return swappingLocations;
    }
}
