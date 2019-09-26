package com.example.tripplannr.trip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplannr.model.Trip;
import com.example.tripplannr.stdanica.R;

import java.util.List;

public class TripResultAdapter extends RecyclerView.Adapter<TripResultAdapter.TripResultViewHolder> {

    private List<String> mStartTimes;
    private List<String> mEndTimes;
    private List<String> mChanges;
    private List<String> mTotalTimes;
    private TripActivity context;

    public TripResultAdapter(List<String> mStartTimes, List<String> mEndTimes, List<String> mChanges, List<String> mTotalTimes, Context context) {
        this.mStartTimes = mStartTimes;
        this.mEndTimes = mEndTimes;
        this.mChanges = mChanges;
        this.mTotalTimes = mTotalTimes;
        if(context instanceof TripActivity)
            this.context = (TripActivity) context;
        else throw new RuntimeException("Context is not an instance of TripActivity");
    }

    @NonNull
    @Override
    public TripResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_result_view_holder, parent, false);
        return new TripResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TripResultViewHolder holder, final int position) {
        holder.mStartTimeTextView.setText(mStartTimes.get(position));
        holder.mEndTimeTextView.setText(mEndTimes.get(position));
        holder.mTotalTimeTextView.setText(String.format("Total time: %s", mTotalTimes.get(position)));
        holder.mChangesTextView.setText(String.format("Changes: %s", mChanges.get(position)));
        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailedView();
                Trip trip = holder.tripResultViewModel
                        .getTripLiveData()
                        .getValue();
                if(trip != null) {
                    trip.setStartTime(mStartTimes.get(position));
                    trip.setEndTime(mEndTimes.get(position));
                    trip.setDuration(Long.parseLong(mTotalTimes.get(position)));
                    trip.setChanges(Integer.parseInt(mChanges.get(position)));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTotalTimes.size();
    }

    private void openDetailedView() {
        TripFragment tripFragment = new TripFragment();
        context.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, tripFragment)
                .commit();
    }

    public class TripResultViewHolder extends RecyclerView.ViewHolder {

        private TextView mStartTimeTextView;
        private TextView mEndTimeTextView;
        private TextView mChangesTextView;
        private TextView mTotalTimeTextView;
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
            mStartTimeTextView = itemView.findViewById(R.id.startTimeTextView);
            mEndTimeTextView = itemView.findViewById(R.id.endTimeTextView);
            mChangesTextView = itemView.findViewById(R.id.changesTextView);
            mTotalTimeTextView = itemView.findViewById(R.id.totalTimeTextView);
            mParentLayout = itemView.findViewById(R.id.resultViewParentLayout);
        }
    }

}
