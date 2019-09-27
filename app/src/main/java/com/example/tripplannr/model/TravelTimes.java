package com.example.tripplannr.model;

import java.time.LocalDateTime;

public class TravelTimes {

    private LocalDateTime depature;
    private LocalDateTime arrival;
    private long duration;

    public TravelTimes(LocalDateTime depature, LocalDateTime arrival, long duration) {
        this.depature = depature;
        this.arrival = arrival;
        this.duration = duration;
    }

    public LocalDateTime getDepature() {
        return depature;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public long getDuration() {
        return duration;
    }
}
