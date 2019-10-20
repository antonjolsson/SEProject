package com.example.tripplannr.application_layer.util;

import com.example.tripplannr.domain_layer.Trip;

import java.util.List;

public interface TripParser {
    List<Trip> getTrips(String data) throws Exception;
}