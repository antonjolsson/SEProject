package com.example.tripplannr.model;

public class Route {

    private Location origin;
    private Location destination;
    private TravelTimes times;
    // private ModeOfTransport mode;


    public Route(Location origin, Location destination, TravelTimes times, String mode) {
        this.origin = origin;
        this.destination = destination;
        this.times = times;

        //TODO
    }

}