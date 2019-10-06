package com.example.tripplannr.controller.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplannr.databinding.TripResultViewHolderBinding;
import com.example.tripplannr.model.Trip;
import com.example.tripplannr.R;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TripResultAdapter extends RecyclerView.Adapter<TripResultAdapter.TripResultViewHolder> {

    private List<Trip> trips;

    public TripResultAdapter(List<Trip> trips) {
        this.trips = trips;
    }

    @NonNull
    @Override
    public TripResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_result_view_holder, parent, false);
        return new TripResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TripResultViewHolder holder, final int position) {
        holder.tripResultViewHolderBinding.setTrip(trips.get(position));
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }


    public class TripResultViewHolder extends RecyclerView.ViewHolder {

        private TripResultViewHolderBinding tripResultViewHolderBinding;
        private TripResultViewModel tripResultViewModel;

        public TripResultViewHolder(@NonNull View itemView) {
            super(itemView);
            tripResultViewHolderBinding = DataBindingUtil.bind(itemView);
            tripResultViewHolderBinding.setViewHolder(this);
            initViewModel();
        }

        public void navigateToDetailedView(Trip trip) {
            tripResultViewModel.onClick(trip);
            Navigation.findNavController(itemView).navigate(R.id.action_navigation_trip_results_to_navigation_trip_fragment);
        }

        private void initViewModel() {
            tripResultViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(TripResultViewModel.class);
        }

    }

}
