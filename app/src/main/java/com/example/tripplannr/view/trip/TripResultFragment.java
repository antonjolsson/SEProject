package com.example.tripplannr.view.trip;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplannr.R;
import com.example.tripplannr.databinding.FragmentTripResultBinding;
import com.example.tripplannr.viewmodel.TripResultViewModel;
import com.example.tripplannr.model.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TripResultFragment extends Fragment {

    private RecyclerView resultRecyclerView;

    private TripResultViewModel tripResultViewModel;

    private FragmentTripResultBinding tripResultBinding;

    private TripResultAdapter tripResultAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tripResultBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_result, container, false);
        initViewModel();
        View view = tripResultBinding.getRoot();
        initRecyclerView(view);
        return view;
    }

    private void initViewModel() {
        tripResultViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
        tripResultViewModel.getTripsLiveData().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                tripResultBinding.setErrorText("");
                if(trips.size() > 0) {
                    tripResultBinding.setTrip(trips.get(0));
                    tripResultAdapter.setTrips(trips);
                }
                else {
                    tripResultAdapter.setTrips(trips);
                    tripResultBinding.setErrorText("Error fetching data \n from the Server");
                }
            }
        });
        tripResultViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                tripResultBinding.setIsLoading(aBoolean);
            }
        });
    }

    private void initRecyclerView(View view) {
        tripResultAdapter = new TripResultAdapter(tripResultViewModel.getTripsLiveData().getValue(), R.id.action_navigation_trip_results_to_navigation_trip_fragment);
        resultRecyclerView = view.findViewById(R.id.tripResultRecyclerView);
        resultRecyclerView.setAdapter(tripResultAdapter);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
