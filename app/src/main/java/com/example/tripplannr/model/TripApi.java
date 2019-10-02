package com.example.tripplannr.model;

import org.json.JSONException;

import java.text.ParseException;
import java.util.List;

public interface TripApi {
    List<Trip> getRoute(String data) throws ParseException, JSONException;
}