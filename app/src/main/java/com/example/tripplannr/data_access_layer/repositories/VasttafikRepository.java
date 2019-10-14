package com.example.tripplannr.data_access_layer.repositories;

import androidx.lifecycle.LiveData;

import com.example.tripplannr.data_access_layer.data_sources.VasttrafikServiceImpl;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripQuery;

import java.util.List;

public class VasttafikRepository {

    private VasttrafikServiceImpl vasttrafikService = VasttrafikServiceImpl.getInstance();

    public void loadTrips(TripQuery query) {
        vasttrafikService.loadTrips(query.getOrigin(), query.getDestination());
    }

    public LiveData<List<Trip>> getData() {
        return vasttrafikService.getData();
    }

    public LiveData<Boolean> isLoading() {
        return vasttrafikService.isLoading();
    }

}
