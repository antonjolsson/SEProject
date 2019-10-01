package com.example.tripplannr.model;

import com.google.android.gms.maps.model.LatLng;

public class TripLocation {

    private String name;
    private LatLng coordinates;
    private String track;

    public TripLocation(String name, LatLng coordinates, String track) {
        this.name = name;
        this.coordinates = coordinates;
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }
}
