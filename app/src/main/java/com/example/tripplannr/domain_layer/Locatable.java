package com.example.tripplannr.domain_layer;

public interface Locatable {

    void setOrigin(TripLocation origin);

    void setDestination(TripLocation destination);

    TripLocation getOrigin();

    TripLocation getDestination();
}
