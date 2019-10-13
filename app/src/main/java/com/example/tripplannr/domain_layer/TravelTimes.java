package com.example.tripplannr.domain_layer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TravelTimes {

    private LocalDateTime departure;
    private LocalDateTime arrival;
    private long duration;

    public TravelTimes(LocalDateTime departure, LocalDateTime arrival) {
        this.departure = departure;
        this.arrival = arrival;
        duration = ChronoUnit.MINUTES.between(departure, arrival);
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public long getDuration() {
        return duration;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
