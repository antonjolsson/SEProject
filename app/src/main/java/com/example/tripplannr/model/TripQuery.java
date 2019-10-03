package com.example.tripplannr.model;

import java.time.LocalDateTime;
import java.util.List;

public class TripQuery {

    private TripLocation origin;
    private TripLocation destination;
    private LocalDateTime time;
    private boolean arrivalTime;
    private List<ModeOfTransport> makeTrip;


    public TripQuery(TripLocation origin, TripLocation des, LocalDateTime time ) {
        this.origin = origin;
        this.destination = des;
        this.time = time;

        //TODO
    }

    public TripLocation getOrigin() {
        return origin;
    }

    public TripLocation getDestination() {
        return destination;
    }

    public LocalDateTime getTime() {
        return time;
    }
}