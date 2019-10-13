package com.example.tripplannr.domain_layer;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Entity
public class Trip {

    @PrimaryKey
    private Long id;
    private String name;
    private ArrayList<Route> routes;
    @Embedded(prefix = "trip_origin_")
    private TripLocation origin;
    @Embedded(prefix = "trip_destination_")
    private TripLocation destination;
    @Ignore
    private TravelTimes times;
    // private List<> notifications;
    // private FerryInfo ferryinfo;

    public Trip(Long id, String name, ArrayList<Route> routes, TripLocation origin, TripLocation destination) {
        this.id = id;
        this.name = name;
        this.routes = routes;
        this.origin = origin;
        this.destination = destination;
        times = new TravelTimes(
                this.routes.get(0).getTimes().getDeparture()
                , this.routes.get(this.routes.size() - 1).getTimes().getArrival());
    }

    private Trip(Builder builder) {
        name = builder.name;
        routes = builder.routes;
        origin = builder.origin;
        destination = builder.destination;
        times = new TravelTimes(
                routes.get(0).getTimes().getDeparture()
                , routes.get(routes.size() - 1).getTimes().getArrival());
    }

    public String getName() {
        return name;
    }

    public ArrayList<Route> getRoutes() {
        return new ArrayList<>(routes);
    }

    public TripLocation getOrigin() {
        return origin;
    }

    public TripLocation getDestination() {
        return destination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public void setOrigin(TripLocation origin) {
        this.origin = origin;
    }

    public void setDestination(TripLocation destination) {
        this.destination = destination;
    }

    public TravelTimes getTimes() {
        return times;
    }

    public boolean hasFerry() {
        return routes
                .stream()
                .filter(new Predicate<Route>() {
                    @Override
                    public boolean test(Route route) {
                        return route.getMode().equals(ModeOfTransport.FERRY);
                    }
                })
                .findAny()
                .isPresent();
    }

    public static final class Builder {
        private String name;
        private ArrayList<Route> routes;
        private TripLocation origin;
        private TripLocation destination;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder routes(ArrayList<Route> val) {
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

        public Trip build() {
            return new Trip(this);
        }
    }


}
