package com.example.tripplannr.domain_layer;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

public class TripQuery {

    private String origin;
    private String destination;
    private LocalDateTime time;
    private boolean timeIsDeparture;
    private List<ModeOfTransport> travelModes;


    public TripQuery(String origin, String destination, LocalDateTime time, boolean timeIsDeparture,
                     List<ModeOfTransport> travelModes) {
        this.origin = origin;
        this.destination = destination;
        this.time = time;
        this.timeIsDeparture = timeIsDeparture;
        this.travelModes = travelModes;
    }

    private TripQuery(Builder builder) {
        origin = builder.origin;
        destination = builder.destination;
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

    public LocalDateTime getTime() {
        return time;
    }

    public boolean isTimeDeparture() {
        return timeIsDeparture;
    }

    public List<ModeOfTransport> getTravelModes() {
        return travelModes;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public static final class Builder {
        private String origin;
        private String destination;
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