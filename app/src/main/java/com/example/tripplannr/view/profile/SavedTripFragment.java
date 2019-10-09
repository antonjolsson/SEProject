package com.example.tripplannr.view.profile;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplannr.R;
import com.example.tripplannr.model.Trip;
import com.example.tripplannr.view.trip.TripResultAdapter;
import com.example.tripplannr.viewmodel.TripResultViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;


public class SavedTripFragment extends Fragment {

    private TripResultViewModel viewModel;
    private RecyclerView recyclerView;
    private TripResultAdapter tripResultAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_trip, container, false);
        initViewModel();
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(final View view) {
        tripResultAdapter = new TripResultAdapter(viewModel.getSavedTrips(), 0);
        recyclerView = view.findViewById(R.id.savedTripRecyclerView);
        recyclerView.setAdapter(tripResultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        final int startPos = viewHolder.getAdapterPosition();
                        final int targetPos = target.getAdapterPosition();
                        tripResultAdapter.switchPosition(startPos, targetPos);
                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                        final List<Trip> backupTrips = viewModel.getSavedTrips();
                        Snackbar snackbar = Snackbar.make(view, "Deleted Trip, Click to undo", Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.RED);
                        snackbar.setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tripResultAdapter.setTrips(backupTrips);
                            }
                        });
                        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                if(event == (DISMISS_EVENT_TIMEOUT | DISMISS_EVENT_SWIPE)) {
                                    viewModel.removeTrip(tripResultAdapter.getData().get(viewHolder.getAdapterPosition()));
                                }
                            }
                        });
                        snackbar.show();
                    }
                })
                .attachToRecyclerView(recyclerView);

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TripResultViewModel.class);
    }
}
