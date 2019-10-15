package com.example.tripplannr.application_layer.util;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.application_layer.trip.TripResultViewModel;
import com.example.tripplannr.application_layer.trip.TripResultViewModelFactory;
import com.example.tripplannr.data_access_layer.AppDatabase;
import com.example.tripplannr.data_access_layer.repositories.TripRepository;

public class InjectorUtils {

    private static TripRepository getTripRepository(Context context) {
        return new TripRepository(AppDatabase.getInstance(context).tripDAO());
    }

    public static TripResultViewModel getTripResultViewModel(Context context, FragmentActivity activity) {
        //return new TripResultViewModelFactory(getTripRepository(context)).create(TripResultViewModel.class);
        return ViewModelProviders
                .of(activity, TripResultViewModelFactory.getInstance(getTripRepository(context)))
                .get(TripResultViewModel.class);
    }

}
