package com.example.tripplannr.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
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
}
