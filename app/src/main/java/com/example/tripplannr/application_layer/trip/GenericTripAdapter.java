package com.example.tripplannr.application_layer.trip;

import com.example.tripplannr.domain_layer.Trip;

import java.util.List;

public interface GenericTripAdapter<T> {

    void switchPosition(int from, int to);

    void updateTrips(final List<T> newTs);

    List<T> getData();

}
