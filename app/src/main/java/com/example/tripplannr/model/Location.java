package com.example.tripplannr.model;

import android.graphics.Point;

public class Location {

    private String name;
    private Point coordinates;
    private String track;

    public Location(String name, Point coordinates, String track) {
        this.name = name;
        this.coordinates = coordinates;
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public Point getCoordinates() {
        return coordinates;
    }
}
