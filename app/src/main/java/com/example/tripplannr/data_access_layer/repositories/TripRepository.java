package com.example.tripplannr.data_access_layer.repositories;

import android.os.AsyncTask;

import com.example.tripplannr.data_access_layer.data_sources.TripDAO;
import com.example.tripplannr.domain_layer.Trip;
import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

public class TripRepository {

    private TripDAO tripDAO;

    public TripRepository(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    public void delete(final Trip trip) {
        new DataBaseTask<>(trip, new Function<Trip, Void>() {
            @Nullable
            @Override
            public Void apply(@Nullable Trip input) {
                tripDAO.delete(trip);
                return null;
            }
        }).execute();
    }

    public List<Trip> findAll() {
        try {
            return new DataBaseTask<>(null, new Function<Void, List<Trip>>() {
                @Nullable
                @Override
                public List<Trip> apply(@Nullable Void input) {
                    return tripDAO.findAll();
                }
            }).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            return new ArrayList<>();
        }
    }

    public void save(final Trip trip) {
        new DataBaseTask<>(trip, new Function<Trip, Void>() {
            @Nullable
            @Override
            public Void apply(@Nullable Trip input) {
                tripDAO.save(trip);
                return null;
            }
        }).execute();
    }

    public void deleteById(final int id) {
        new DataBaseTask<>(id, new Function<Integer, Void>() {
            @Nullable
            @Override
            public Void apply(@Nullable Integer input) {
                tripDAO.deleteById(id);
                return null;
            }
        }).execute();
    }

    private static class DataBaseTask<T, R> extends AsyncTask<Void, Void, R> {

        private T data;
        private Function<T, R> asyncFunction;

        public DataBaseTask(T data, Function<T, R> asyncFunction) {
            this.data = data;
            this.asyncFunction = asyncFunction;
        }

        @Override
        protected R doInBackground(Void... voids) {
            return asyncFunction.apply(data);
        }
    }

}
