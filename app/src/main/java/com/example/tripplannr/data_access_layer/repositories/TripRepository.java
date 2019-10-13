package com.example.tripplannr.data_access_layer.repositories;

import com.example.tripplannr.data_access_layer.data_sources.TripDAO;
import com.example.tripplannr.domain_layer.Trip;

import java.util.List;
import java.util.Optional;

public class TripRepository {

    private TripDAO tripDAO;

    public TripRepository(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    public void delete(Trip trip) {
        tripDAO.delete(trip);
    }

    public List<Trip> findAll() {
        return tripDAO.findAll();
    }

    public void save(Trip trip) {
        tripDAO.save(trip);
    }

    public void deleteById(int id) {
        tripDAO.deleteById(id);
    }

}
