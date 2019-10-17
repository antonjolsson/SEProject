package com.example.tripplannr.domain_layer;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Entity
public class Trip implements Locatable{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @Ignore
    private List<Route> routes;

    @Ignore
    private TripLocation origin;
    @Ignore
    private TripLocation destination;
    @Ignore
    private TravelTimes times;
    // private List<> notifications;
    // private FerryInfo ferryinfo;


    public Trip(String name) {
        this.name = name;
    }

    public Trip(String name, List<Route> routes) {
        this.name = name;
        setRoutes(routes);

    }

    private Trip(Builder builder) {
        name = builder.name;
        setRoutes(builder.routes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Route> getRoutes() {
        return new ArrayList<>(routes);
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

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
        origin = routes.get(0).getOrigin();
        destination = routes.get(routes.size() - 1).getDestination();
        times = new TravelTimes(
                this.routes.get(0).getTimes().getDeparture()
                , this.routes.get(this.routes.size() - 1).getTimes().getArrival());
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
        private List<Route> routes;

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

        public Trip build() {
            return new Trip(this);
        }
    }

    public void addRouteEnd(Route route) {
        routes.add(route);
        destination = route.getDestination();
        times = new TravelTimes(times.getDeparture(), route.getTimes().getArrival());
    }

    public void addRouteStart(Route route) {
        routes.add(0, route);
        origin = route.getOrigin();
        times = new TravelTimes(route.getTimes().getDeparture(), times.getArrival());
    }

    public void setOrigin(TripLocation origin) {
        this.origin = origin;
    }

    public void setDestination(TripLocation destination) {
        this.destination = destination;
    }

}
