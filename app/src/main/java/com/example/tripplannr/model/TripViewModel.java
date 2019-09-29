package com.example.tripplannr.model;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.example.tripplannr.model.TripViewModel.LocationField.*;

public class TripViewModel extends ViewModel {

    public enum LocationField {ORIGIN, DESTINATION}
    private MutableLiveData<TripLocation> origin = new MutableLiveData<>();
    private MutableLiveData<TripLocation> destination = new MutableLiveData<>();

    boolean initOriginField = true;

    public void setFocusedLocationField(LocationField focusedLocationField) {
        this.focusedLocationField = focusedLocationField;
    }

    public LocationField getFocusedLocationField() {
        return focusedLocationField;
    }

    private LocationField focusedLocationField = DESTINATION;

    public TripViewModel() {}

    void setLocation(Location location, String name) {
        if (initOriginField) {
            origin.setValue(new TripLocation(name, location));
            initOriginField = false;
        }
        else if (focusedLocationField == ORIGIN)
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
