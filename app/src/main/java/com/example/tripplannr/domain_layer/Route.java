package com.example.tripplannr.domain_layer;

import androidx.room.Embedded;

public class Route {

    @Embedded(prefix = "route_origin_")
    private TripLocation origin;
    @Embedded(prefix = "route_destination_")
    private TripLocation destination;
    @Embedded(prefix = "route_")
    private TravelTimes times;
    private ModeOfTransport mode;

    public Route(TripLocation origin, TripLocation destination, TravelTimes times,
                 ModeOfTransport mode) {
        this.origin = origin;
        this.destination = destination;
        this.times = times;
        this.mode = mode;
    }

    private Route(Builder builder) {
        origin = builder.origin;
        destination = builder.destination;
        times = builder.times;
        mode = builder.mode;
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

    public ModeOfTransport getMode() {
        return mode;
    }

    public void setOrigin(TripLocation origin) {
        this.origin = origin;
    }

    public void setDestination(TripLocation destination) {
        this.destination = destination;
    }

    public void setTimes(TravelTimes times) {
        this.times = times;
    }

    public void setMode(ModeOfTransport mode) {
        this.mode = mode;
    }

    public static final class Builder {
        private TripLocation origin;
        private TripLocation destination;
        private TravelTimes times;
        private ModeOfTransport mode;

        public Builder() {
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

        public Builder mode(ModeOfTransport val) {
            mode = val;
            return this;
        }

        public Route build() {
            return new Route(this);
        }
    }
}
