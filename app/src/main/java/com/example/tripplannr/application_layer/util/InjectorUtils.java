package com.example.tripplannr.application_layer.util;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.application_layer.search.SearchViewModel;
import com.example.tripplannr.application_layer.search.SearchViewModelFactory;
import com.example.tripplannr.application_layer.trip.TripResultViewModel;
import com.example.tripplannr.application_layer.trip.TripResultViewModelFactory;
import com.example.tripplannr.data_access_layer.AppDatabase;
import com.example.tripplannr.data_access_layer.repositories.TripRepository;
import com.example.tripplannr.data_access_layer.repositories.VasttrafikRepository;

public class InjectorUtils {

    private static TripRepository getTripRepository(Context context) {
        return new TripRepository(AppDatabase.getInstance(context).tripDAO());
    }

    private static VasttrafikRepository getVasttrafikRepository(Context context) {
        return new VasttrafikRepository(context);
    }

    public static TripResultViewModel getTripResultViewModel(Context context, FragmentActivity activity) {
        //return new TripResultViewModelFactory(getTripRepository(context)).create(TripResultViewModel.class);
        return ViewModelProviders
                .of(activity, TripResultViewModelFactory.getInstance(getTripRepository(context), getVasttrafikRepository(context)))
                .get(TripResultViewModel.class);
    }

    public static SearchViewModel getSearchViewModel(Context context, FragmentActivity activity) {
        //return new TripResultViewModelFactory(getTripRepository(context)).create(TripResultViewModel.class);
        return ViewModelProviders
                .of(activity, SearchViewModelFactory.getInstance(getVasttrafikRepository(context)))
                .get(SearchViewModel.class);
    }

}
