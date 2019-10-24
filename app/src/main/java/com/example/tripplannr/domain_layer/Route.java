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
    private List<TripLocation> stops;
    // Västtrafik journey reference
    @Ignore
    private String journeyRef;
    @Ignore
    // Västtrafik geometry reference
    private String geometryRef;

    public Route(String name, TripLocation origin, TripLocation destination, TravelTimes times,
                 ModeOfTransport mode) {
        this.name = name;
        this.origin = origin;
        this.destination = destination;
        this.times = times;
        this.mode = mode;
    }

    private Route(Builder builder) {
        setOrigin(builder.origin);
        setDestination(builder.destination);
        times = builder.times;
        mode = builder.mode;
        name = builder.name;
        setFerryinfo(builder.ferryinfo);
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

    public void setStops(List<TripLocation> stops) {
        this.stops = stops;
    }

    public void setGeometryRef(String geometryRef) {
        this.geometryRef = geometryRef;
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

    public List<TripLocation> getStops() {
        return stops;
    }

    public String getGeometryRef() {
        return geometryRef;
    }

    public FerryInfo getFerryinfo() {
        return ferryinfo;
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

    public void setFerryinfo(FerryInfo ferryinfo) {
        this.ferryinfo = ferryinfo;
    }

    public static final class Builder {
        private TripLocation origin;
        private TripLocation destination;
        private TravelTimes times;
        private ModeOfTransport mode;
        private String name;
        private FerryInfo ferryinfo;

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

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder ferryinfo(FerryInfo val) {
            ferryinfo = val;
            return this;
        }

        public Route build() {
            return new Route(this);
        }
    }
}