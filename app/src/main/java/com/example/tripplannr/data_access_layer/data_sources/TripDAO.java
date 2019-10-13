package com.example.tripplannr.data_access_layer.data_sources;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tripplannr.domain_layer.Trip;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Dao
public interface TripDAO {

    @Delete
    void delete(Trip trip);

    @Query("select * from trip")
    List<Trip> findAll();

    @Insert
    void save(Trip trip);

    @Query("delete from trip where id = :id")
    void deleteById(int id);

}
