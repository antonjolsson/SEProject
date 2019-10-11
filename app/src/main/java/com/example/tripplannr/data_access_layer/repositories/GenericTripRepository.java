package com.example.tripplannr.data_access_layer.repositories;

import com.example.tripplannr.application_layer.util.StenaLineParser;
import com.example.tripplannr.application_layer.util.TripParser;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripQuery;

import java.util.ArrayList;
import java.util.List;

public class GenericTripRepository {

    private List<TripParser> tripParsers;

    public GenericTripRepository() {
        tripParsers = new ArrayList<>();
        TripParser stenaLineApi = new StenaLineParser();

        //TODO implement when no context needed?
        //TripParser vasttrafikAPi = new VasttrafikParser(new Context() {
        //})

        tripParsers.add(stenaLineApi);
    }

    public List<Trip> makeTrip(TripQuery tripQuery) {
        // TODO Create trips with routes returned from api:s
        List<Trip> trips = new ArrayList<>();
        return trips;
    }

}