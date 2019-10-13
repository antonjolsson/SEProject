package com.example.tripplannr.data_access_layer.data_sources;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tripplannr.domain_layer.Route;
import com.example.tripplannr.domain_layer.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Dao
public abstract class TripDAO {


    public void delete(Trip trip) {
        deleteTrip(trip);
        deleteAllRoutesById(trip.getId());
    }

    public List<Trip> findAll() {
        return findAllHelper()
                .stream()
                .map(new Function<Trip, Trip>() {
                    @Override
                    public Trip apply(Trip trip) {
                        trip.setRoutes(findAllRoutesById(trip.getId()));
                        return trip;
                    }
                }).collect(Collectors.toCollection(new Supplier<List<Trip>>() {
                    @Override
                    public List<Trip> get() {
                        return new ArrayList<>();
                    }
                }));
    }

    public void save(final Trip trip) {
        final int id = (int) insertTrip(trip);
        insertAllRoutes(trip.getRoutes()
                            .stream()
                            .map(new Function<Route, Route>() {
                                @Override
                                public Route apply(Route route) {
                                    route.setTripId(id);
                                    return route;
                                }
                            }).collect(Collectors.toCollection(new Supplier<List<Route>>() {
                    @Override
                    public List<Route> get() {
                        return new ArrayList<>();
                    }
                })));
    }

    @Insert
    abstract long insertTrip(Trip trip);

    @Query("delete from trip where id = :id")
    public abstract void deleteById(int id);

    @Insert
    abstract void insertAllRoutes(List<Route> routes);

    @Query("select * from trip")
    abstract List<Trip> findAllHelper();

    @Query("select * from route where tripId = :id")
    abstract List<Route> findAllRoutesById(int id);

    @Delete
    abstract void deleteTrip(Trip trip);

    @Query("delete from route where tripId = :id")
    abstract void deleteAllRoutesById(int id);

}
