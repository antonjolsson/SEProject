package com.example.tripplannr.data_access_layer.dao;

import com.example.tripplannr.domain_layer.Trip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TripDAO {

    private List<Trip> database = new ArrayList<>();

    private static TripDAO instance = null;

    public static TripDAO getInstance() {
        if(instance == null) instance = new TripDAO();
        return instance;
    }

    public boolean delete(Trip trip) {
        return database.remove(trip);
    }

    public List<Trip> findAll() {
        return Collections.unmodifiableList(database);
    }

    public Optional<Trip> save(Trip trip) {
        return database.add(trip) ? Optional.of(trip) : Optional.<Trip>empty();
    }

    public void deleteById(int id) {
        database.remove(id);
    }

}