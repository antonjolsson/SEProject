package com.example.tripplannr.domain_layer;

import android.location.Location;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Route implements Locatable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int tripId;
    @Embedded(prefix = "origin_")
    private TripLocation origin;
    @Embedded(prefix = "destination_")
    private TripLocation destination;
    @Embedded
    private final TravelTimes times;
    private final ModeOfTransport mode;
    private String name;
    @Ignore
    private FerryInfo ferryinfo;
    @Ignore
    private List<Location> legs;
    @Ignore
    private List<TripLocation> locations;
    // Västtrafik journey reference
    private String journeyRef;

    public Route(String name, TripLocation origin, TripLocation destination, TravelTimes times,
                 ModeOfTransport mode) {
        this.name = name;
        this.origin = origin;
        this.destination = destination;
        this.times = times;
        this.mode = mode;
    }

    private Route(Builder builder) {
        origin = builder.origin;
        destination = builder.destination;
        times = builder.times;
        mode = builder.mode;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public void setLocations(List<TripLocation> locations) {
        this.locations = locations;
    }

    public void setOrigin(TripLocation origin) {
        this.origin = origin;
    }

    public void setDestination(TripLocation destination) {
        this.destination = destination;
    }

    public TripLocation getOrigin() {
        return origin;
    }

    public TripLocation getDestination() {
        return destination;
    }

    public TravelTimes getTimes() {
        return times;
    }

    public ModeOfTransport getMode() {
        return mode;
    }

    public int getTripId() {
        return tripId;
    }

    public List<Location> getLegs() {
        return legs;
    }

    public List<TripLocation> getLocations() {
        return locations;
    }

    public static final class Builder {
        private TripLocation origin;
        private TripLocation destination;
        private TravelTimes times;
        private ModeOfTransport mode;

        public Builder() {
        }

        public Builder origin(TripLocation val) {
            origin = val;
            return this;
        }

        public Builder destination(TripLocation val) {
            destination = val;
            return this;
        }

        public Builder times(TravelTimes val) {
            times = val;
            return this;
        }

        public Builder mode(ModeOfTransport val) {
            mode = val;
            return this;
        }

        public Route build() {
            return new Route(this);
        }
    }

    public void setFerryInfo(FerryInfo ferryinfo) {
        this.ferryinfo = ferryinfo;
    }

    public void setLegs(List<Location> legs) {
        this.legs = legs;
    }

    public void setJourneyRef(String journeyRef) {
        this.journeyRef = journeyRef;
    }

    public String getJourneyRef() {
        return journeyRef;
    }
}