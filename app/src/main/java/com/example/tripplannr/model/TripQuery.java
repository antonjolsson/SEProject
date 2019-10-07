package com.example.tripplannr.model;

import com.example.tripplannr.model.tripdata.FerryInfo;
import com.example.tripplannr.model.tripdata.TripLocation;

import java.util.Calendar;
import java.util.List;

public class TripQuery {

    private TripLocation origin;
    private TripLocation destination;
    private Calendar time;
    private boolean timeIsDeparture;
    private List<FerryInfo.ModeOfTransport> travelModes;


    public TripQuery(TripLocation origin, TripLocation destination, Calendar time, boolean timeIsDeparture,
                     List<FerryInfo.ModeOfTransport> travelModes) {
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

    public List<FerryInfo.ModeOfTransport> getTravelModes() {
        return travelModes;
    }

}