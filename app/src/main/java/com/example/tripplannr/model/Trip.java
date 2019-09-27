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

    private Trip(Builder builder) {
        name = builder.name;
        routes = builder.routes;
        origin = builder.origin;
        destination = builder.destination;
        times = builder.times;
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

    public static final class Builder {
        private String name;
        private List<Route> routes;
        private Location origin;
        private Location destination;
        private TravelTimes times;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder routes(List<Route> val) {
            routes = val;
            return this;
        }

        public Builder origin(Location val) {
            origin = val;
            return this;
        }

        public Builder destination(Location val) {
            destination = val;
            return this;
        }

        public Builder times(TravelTimes val) {
            times = val;
            return this;
        }

        public Trip build() {
            return new Trip(this);
        }
    }
}
