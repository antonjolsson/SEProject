package com.example.tripplannr.model;

import java.util.Collections;
import java.util.List;

public class Trip {

    private String name;
    private List<Route> routes;
    private Location origin;
    private Location destination;
    private TravelTimes times;

    public Trip(String name, List<Route> routes, Location origin, Location destination, TravelTimes times) {
        this.name = name;
        this.routes = routes;
        this.origin = origin;
        this.destination = destination;
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public List<Route> getRoutes() {
        return Collections.unmodifiableList(routes);
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

}
