package com.example.tripplannr.model;

import android.graphics.Point;

public class Location {

    private String name;
    private Point coordinates;

    public Location(String name, Point coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public Point getCoordinates() {
        return coordinates;
    }
}
