package com.example.tripplannr.domain_layer;

import android.location.Location;

import androidx.room.Ignore;

public class TripLocation {

    private final String name;
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

    private TripLocation(Builder builder) {
        name = builder.name;
        location = builder.location;
        track = builder.track;
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

    public static final class Builder {
        private String name;
        private Location location;
        private String track;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder location(Location val) {
            location = val;
            return this;
        }

        public Builder track(String val) {
            track = val;
            return this;
        }

        public TripLocation build() {
            return new TripLocation(this);
        }
    }
}
