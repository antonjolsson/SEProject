package com.example.tripplannr.trip;

import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplannr.model.Location;
import com.example.tripplannr.model.Route;
import com.example.tripplannr.model.TravelTimes;
import com.example.tripplannr.stdanica.R;
import com.example.tripplannr.model.Trip;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class TripFragment extends Fragment {

    private TripResultViewModel tripResultViewModel;

    private RecyclerView routesRecyclerView;

    private List<Route> routes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        initViewModel();
        routes = tripResultViewModel.getTripLiveData().getValue().getRoutes();
        initRecyclerView(view);
        return view;
    }

    private void initViewModel() {
        tripResultViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
        tripResultViewModel.getTripLiveData().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {

            }
        });
    }


    private void initRecyclerView(View view) {
        routesRecyclerView = view.findViewById(R.id.routesRecyclerView);
        routesRecyclerView.setAdapter(new RoutesAdapter(routes));
        routesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
