package com.example.tripplannr.model;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.example.tripplannr.model.TripViewModel.LocationField.*;

public class TripViewModel extends ViewModel {

    public enum LocationField {ORIGIN, DESTINATION}
    private MutableLiveData<TripLocation> origin = new MutableLiveData<>();
    private MutableLiveData<TripLocation> destination = new MutableLiveData<>();
    private MutableLiveData<Boolean> addressQuery = new MutableLiveData<>();
    private Location currLocation;

    private boolean initOriginField = true;

    private Deque<LocationField> focusedLocationFields = new ArrayDeque<>();

    public TripViewModel() {
        focusedLocationFields.push(DESTINATION);
    }

    public void flattenFocLocStack() {
        if (focusedLocationFields.size() > 1) focusedLocationFields.remove();
    }

    public MutableLiveData<Boolean> getAddressQuery() {
        return addressQuery;
    }

    public void setAddressQuery(boolean addressQuery) {
        this.addressQuery.setValue(addressQuery);
    }

    public void getCurrTripLocation() {
        focusedLocationFields.push(ORIGIN);
        addressQuery.setValue(true);
    }

    public void autoSetCurrLocation(Location location) {
        currLocation = location;
    }

    public void setFocusedLocationField(LocationField focusedLocationField) {
        focusedLocationFields.remove();
        focusedLocationFields.push(focusedLocationField);
    }

    public LocationField getFocusedLocationField() {
        return focusedLocationFields.peek();
    }

    public void setLocation(Location location, String name) {
        if (initOriginField) {
            origin.setValue(new TripLocation(name, location));
            initOriginField = false;
        }
        else if (focusedLocationFields.peek() == ORIGIN)
            origin.setValue(new TripLocation(name, location));
        else destination.setValue(new TripLocation(name, location));
    }

    public LiveData<TripLocation> getOrigin() {
        return origin;
    }

    public LiveData<TripLocation> getDestination() {
        return destination;
    }

    public boolean isInitOriginField() {
        return initOriginField;
    }
}
