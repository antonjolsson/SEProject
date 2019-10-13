package com.example.tripplannr.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplannr.R;
import com.example.tripplannr.databinding.RouteViewHolderBinding;
import com.example.tripplannr.model.tripdata.Route;
import com.example.tripplannr.viewmodel.TripResultViewModel;

import java.util.List;
import java.util.Objects;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RouteViewHolder> {

    private List<Route> routes;
    private TripResultViewModel tripResultViewModel;

    RoutesAdapter(TripResultViewModel viewModel) {
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
        holder.routeViewHolderBinding.setIconType(routes.get(position).getMode());
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    class RouteViewHolder extends RecyclerView.ViewHolder {

        private RouteViewHolderBinding routeViewHolderBinding;

        RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            routeViewHolderBinding = DataBindingUtil.bind(itemView);
            Objects.requireNonNull(routeViewHolderBinding).setFragmentActivity((FragmentActivity)
                    itemView.getContext());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tripResultViewModel.setSelectedRoute(routeViewHolderBinding.getRoute());
                }
            });
        }

    }
}
