package com.example.tripplannr.controller.trip;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tripplannr.R;
import com.example.tripplannr.model.Trip;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TripResultFragment extends Fragment {

    private RecyclerView resultRecyclerView;

    private TripResultViewModel tripResultViewModel;

    private TextView originTextView, destinationTextView, whenTextView, errorTextView;

    private ProgressBar isLoadingProgressBar;

    private List<Trip> tripsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_result, container, false);
        initRecyclerView(view);
        initComponents(view);
        initViewModel();
        return view;
    }

    private void initComponents(View view) {
        originTextView = view.findViewById(R.id.originTextView);
        destinationTextView = view.findViewById(R.id.destinationTextView);
        whenTextView = view.findViewById(R.id.whenTextView);
        isLoadingProgressBar = view.findViewById(R.id.isLoadingProgressBar);
        errorTextView = view.findViewById(R.id.errorTextView);
    }

    private void initViewModel() {
        tripResultViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
        tripResultViewModel.setContext(getContext());
        tripResultViewModel.buildFakeStenaTrip();
        tripResultViewModel.getTripsLiveData().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                tripsList.clear();
                tripsList.addAll(trips);
                errorTextView.setText("");
                if(trips.size() > 0) {
                    originTextView.setText(Html.fromHtml("<b>From: </b>", Html.FROM_HTML_MODE_LEGACY) + trips.get(0).getOrigin().getName());
                    destinationTextView.setText(Html.fromHtml("<b>To: </b>", Html.FROM_HTML_MODE_LEGACY) + trips.get(0).getDestination().getName());
                    whenTextView.setText(Html.fromHtml("<b>When: </b>", Html.FROM_HTML_MODE_LEGACY) + trips.get(0).getTimes().getDeparture().format(DateTimeFormatter.ofPattern("dd-MM-yy")));
                    resultRecyclerView.setAdapter(new TripResultAdapter(trips));
                }
                else {
                    resultRecyclerView.setAdapter(new TripResultAdapter(trips));
                    errorTextView.setText("Error fetching data \n from the Server");
                }
            }
        });
        tripResultViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) isLoadingProgressBar.setVisibility(View.VISIBLE);
                else isLoadingProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void initRecyclerView(View view) {
        resultRecyclerView = view.findViewById(R.id.tripResultRecyclerView);
        resultRecyclerView.setAdapter(new TripResultAdapter(tripsList));
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
