package com.example.tripplannr.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplannr.model.Trip;
import com.example.tripplannr.stdanica.R;

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
        holder.mTimeTextView.setText(
                "When: "
                + trips.get(position).getTimes().getDeparture().format(DateTimeFormatter.ofPattern("HH:mm"))
                + " - "
                + trips.get(position).getTimes().getArrival().format(DateTimeFormatter.ofPattern("HH:mm")));
        holder.mStopTextView.setText(trips.get(position).getOrigin().getName());
        holder.mTotalTimeTextView.setText(String.format("Total time: %s %s", trips.get(position).getTimes().getDuration(), "minutes"));
        holder.mChangesTextView.setText(String.format("Changes: %s", trips.get(position).getRoutes().size()));
        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tripResultViewModel.onClick(trips.get(position));
                Navigation.findNavController(v).navigate(R.id.action_navigation_trip_results_to_navigation_trip_fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }


    public class TripResultViewHolder extends RecyclerView.ViewHolder {

        private TextView mTimeTextView, mChangesTextView, mTotalTimeTextView, mStopTextView;
        private ConstraintLayout mParentLayout;
        private View itemView;
        private TripResultViewModel tripResultViewModel;

        public TripResultViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            initComponents();
            initViewModel();
        }

        private void initViewModel() {
            tripResultViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(TripResultViewModel.class);
        }

        private void initComponents() {
            mTimeTextView = itemView.findViewById(R.id.timeTextView);
            mChangesTextView = itemView.findViewById(R.id.changesTextView);
            mTotalTimeTextView = itemView.findViewById(R.id.totalTimeTextView);
            mParentLayout = itemView.findViewById(R.id.resultViewParentLayout);
            mStopTextView = itemView.findViewById(R.id.stopTextView);
        }
    }

}
