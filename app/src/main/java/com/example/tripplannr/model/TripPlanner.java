package com.example.tripplannr.model;

import java.util.ArrayList;
import java.util.List;

class TripPlanner {

    private List<TripApi> tripApis;

    TripPlanner() {
        tripApis = new ArrayList<>();
        TripApi stenaLineApi = new StenaLineApi();

        //TODO implement when no context needed?
        //TripApi vasttrafikAPi = new VasttrafikApi(new Context() {
        //})

        tripApis.add(stenaLineApi);
    }

    List<Trip> makeTrip(TripQuery tripQuery) {
        // TODO Create trips with routes returned from api:s
        List<Trip> trips = new ArrayList<>();
        return trips;
    }

}