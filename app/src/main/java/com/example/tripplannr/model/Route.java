package com.example.tripplannr.model;

public class Route {

    private TripLocation origin;
    private TripLocation destination;
    private TravelTimes times;
    private ModeOfTransport mode;

    public Route(TripLocation origin, TripLocation destination, TravelTimes times, ModeOfTransport mode) {
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
