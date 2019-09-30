package com.example.tripplannr.model;

import android.graphics.Point;

public class Location {

    private String name;
    private String track;
    private Point coordinates;

    public Location(String name, String track, Point coordinates) {
        this.name = name;
        this.track = track;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public String getTrack() {
        return track;
    }
}
