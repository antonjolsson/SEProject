package com.example.tripplannr.domain_layer;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

public class TripQuery {

    private TripLocation origin;
    private TripLocation destination;
    private LocalDateTime time;
    private boolean timeIsDeparture;
    private List<ModeOfTransport> travelModes;


    public TripQuery(TripLocation origin, TripLocation destination, LocalDateTime time, boolean timeIsDeparture,
                     List<ModeOfTransport> travelModes) {
        this.origin = origin;
        this.destination = destination;
        this.time = time;
        this.timeIsDeparture = timeIsDeparture;
        this.travelModes = travelModes;
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


    public boolean isTimeDeparture() {
        return timeIsDeparture;
    }

    public List<ModeOfTransport> getTravelModes() {
        return travelModes;
    }

}