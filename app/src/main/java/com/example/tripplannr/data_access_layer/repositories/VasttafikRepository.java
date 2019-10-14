package com.example.tripplannr.data_access_layer.repositories;

import androidx.lifecycle.LiveData;

import com.example.tripplannr.data_access_layer.data_sources.VasttrafikServiceImpl;
import com.example.tripplannr.domain_layer.Trip;

import java.util.List;

public class VasttafikRepository {

    private VasttrafikServiceImpl vasttrafikService = new VasttrafikServiceImpl();

    public void loadTrips(final String origin, final String destination) {
        vasttrafikService.loadTrips(origin, destination);
    }

    public void getMatching(final String pattern) {
        vasttrafikService.getMatching(pattern);
    }

    public LiveData<List<Trip>> getData() {
        return vasttrafikService.getData();
    }

    public LiveData<Boolean> isLoading() {
        return vasttrafikService.isLoading();
    }

}
