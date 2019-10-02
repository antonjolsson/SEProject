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
        }
        else if (focusedLocationFields.peek() == ORIGIN)
            origin.setValue(tripLocation);
        else destination.setValue(tripLocation);
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
