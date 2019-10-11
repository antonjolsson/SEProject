package com.example.tripplannr.data_access_layer.repositories;

import com.example.tripplannr.data_access_layer.data_sources.TripDAOImpl;
import com.example.tripplannr.domain_layer.Trip;

import java.util.List;
import java.util.Optional;

public class TripRepository {

    private TripDAOImpl tripDAOImpl = TripDAOImpl.getInstance();

    public boolean delete(Trip trip) {
        return tripDAOImpl.delete(trip);
    }

    public List<Trip> getSavedTrips() {
        return tripDAOImpl.findAll();
    }

    public Optional<Trip> save(Trip trip) {
        return tripDAOImpl.save(trip);
    }

    public void deleteById(int id) {
        tripDAOImpl.deleteById(id);
    }

}
