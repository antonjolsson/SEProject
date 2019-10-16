package com.example.tripplannr.application_layer.trip;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tripplannr.data_access_layer.data_sources.TripDAO;
import com.example.tripplannr.data_access_layer.repositories.TripRepository;
import com.example.tripplannr.data_access_layer.repositories.VasttrafikRepository;

public class TripResultViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private TripRepository tripRepository;
    private VasttrafikRepository vasttrafikRepository;

    private static TripResultViewModelFactory instance = null;

    private TripResultViewModelFactory(TripRepository tripRepository, VasttrafikRepository vasttrafikRepository) {
        this.tripRepository = tripRepository;
        this.vasttrafikRepository = vasttrafikRepository;
    }

    public static TripResultViewModelFactory getInstance(TripRepository tripRepository, VasttrafikRepository vasttrafikRepository) {
        if (instance == null) {
            instance = new TripResultViewModelFactory(tripRepository, vasttrafikRepository);
        }
        return instance;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TripResultViewModel(tripRepository, vasttrafikRepository);
    }
}
