package com.example.tripplannr.application_layer.trip;

import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplannr.R;
import com.example.tripplannr.application_layer.util.ModeOfTransportIconDictionary;
import com.example.tripplannr.databinding.RouteViewHolderBinding;
import com.example.tripplannr.domain_layer.Route;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RouteViewHolder> implements GenericTripAdapter<Route> {

    private List<Route> routes;
    private TripResultViewModel tripResultViewModel;

    public RoutesAdapter(TripResultViewModel viewModel) {
        this.tripResultViewModel = viewModel;
        this.routes = Objects.requireNonNull(viewModel.getTripLiveData().getValue()).getRoutes();
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_view_holder, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        holder.routeViewHolderBinding.setRoute(routes.get(position));
        holder.setIconType(ModeOfTransportIconDictionary.getTransportIcon(routes.get(position).getMode()));
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    @Override
    public void switchPosition(int from, int to) {
        Collections.swap(routes, from, to);
    }

    @Override
    public void updateTrips(final List<Route> newRoutes) {
        DiffUtil.DiffResult diffUtil = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return routes.size();
            }

            @Override
            public int getNewListSize() {
                return newRoutes.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return routes.get(oldItemPosition).equals(newRoutes.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return routes.get(oldItemPosition).equals(newRoutes.get(newItemPosition));
            }
        });
        diffUtil.dispatchUpdatesTo(this);
        routes.clear();
        routes.addAll(newRoutes);
        notifyDataSetChanged();
    }

    @Override
    public List<Route> getData() {
        return Collections.unmodifiableList(routes);
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder {

        private RouteViewHolderBinding routeViewHolderBinding;

        RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            routeViewHolderBinding = DataBindingUtil.bind(itemView);
        }

        public void setIconType(int iconType) {
            routeViewHolderBinding.iconImageView
                    .setImageDrawable(itemView.getContext()
                            .getResources()
                            .getDrawable(iconType, itemView.getContext().getTheme()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tripResultViewModel.updateRoute(routeViewHolderBinding.getRoute());
                }
            });
        }

    }
}
