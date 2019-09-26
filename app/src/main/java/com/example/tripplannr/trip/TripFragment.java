package com.example.tripplannr.trip;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplannr.stdanica.R;
import com.example.tripplannr.model.Trip;

import java.util.Objects;


public class TripFragment extends Fragment {

    private TripResultViewModel tripResultViewModel;

    private FragmentActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViewModel();
        mainActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        initComponents(view);
        return view;
    }

    private void goBack() {
        tripResultViewModel.getTripsLiveData().getValue().add(new Trip("test", "test", 0, 0));
        System.out.println(tripResultViewModel.getTripsLiveData().getValue());
        mainActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new TripResultFragment())
                .commit();
    }

    private void initComponents(View view) {
        view.findViewById(R.id.testReturnButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goBack();
                    }
                });
    }


    private void initViewModel() {
        tripResultViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
        tripResultViewModel.getTripLiveData().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                onTripUpdated();
            }
        });
    }

    private void onTripUpdated() {

    }

}
