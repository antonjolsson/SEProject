package com.example.tripplannr;

import android.location.Location;

class TripLocation {
    String name;
    Location location;

    TripLocation(String name, Location location) {
        this.name = name;
        this.location = location;
    }
}
