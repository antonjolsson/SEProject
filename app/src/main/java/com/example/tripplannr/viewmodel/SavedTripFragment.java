package com.example.tripplannr.viewmodel;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplannr.R;
import com.example.tripplannr.view.TripResultAdapter;
import com.example.tripplannr.viewmodel.TripResultViewModel;
import com.example.tripplannr.view.TripResultAdapter;


public class SavedTripFragment extends Fragment {

    private TripResultViewModel viewModel;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_trip, container, false);
        initViewModel();
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.savedTripRecyclerView);
        recyclerView.setAdapter(new TripResultAdapter(viewModel.getSavedTrips()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TripResultViewModel.class);
    }
}
