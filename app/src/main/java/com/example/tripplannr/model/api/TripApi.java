package com.example.tripplannr.model.api;

import com.example.tripplannr.model.Trip;

import org.json.JSONException;

import java.text.ParseException;
import java.util.List;

public interface TripApi {
    List<Trip> getRoute(String data) throws ParseException, JSONException;
}