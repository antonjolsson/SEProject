package com.example.tripplannr.data_access_layer.repositories;

import com.example.tripplannr.data_access_layer.dao.TripDAO;
import com.example.tripplannr.domain_layer.Trip;

import java.util.List;
import java.util.Optional;

public class TripRepository {

    private TripDAO tripDAO = TripDAO.getInstance();

    public boolean delete(Trip trip) {
        return tripDAO.delete(trip);
    }

    public List<Trip> getSavedTrips() {
        return tripDAO.findAll();
    }

    public Optional<Trip> save(Trip trip) {
        return tripDAO.save(trip);
    }

    public void deleteById(int id) {
        tripDAO.deleteById(id);
    }

}
