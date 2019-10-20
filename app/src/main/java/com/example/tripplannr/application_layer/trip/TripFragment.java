package com.example.tripplannr.application_layer.trip;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import com.example.tripplannr.R;
import com.example.tripplannr.application_layer.util.InjectorUtils;
import com.example.tripplannr.application_layer.util.ModeOfTransportIconDictionary;
import com.example.tripplannr.data_access_layer.repositories.VasttrafikRepository;
import com.example.tripplannr.databinding.FragmentTripBinding;
import com.example.tripplannr.domain_layer.Route;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripLocation;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class TripFragment extends Fragment {

    private TripResultViewModel tripResultViewModel;

    private RecyclerView routesRecyclerView;

    private FragmentTripBinding tripBinding;

    private Trip tripData;

    private VasttrafikRepository vasttrafikRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tripBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip, container, false);
        tripBinding.setFragment(this);
        View view = tripBinding.getRoot();
        initViewModel();
        tripBinding.setTrip(tripData);
        initRecyclerView(view);
        vasttrafikRepository = new VasttrafikRepository(getContext());
        addJourneyDetails();
        addLegDetails();
        for(Route route : tripData.getRoutes()) {
            if(route.getLocations() != null) {
                for (TripLocation trip : route.getLocations()) {
                    System.out.println(trip.getName());
                    System.out.println(trip.getLocation().getLongitude());
                    System.out.println(trip.getLocation().getLatitude());
                }
            }
        }
        return view;
    }

    private void addLegDetails() {
        for(Route route : tripData.getRoutes())
            vasttrafikRepository.addLegDetails(route.getJourneyRef(), route,
                    tripResultViewModel.getTripLiveData());
    }

    private void addJourneyDetails(){
        for(Route route : tripData.getRoutes())
            vasttrafikRepository.addJourneyDetails(route.getJourneyRef(), route,
                    tripResultViewModel.getTripLiveData());
    }

    private void initViewModel() {
        tripResultViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
        tripResultViewModel.getTripLiveData().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                tripData = trip;
                if (trip.getRoutes().size() > 0) {
                    routesRecyclerView.setAdapter(new RoutesAdapter(tripResultViewModel));
                }
            }
        });
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
                        if (event == (DISMISS_EVENT_TIMEOUT | DISMISS_EVENT_SWIPE)) {
                            tripResultViewModel.saveTrip(tripData);
                        }
                    }
                })
                .show();
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
        routesRecyclerView.setAdapter(new RoutesAdapter(tripResultViewModel));
        routesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void createDialog(String vasttrafik, String stenaLine, View view) {
        TextView message = new TextView(getContext());
        message.setText("Book trip here: \n" +
                vasttrafik + "\n" +
                stenaLine);
        Linkify.addLinks(message, Linkify.WEB_URLS);
        message.setMovementMethod(LinkMovementMethod.getInstance());
        message.setTextSize(18);
        getDialogBuilder(view)
                .setView(message)
                .create()
                .show();

    }

    public void createDialog(String vasttrafik, View view) {
        TextView message = new TextView(getContext());
        message.setText("Book trip here: \n" +
                vasttrafik);
        Linkify.addLinks(message, Linkify.WEB_URLS);
        message.setMovementMethod(LinkMovementMethod.getInstance());
        message.setTextSize(18);
        getDialogBuilder(view)
                .setView(message)
                .create()
                .show();

    }

    private AlertDialog.Builder getDialogBuilder(final View view) {
        return new AlertDialog.Builder(getActivity())
                    .setTitle("Book trip?")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
    }


}
