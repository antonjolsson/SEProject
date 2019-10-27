package com.example.tripplannr.application_layer.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tripplannr.data_access_layer.repositories.VasttrafikRepository;

public class SearchViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static SearchViewModelFactory instance;

    public static SearchViewModelFactory getInstance(VasttrafikRepository vasttrafikRepository) {
        if (instance == null) instance = new SearchViewModelFactory(vasttrafikRepository);
        return instance;
    }

    private final VasttrafikRepository vasttrafikRepository;

    private SearchViewModelFactory(VasttrafikRepository vasttrafikRepository) {
        this.vasttrafikRepository = vasttrafikRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new SearchViewModel(vasttrafikRepository);
    }
}
