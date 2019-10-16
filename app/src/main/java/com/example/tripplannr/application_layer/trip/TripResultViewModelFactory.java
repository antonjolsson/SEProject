package com.example.tripplannr.application_layer.trip;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tripplannr.data_access_layer.data_sources.TripDAO;
import com.example.tripplannr.data_access_layer.repositories.TripRepository;
import com.example.tripplannr.data_access_layer.repositories.VasttafikRepository;

public class TripResultViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private TripRepository tripRepository;
    private VasttafikRepository vasttafikRepository;

    private static TripResultViewModelFactory instance = null;

    private TripResultViewModelFactory(TripRepository tripRepository, VasttafikRepository vasttafikRepository) {
        this.tripRepository = tripRepository;
        this.vasttafikRepository = vasttafikRepository;
    }

    public static TripResultViewModelFactory getInstance(TripRepository tripRepository, VasttafikRepository vasttafikRepository) {
        if (instance == null) {
            instance = new TripResultViewModelFactory(tripRepository, vasttafikRepository);
        }
        return instance;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TripResultViewModel(tripRepository, vasttafikRepository);
    }
}
