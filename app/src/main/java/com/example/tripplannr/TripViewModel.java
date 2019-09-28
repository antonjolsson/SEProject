package com.example.tripplannr;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.example.tripplannr.TripViewModel.LocationField.*;

public class TripViewModel extends ViewModel {

    enum LocationField {ORIGIN, DESTINATION}
    private MutableLiveData<TripLocation> origin = new MutableLiveData<>();
    private MutableLiveData<TripLocation> destination = new MutableLiveData<>();
    private LocationField focusedLocationField = DESTINATION;

    public TripViewModel() {}

    void setLocation(Location location, String name) {
        if (focusedLocationField == ORIGIN)
            origin.setValue(new TripLocation(name, location));
        else destination.setValue(new TripLocation(name, location));
    }

    LiveData<TripLocation> getOrigin() {
        return origin;
    }

    LiveData<TripLocation> getDestination() {
        return destination;
    }
}
