package com.example.tripplannr.data_access_layer.data_sources;

import com.example.tripplannr.domain_layer.Trip;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface TripDAO {

    boolean delete(Trip trip);

    List<Trip> findAll();

    Optional<Trip> save(Trip trip);

    void deleteById(int id);

}
