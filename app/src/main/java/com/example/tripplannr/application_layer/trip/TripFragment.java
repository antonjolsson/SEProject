package com.example.tripplannr.application_layer.trip;

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

import com.example.tripplannr.R;
import com.example.tripplannr.application_layer.util.InjectorUtils;
import com.example.tripplannr.application_layer.util.ModeOfTransportIconDictionary;
import com.example.tripplannr.databinding.FragmentTripBinding;
import com.example.tripplannr.domain_layer.Trip;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;
import java.util.Observable;


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
        tripResultViewModel = InjectorUtils.getTripResultViewModel(getContext(), getActivity());
        tripData = tripResultViewModel.getTripLiveData().getValue();
    }

    public void activateNotifications(View view) {
        tripBinding.setSaved(true);
        NotificationManagerCompat.from(Objects.requireNonNull(getActivity())).notify(0, getNotification());
        Snackbar
                .make(view, "Trip saved", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tripBinding.setSaved(false);
                    }
                })
                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        if(event == (DISMISS_EVENT_TIMEOUT | DISMISS_EVENT_SWIPE)) {
                            tripResultViewModel.saveTrip(tripData);
                        }
                    }
                })
                .show();
    }

    private Notification getNotification() {
        return new NotificationCompat.Builder(Objects.requireNonNull(getActivity()), "TRIP_CHANNEL")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), ModeOfTransportIconDictionary.getTransportIcon(tripData.getRoutes().get(0).getMode())))
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
        routesRecyclerView.setAdapter(new RoutesAdapter(tripData.getRoutes()));
        routesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}
