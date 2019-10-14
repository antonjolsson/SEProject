package com.example.tripplannr.domain_layer;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Route {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int tripId;
    @Embedded(prefix = "origin_")
    private TripLocation origin;
    @Embedded(prefix = "destination_")
    private TripLocation destination;
    @Embedded
    private TravelTimes times;
    private ModeOfTransport mode;

    public Route(TripLocation origin, TripLocation destination, TravelTimes times,
                 ModeOfTransport mode) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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
}
