package com.example.tripplannr.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

public class TripRepository {

    private List<Trip> database = new ArrayList<>();

    private static TripRepository instance = null;

    public static TripRepository getInstance() {
        if(instance == null) instance = new TripRepository();
        return instance;
    }

    public boolean delete(Trip trip) {
        return database.remove(trip);
    }

    public List<Trip> getSavedTrips() {
        return Collections.unmodifiableList(database);
    }

    public Optional<Trip> save(Trip trip) {
        return database.add(trip) ? Optional.of(trip) : Optional.<Trip>empty();
    }

}
