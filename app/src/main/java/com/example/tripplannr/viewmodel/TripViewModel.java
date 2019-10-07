package com.example.tripplannr.viewmodel;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.model.Trip;
import com.example.tripplannr.model.tripdata.TripLocation;
import com.example.tripplannr.model.TripPlanner;
import com.example.tripplannr.model.TripQuery;

import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.*;
import static com.example.tripplannr.viewmodel.TripViewModel.ShownFragment.*;

public class TripViewModel extends ViewModel {

    public enum ShownFragment {MAP, TIME_CONTROLS}
    public enum LocationField {ORIGIN, DESTINATION}
    private MutableLiveData<TripLocation> origin = new MutableLiveData<>();
    private MutableLiveData<TripLocation> destination = new MutableLiveData<>();
    private MutableLiveData<Boolean> addressQuery = new MutableLiveData<>();
    private MutableLiveData<Calendar> desiredTime = new MutableLiveData<>();
    private MutableLiveData<Boolean> timeIsDeparture = new MutableLiveData<>();
    private MutableLiveData<ShownFragment> fragments = new MutableLiveData<>();

    private MutableLiveData<List<Trip>> trips = new MutableLiveData<>();

    private boolean initOriginField = true;
    private Deque<LocationField> focusedLocationFields = new ArrayDeque<>();
    private TripPlanner tripPlanner;

    public TripViewModel() {
        focusedLocationFields.push(DESTINATION);
        timeIsDeparture.setValue(true);
        tripPlanner = new TripPlanner();
    }

    public void obtainTrips() {
        TripQuery tripQuery = new TripQuery(origin.getValue(), destination.getValue(),
                desiredTime.getValue(),
                Objects.requireNonNull(timeIsDeparture.getValue()), null);
        trips.setValue(tripPlanner.makeTrip(tripQuery));
    }

    public MutableLiveData<ShownFragment> getFragments() {
        return fragments;
    }

    public void setTime(Calendar desiredTime, boolean timeIsDeparture) {
        this.desiredTime.setValue(desiredTime);
        this.timeIsDeparture.setValue(timeIsDeparture);
    }

    public void showTimeControls() {
        fragments.setValue(TIME_CONTROLS);
    }

    public void showMap() {
        fragments.setValue(MAP);
    }

    public void flattenFocLocationStack() {
        if (focusedLocationFields.size() > 1) focusedLocationFields.remove();
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
}
