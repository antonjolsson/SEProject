package com.example.tripplannr.domain_layer;

import java.util.Collections;
import java.util.List;

public class Trip {

    private String name;
    private List<Route> routes;
    private TripLocation origin;
    private TripLocation destination;
    // private List<> notifications;
    private TravelTimes times;
    // private FerryInfo ferryinfo;

    public Trip(String name, List<Route> routes, TripLocation origin, TripLocation destination, TravelTimes times) {
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

    public TripLocation getOrigin() {
        return origin;
    }

    public TripLocation getDestination() {
        return destination;
    }


    public TravelTimes getTimes() {
        return times;
    }

    public static final class Builder {
        private String name;
        private List<Route> routes;
        private TripLocation origin;
        private TripLocation destination;
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

        public Builder origin(TripLocation val) {
            origin = val;
            return this;
        }

        public Builder destination(TripLocation val) {
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
