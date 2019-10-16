package com.example.tripplannr.data_access_layer.repositories;

import android.content.Context;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.tripplannr.data_access_layer.data_sources.VasttrafikServiceImpl;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripLocation;
import com.example.tripplannr.domain_layer.TripQuery;

import java.util.List;

public class VasttrafikRepository {

    private VasttrafikServiceImpl vasttrafikService;

    public VasttrafikRepository(Context context) {
        System.out.println(context);
       vasttrafikService = VasttrafikServiceImpl.getInstance(context);
    }

    public void loadTrips(TripQuery query) {
        vasttrafikService.loadTrips(query);
    }

    public LiveData<Integer> getStatusCode() {
        return vasttrafikService.getStatusCode();
    }

    public LiveData<List<Trip>> getData() {
        return vasttrafikService.getData();
    }

    public LiveData<Boolean> isLoading() {
        return vasttrafikService.isLoading();
    }

    public void getMatching(final String pattern) {
        vasttrafikService.getMatching(pattern);

    }

    public LiveData<List<TripLocation>> getAddressMatches() {
        return vasttrafikService.getAddressMatches();
    }

}
