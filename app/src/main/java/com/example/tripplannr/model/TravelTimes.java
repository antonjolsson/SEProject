package com.example.tripplannr.model;

import java.time.LocalDate;
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

        //TODO
    }

}