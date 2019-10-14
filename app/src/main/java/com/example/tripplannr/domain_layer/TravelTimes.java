package com.example.tripplannr.domain_layer;

import java.util.Calendar;

public class TravelTimes {

    private Calendar departure;
    private Calendar arrival;
    private long duration;

    public TravelTimes(Calendar departure, Calendar arrival) {
        this.departure = departure;
        this.arrival = arrival;
        //duration = ChronoUnit.MINUTES.between(departure, arrival);
        duration = (departure.getTimeInMillis() -arrival.getTimeInMillis())/60000;
    }

    public Calendar getDeparture() {
        return departure;
    }

    public Calendar getArrival() {
        return arrival;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
