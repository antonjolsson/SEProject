package com.example.tripplannr.application_layer.trip;

import java.util.List;

interface GenericTripAdapter<T> {

    void switchPosition(int from, int to);

    void updateTrips(final List<T> newTs);

    List<T> getData();

}
