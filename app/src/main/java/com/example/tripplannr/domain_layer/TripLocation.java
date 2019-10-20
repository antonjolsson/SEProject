package com.example.tripplannr.domain_layer;

import android.location.Location;

import androidx.room.Ignore;

public class TripLocation {

    private String name;
    @Ignore
    private Location location;
    private String track;

    @Ignore
    public TripLocation(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public TripLocation(String name, Location location, String track) {
        this.name = name;
        this.location = location;
        this.track = track;
    }

    public TripLocation(String name, String track) {
        this.name = name;
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
