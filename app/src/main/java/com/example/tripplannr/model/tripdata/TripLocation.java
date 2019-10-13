package com.example.tripplannr.model.tripdata;

import android.location.Location;

public class TripLocation {

    private String name;
    private Location location;
    private String track;

    public TripLocation(String name, Location location) {
        this.name = name;
        this.location = location;
    }

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
