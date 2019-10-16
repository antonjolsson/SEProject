package com.example.tripplannr.application_layer.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tripplannr.data_access_layer.repositories.VasttafikRepository;

public class SearchViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static SearchViewModelFactory instance;

    public static SearchViewModelFactory getInstance(VasttafikRepository vasttafikRepository) {
        if(instance == null) instance = new SearchViewModelFactory(vasttafikRepository);
        return instance;
    }

    private VasttafikRepository vasttafikRepository;

    private SearchViewModelFactory(VasttafikRepository vasttafikRepository) {
        this.vasttafikRepository = vasttafikRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchViewModel(vasttafikRepository);
    }
}
