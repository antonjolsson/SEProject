package com.example.tripplannr.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class TripLocation {

    private String name;
    private Location location;
    private String track;

    public TripLocation(String name, Location location, String track) {
        this.name = name;
        this.location = location;
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
