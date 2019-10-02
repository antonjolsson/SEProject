package com.example.tripplannr.trip;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.model.Trip;
import com.example.tripplannr.model.VasttrafikApi;

import org.json.JSONException;

import java.util.List;

public class TripResultViewModel extends ViewModel implements IClickHandler<Trip> {

    private MutableLiveData<List<Trip>> mTripsLiveData = new MutableLiveData<>();

    private MutableLiveData<Trip> mTripLiveData = new MutableLiveData<>();

    private Context context;

    public TripResultViewModel() {
        super();
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

    public void setContext(Context context) {
        this.context = context;
        buildFakeTrip();
    }

    private void buildFakeTrip() {
        try {
            mTripsLiveData.setValue(new VasttrafikApi(context).getRoute("xd"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
