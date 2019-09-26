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

    private List<String> mStartTimes = new ArrayList<>();
    private List<String> mEndTimes = new ArrayList<>();
    private List<String> mChanges = new ArrayList<>();
    private List<String> mTotalTimes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_result, container, false);
        initRecyclerView(view);
        //populate();
        initViewModel();
        return view;
    }

    private void initViewModel() {
        tripResultViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
        tripResultViewModel.getTripsLiveData().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                mStartTimes.clear();
                mEndTimes.clear();
                mChanges.clear();
                mTotalTimes.clear();
                trips.forEach(new Consumer<Trip>() {
                    @Override
                    public void accept(Trip trip) {
                        mStartTimes.add(trip.getStartTime());
                        mEndTimes.add(trip.getEndTime());
                        mChanges.add(String.valueOf(trip.getChanges()));
                        mTotalTimes.add(String.valueOf(trip.getDuration()));
                    }
                });
            }
        });
        System.out.println(tripResultViewModel.getTripsLiveData().getValue());
    }

    private void initRecyclerView(View view) {
        resultRecyclerView = view.findViewById(R.id.tripResultRecyclerView);
        resultRecyclerView.setAdapter(new TripResultAdapter(mStartTimes, mEndTimes, mChanges, mTotalTimes,getContext()));
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void populate() {
        mStartTimes.addAll(Arrays.asList("13:00", "15:00"));
        mEndTimes.addAll(Arrays.asList("17:00", "18:00"));
        mChanges.addAll(Arrays.asList("1", "2"));
        mTotalTimes.addAll(Arrays.asList("5h", "6h30m"));
    }

}
