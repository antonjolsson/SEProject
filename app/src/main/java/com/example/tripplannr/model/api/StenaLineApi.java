package com.example.tripplannr.model.api;

import com.example.tripplannr.model.Trip;

import java.text.ParseException;
import java.util.List;

public class StenaLineApi implements TripApi {

    private String apiAddress;

    public StenaLineApi( ) {

        //TODO
    }

    @Override
    public List<Trip> getRoute(String data ) throws ParseException {
        return null;
        //TODO
    }

}