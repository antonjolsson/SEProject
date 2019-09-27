package com.example.tripplannr.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplannr.model.Route;
import com.example.tripplannr.stdanica.R;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RouteViewHolder> {

    private List<Route> routes = new ArrayList<>();

    public RoutesAdapter(List<Route> routes) {
        this.routes = routes;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_view_holder, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        holder.startTimeTextView.setText(routes.get(position).getTimes().getDeparture().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        holder.endTimeTextView.setText(routes.get(position).getTimes().getArrival().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        holder.transportTextView.setText(routes.get(position).getMode().toString());
        holder.originTextView.setText(routes.get(position).getOrigin().getName());
        holder.destinationTextView.setText(routes.get(position).getDestination().getName());
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder {

        private TextView startTimeTextView, endTimeTextView, transportTextView, originTextView, destinationTextView;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents(itemView);
        }

        private void initComponents(View view) {
            startTimeTextView = view.findViewById(R.id.startTimeTextView);
            endTimeTextView = view.findViewById(R.id.endTimeTextView);
            transportTextView = view.findViewById(R.id.transportTextView);
            originTextView = view.findViewById(R.id.originTextView);
            destinationTextView = view.findViewById(R.id.destinationTextView);
        }
    }
}
