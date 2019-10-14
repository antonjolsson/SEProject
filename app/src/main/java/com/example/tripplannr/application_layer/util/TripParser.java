package com.example.tripplannr.application_layer.util;

import com.example.tripplannr.domain_layer.Trip;

import org.json.JSONException;

import java.text.ParseException;
import java.util.List;

public interface TripParser {
    List<Trip> getRoute(String data) throws ParseException, JSONException;
}