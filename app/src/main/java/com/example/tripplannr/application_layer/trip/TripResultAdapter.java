package com.example.tripplannr.application_layer.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplannr.R;
import com.example.tripplannr.databinding.TripResultViewHolderBinding;
import com.example.tripplannr.domain_layer.Trip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TripResultAdapter extends RecyclerView.Adapter<TripResultAdapter.TripResultViewHolder> implements GenericTripAdapter<Trip> {

    private List<Trip> trips;
    private int viewHolderNavigation;
    private TripResultViewModel viewModel;

    public TripResultAdapter(List<Trip> trips, int viewHolderNavigation, TripResultViewModel viewModel) {
        if (trips != null) this.trips = new ArrayList<>(trips);
        else this.trips = new ArrayList<>();
        this.viewModel = viewModel;
        this.viewHolderNavigation = viewHolderNavigation;
    }

    @NonNull
    @Override
    public TripResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_result_view_holder, parent, false);
        return new TripResultViewHolder(view, viewHolderNavigation, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull final TripResultViewHolder holder, final int position) {
        holder.tripResultViewHolderBinding.setTrip(trips.get(position));
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    @Override
    public void updateTrips(final List<Trip> newTrips) {
        trips.clear();
        trips.addAll(newTrips);
        notifyDataSetChanged();
    }

    @Override
    public void switchPosition(int from, int to) {
        Collections.swap(trips, from, to);
        notifyDataSetChanged();
    }

    @Override
    public List<Trip> getData() {
        return Collections.unmodifiableList(trips);
    }

    public class TripResultViewHolder extends RecyclerView.ViewHolder {

        private TripResultViewHolderBinding tripResultViewHolderBinding;
        private TripResultViewModel viewModel;
        private int navigation;

        public TripResultViewHolder(@NonNull View itemView, int navigation, TripResultViewModel viewModel) {
            super(itemView);
            tripResultViewHolderBinding = DataBindingUtil.bind(itemView);
            this.viewModel = viewModel;
            this.navigation = navigation;
            Objects.requireNonNull(tripResultViewHolderBinding).setViewHolder(this);
        }

        public void navigateToDetailedView(Trip trip) {
            if (navigation != 0) {
                viewModel.onClick(trip);
                Navigation.findNavController(itemView).navigate(navigation);
            }
        }

        public Trip getContent() {
            return tripResultViewHolderBinding.getTrip();
        }

    }

}
