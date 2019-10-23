package com.example.tripplannr.application_layer.trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplannr.R;
import com.example.tripplannr.application_layer.util.InjectorUtils;
import com.example.tripplannr.databinding.FragmentTripResultBinding;
import com.example.tripplannr.domain_layer.Trip;

import java.util.List;


public class TripResultFragment extends Fragment {

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
        tripResultViewModel = InjectorUtils.getTripResultViewModel(getContext(), getActivity());
        tripResultViewModel.getTripsLiveData().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                if (trips.size() > 0) {
                    tripResultBinding.setTrip(trips.get(0));
                    tripResultAdapter.updateTrips(trips);
                } else if(!tripResultViewModel.isLoading().getValue()) {
                    tripResultBinding.setErrorText("Found no trips with that route");
                }
            }
        });
        tripResultViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                tripResultBinding.setIsLoading(aBoolean);
            }
        });
        tripResultViewModel.getStatusCode().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer > 299)
                    tripResultBinding.setErrorText("Error fetching data from server");
                else tripResultBinding.setErrorText("");
            }
        });
    }

    private void initRecyclerView(View view) {
        tripResultAdapter =
                new TripResultAdapter(tripResultViewModel.getTripsLiveData().getValue()
                        , R.id.action_navigation_trip_results_to_navigation_trip_fragment
                        , tripResultViewModel);
        RecyclerView resultRecyclerView = view.findViewById(R.id.tripResultRecyclerView);
        resultRecyclerView.setAdapter(tripResultAdapter);
        resultRecyclerView.setNestedScrollingEnabled(false);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
