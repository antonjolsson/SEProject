package com.example.tripplannr.trip;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplannr.stdanica.R;
import com.example.tripplannr.model.Trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


public class TripResultFragment extends Fragment {

    private RecyclerView resultRecyclerView;

    private TripResultViewModel tripResultViewModel;

    private List<Trip> tripsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_result, container, false);
        initRecyclerView(view);
        initViewModel();
        return view;
    }

    private void initViewModel() {
        tripResultViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
        tripResultViewModel.getTripsLiveData().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                tripsList.clear();
                tripsList.addAll(trips);
            }
        });
        System.out.println(tripResultViewModel.getTripsLiveData().getValue());
    }

    private void initRecyclerView(View view) {
        resultRecyclerView = view.findViewById(R.id.tripResultRecyclerView);
        resultRecyclerView.setAdapter(new TripResultAdapter(tripsList, getContext()));
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
