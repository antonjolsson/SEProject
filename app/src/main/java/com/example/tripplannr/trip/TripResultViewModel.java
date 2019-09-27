package com.example.tripplannr.trip;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.model.Trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripResultViewModel extends ViewModel implements IClickHandler<Trip> {

    private MutableLiveData<List<Trip>> mTripsLiveData = new MutableLiveData<>();

    private MutableLiveData<Trip> mTripLiveData = new MutableLiveData<>();

    public TripResultViewModel() {
        super();
        mTripsLiveData.setValue(new ArrayList<>(Arrays.asList(
                new Trip("13:00", "17:00", 5, 3)
                , new Trip("14:00", "18:00", 2, 1)
        )));
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
}
