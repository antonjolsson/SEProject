package com.example.tripplannr.model;

import java.util.Calendar;
import java.util.List;

class TripQuery {

    private TripLocation origin;
    private TripLocation destination;
    private Calendar time;
    private boolean timeIsDeparture;
    private List<ModeOfTransport> travelModes;


    TripQuery(TripLocation origin, TripLocation destination, Calendar time, boolean timeIsDeparture,
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

    public Calendar getTime() {
        return time;
    }

    public boolean isTimeDeparture() {
        return timeIsDeparture;
    }

    public List<ModeOfTransport> getTravelModes() {
        return travelModes;
    }

}