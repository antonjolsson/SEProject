package com.example.tripplannr.view;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplannr.R;
import com.example.tripplannr.model.Trip;
import com.example.tripplannr.viewmodel.TripResultViewModel;

import java.util.Objects;


public class SavedTripDetails extends Fragment {

    private TripResultViewModel viewModel;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_trip_details, container, false);
        initViewModel();
        initRecyclerView(view);
        return view;
    }

    private void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(TripResultViewModel.class);
    }



    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.routesRecyclerView);
        recyclerView.setAdapter(new RoutesAdapter(viewModel.getSavedTrips().getRoutes()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void deleteSavedTrip(){
        NotificationManagerCompat.from(Objects.requireNonNull(getActivity())).notify(0, getNotification());
        //TODO delete saved trip
    }

    private Notification getNotificationt() {
        return new NotificationCompat.Builder(Objects.requireNonNull(getActivity()), "TRIP_CHANNEL")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), ModeOfTransportIconDictionary.getTransportIcon(tripData.getRoutes().get(0).getMode())))
                .setContentTitle("Notification about your Trip")
                .setContentText("Your trip \"" + tripData.getName() + "\" has been deleted from save trips," +
                        " you will no longer be receiving notifications related to this trip")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle())
                .build();
    }


}
