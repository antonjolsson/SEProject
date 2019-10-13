package com.example.tripplannr.view;

import android.app.Notification;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tripplannr.R;
import com.example.tripplannr.databinding.FragmentTripBinding;
import com.example.tripplannr.viewmodel.TripResultViewModel;
import com.example.tripplannr.model.tripdata.Route;
import com.example.tripplannr.model.Trip;

import java.util.List;
import java.util.Objects;


public class TripFragment extends Fragment {

    private TripResultViewModel tripResultViewModel;

    private RecyclerView routesRecyclerView;

    private FragmentTripBinding tripBinding;

    private Trip tripData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tripBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip, container, false);
        tripBinding.setFragment(this);
        View view = tripBinding.getRoot();
        initViewModel();
        initRecyclerView(view);
        return view;
    }

    private void initViewModel() {
        tripResultViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
        tripResultViewModel.getTripLiveData().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                tripData = trip;
                if(trip.getRoutes().size() > 0) {
                    routesRecyclerView.setAdapter(new RoutesAdapter(tripResultViewModel));
                }
            }
        });
    }

    public void activateNotifications() {
        if(tripResultViewModel.saveTrip(tripData)) {
            NotificationManagerCompat.from(Objects.requireNonNull(getActivity())).notify(0, getNotification());
            tripBinding.setSaved(true);
        }
        //ToDo: add some error text I guess
    }

    private Notification getNotification() {
        return new NotificationCompat.Builder(Objects.requireNonNull(getActivity()), "TRIP_CHANNEL")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        ModeOfTransportIconDictionary.getTransportIcon(tripData.getRoutes().get(0).getMode())))
                .setContentTitle("Notification about your Trip")
                .setContentText("Your trip \"" + tripData.getName() + "\" has been saved to your profile," +
                        " you will now be receiving notifications related to this trip")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle())
                .build();
    }

    private void initRecyclerView(View view) {
        routesRecyclerView = view.findViewById(R.id.routesRecyclerView);
        routesRecyclerView.setAdapter(new RoutesAdapter(Objects.requireNonNull(tripResultViewModel)));
        routesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}
