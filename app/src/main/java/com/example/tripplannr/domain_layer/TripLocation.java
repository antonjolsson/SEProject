package com.example.tripplannr.domain_layer;

import android.location.Location;

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

    public String getTrack() {
        return track;
    }
}
