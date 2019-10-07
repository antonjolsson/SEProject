package com.example.tripplannr.model;

import com.example.tripplannr.model.api.StenaLineApi;
import com.example.tripplannr.model.api.TripApi;

import java.util.ArrayList;
import java.util.List;

public class TripPlanner {

    private List<TripApi> tripApis;

    public TripPlanner() {
        tripApis = new ArrayList<>();
        TripApi stenaLineApi = new StenaLineApi();

        //TODO implement when no context needed?
        //TripApi vasttrafikAPi = new VasttrafikApi(new Context() {
        //})

        tripApis.add(stenaLineApi);
    }

    public List<Trip> makeTrip(TripQuery tripQuery) {
        // TODO Create trips with routes returned from api:s
        List<Trip> trips = new ArrayList<>();
        return trips;
    }

}