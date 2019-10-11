package com.example.tripplannr.data_access_layer.data_sources;

import com.example.tripplannr.domain_layer.Trip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TripDAOImpl implements TripDAO {

    private List<Trip> database = new ArrayList<>();

    private static TripDAOImpl instance = null;

    public static TripDAOImpl getInstance() {
        if(instance == null) instance = new TripDAOImpl();
        return instance;
    }

    @Override
    public boolean delete(Trip trip) {
        return database.remove(trip);
    }

    @Override
    public List<Trip> findAll() {
        return Collections.unmodifiableList(database);
    }

    @Override
    public Optional<Trip> save(Trip trip) {
        return database.add(trip) ? Optional.of(trip) : Optional.<Trip>empty();
    }

    @Override
    public void deleteById(int id) {
        database.remove(id);
    }

}