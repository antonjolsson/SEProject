package com.example.tripplannr.domain_layer;

import android.location.Location;

import java.time.LocalDateTime;
import java.util.List;

public class TripQuery {

    private String origin;
    private String destination;
    private Location originLocation;
    private Location destinationLocation;
    private LocalDateTime time;
    private final boolean timeIsDeparture;
    private final List<ModeOfTransport> travelModes;


    public TripQuery(String origin, String destination, LocalDateTime time, boolean timeIsDeparture,
                     List<ModeOfTransport> travelModes) {
        this.origin = origin;
        this.destination = destination;
        this.time = time;
        this.timeIsDeparture = timeIsDeparture;
        this.travelModes = travelModes;
    }

    public TripQuery(Location originLocation, Location destinationLocation, LocalDateTime time, boolean timeIsDeparture,
                     List<ModeOfTransport> travelModes) {
        this.originLocation = originLocation;
        this.destinationLocation = destinationLocation;
        this.time = time;
        this.timeIsDeparture = timeIsDeparture;
        this.travelModes = travelModes;
    }

    private TripQuery(Builder builder) {
        origin = builder.origin;
        destination = builder.destination;
        originLocation = builder.originLocation;
        destinationLocation = builder.destinationLocation;
        time = builder.time;
        timeIsDeparture = builder.timeIsDeparture;
        travelModes = builder.travelModes;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public Location getOriginLocation() {
        return originLocation;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public boolean isTimeDeparture() {
        return timeIsDeparture;
    }

    public List<ModeOfTransport> getTravelModes() {
        return travelModes;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOriginLocation(Location originLocation) {
        this.originLocation = originLocation;
    }

    public void setDestinationLocation(Location destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public static final class Builder {
        private String origin;
        private String destination;
        private Location originLocation;
        private Location destinationLocation;
        private LocalDateTime time;
        private boolean timeIsDeparture;
        private List<ModeOfTransport> travelModes;

        public Builder() {
        }

        public Builder origin(String val) {
            origin = val;
            return this;
        }

        public Builder destination(String val) {
            destination = val;
            return this;
        }

        public Builder originLocation(Location val) {
            originLocation = val;
            return this;
        }

        public Builder destinationLocation(Location val) {
            destinationLocation = val;
            return this;
        }

        public Builder time(LocalDateTime val) {
            time = val;
            return this;
        }

        public Builder timeIsDeparture(boolean val) {
            timeIsDeparture = val;
            return this;
        }

        public Builder travelModes(List<ModeOfTransport> val) {
            travelModes = val;
            return this;
        }

        public TripQuery build() {
            return new TripQuery(this);
        }
    }
}