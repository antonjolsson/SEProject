package com.example.tripplannr.model;

public class Route {

    private Location origin;
    private Location destination;
    private TravelTimes times;
    private ModeOfTransport mode;

    public Route(Location origin, Location destination, TravelTimes times, ModeOfTransport mode) {
        this.origin = origin;
        this.destination = destination;
        this.times = times;
        this.mode = mode;
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getDestination() {
        return destination;
    }

    public TravelTimes getTimes() {
        return times;
    }

    public ModeOfTransport getMode() {
        return mode;
    }
}
