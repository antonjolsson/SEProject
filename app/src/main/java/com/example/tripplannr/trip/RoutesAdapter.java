package com.example.tripplannr.trip;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplannr.model.Route;
import com.example.tripplannr.stdanica.R;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RouteViewHolder> {

    private List<Route> routes;

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
        holder.startTimeTextView.setText(routes.get(position).getTimes().getDeparture().format(DateTimeFormatter.ofPattern("HH:mm")));
        holder.endTimeTextView.setText(routes.get(position).getTimes().getArrival().format(DateTimeFormatter.ofPattern("HH:mm")));
        holder.originTextView.setText(routes.get(position).getOrigin().getName());
        holder.destinationTextView.setText(routes.get(position).getDestination().getName());
        switch (routes.get(position).getMode()) {
            case BUS:
                holder.iconImageView.setImageDrawable(holder.fragmentActivity.getDrawable(R.drawable.bus));
                break;
            case TRAM:
                holder.iconImageView.setImageDrawable(holder.fragmentActivity.getDrawable(R.drawable.tram));
                break;
            case WALK:
                holder.iconImageView.setImageDrawable(holder.fragmentActivity.getDrawable(R.drawable.walk));
                break;
            case FERRY:
                holder.iconImageView.setImageDrawable(holder.fragmentActivity.getDrawable(R.drawable.boat));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder {

        private TextView startTimeTextView, endTimeTextView,  originTextView, destinationTextView;
        private ImageView iconImageView;
        private FragmentActivity fragmentActivity;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents(itemView);
            fragmentActivity = (FragmentActivity) itemView.getContext();
        }

        private void initComponents(View view) {
            startTimeTextView = view.findViewById(R.id.startTimeTextView);
            endTimeTextView = view.findViewById(R.id.timeTextView);
            originTextView = view.findViewById(R.id.originTextView);
            destinationTextView = view.findViewById(R.id.destinationTextView);
            iconImageView = view.findViewById(R.id.iconImageView);
        }
    }
}
