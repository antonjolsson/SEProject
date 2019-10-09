package com.example.tripplannr.view.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplannr.databinding.TripResultViewHolderBinding;
import com.example.tripplannr.model.Trip;
import com.example.tripplannr.R;
import com.example.tripplannr.viewmodel.TripResultViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TripResultAdapter extends RecyclerView.Adapter<TripResultAdapter.TripResultViewHolder> {

    private List<Trip> trips;
    private int viewHolderNavigation;

    public TripResultAdapter(List<Trip> trips, int viewHolderNavigation) {
        this.trips = new ArrayList<>(trips);
        this.viewHolderNavigation = viewHolderNavigation;
    }

    @NonNull
    @Override
    public TripResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_result_view_holder, parent, false);
        return new TripResultViewHolder(view, viewHolderNavigation);
    }

    @Override
    public void onBindViewHolder(@NonNull final TripResultViewHolder holder, final int position) {
        holder.tripResultViewHolderBinding.setTrip(trips.get(position));
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public void setTrips(final List<Trip> newTrips) {
        DiffUtil.DiffResult diffUtil = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return trips.size();
            }

            @Override
            public int getNewListSize() {
                return newTrips.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return trips.get(oldItemPosition).equals(newTrips.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return trips.get(oldItemPosition).equals(newTrips.get(newItemPosition));
            }
        });
        diffUtil.dispatchUpdatesTo(this);
        trips.clear();
        trips.addAll(newTrips);
        notifyDataSetChanged();
    }

    public void switchPosition(int from, int to) {
        Collections.swap(trips, from, to);
        notifyDataSetChanged();
    }

    public List<Trip> getData() {
        return Collections.unmodifiableList(trips);
    }

    public class TripResultViewHolder extends RecyclerView.ViewHolder {

        private TripResultViewHolderBinding tripResultViewHolderBinding;
        private TripResultViewModel tripResultViewModel;
        private int navigation;

        public TripResultViewHolder(@NonNull View itemView, int navigation) {
            super(itemView);
            tripResultViewHolderBinding = DataBindingUtil.bind(itemView);
            this.navigation = navigation;
            Objects.requireNonNull(tripResultViewHolderBinding).setViewHolder(this);
            initViewModel();
        }

        public void navigateToDetailedView(Trip trip) {
            if(navigation != 0) {
                tripResultViewModel.onClick(trip);
                Navigation.findNavController(itemView).navigate(navigation);
            }
        }

        public Trip getContent() {
            return tripResultViewHolderBinding.getTrip();
        }

        private void initViewModel() {
            tripResultViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(TripResultViewModel.class);
        }

    }

}
