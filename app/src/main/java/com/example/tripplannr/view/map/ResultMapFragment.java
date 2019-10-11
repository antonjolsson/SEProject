package com.example.tripplannr.view.map;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.model.Trip;
import com.example.tripplannr.model.tripdata.Route;
import com.example.tripplannr.model.tripdata.TravelTimes;
import com.example.tripplannr.model.tripdata.TripLocation;
import com.example.tripplannr.viewmodel.TripResultViewModel;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.tripplannr.model.tripdata.ModeOfTransport.WALK;
import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.ORIGIN;

public class ResultMapFragment extends MapFragment {

    private TripResultViewModel tripResultViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripResultViewModel = ViewModelProviders.
                of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
    }

    @Override
    void setListeners() {
        tripResultViewModel.getTripLiveData().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                drawTrip(trip);
            }
        });
    }

    private void drawTrip(Trip trip) {
        trip = getFakeTrip(trip);
        drawMarkers(trip);
        drawPolyLines(trip);
    }

    private void drawPolyLines(Trip trip) {
        List<Route> routes = trip.getRoutes();
        PolylineOptions polylineOptions = new PolylineOptions();
        List<PatternItem> dotLine = Arrays.asList(new Dot(), new Gap(14));

        for (Route route : routes) {
            polylineOptions.add(new LatLng(route.getOrigin().getLocation().getLatitude(),
                            route.getOrigin().getLocation().getLongitude()),
                    new LatLng(route.getDestination().getLocation().getLatitude(),
                            route.getDestination().getLocation().getLongitude()));
            Polyline polyline = mMap.addPolyline(polylineOptions);
            if (route.getMode().equals(WALK)) polyline.setPattern(dotLine);
            polyline.setColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),
                    R.color.colorPrimary));
            polyline.setWidth(15);
            polyline.setClickable(true);
        }
    }

    private void drawMarkers(Trip trip) {
        model.setFocusedLocationField(ORIGIN);
        model.setLocation(trip.getOrigin().getLocation(), null);
        model.setFocusedLocationField(DESTINATION);
        model.setLocation(trip.getDestination().getLocation(), null);
    }

    // TODO: remove this when trips contain Location data
    private Trip getFakeTrip(Trip trip) {
        Location originLoc = makeLocation(57.706998, 11.938496);
        TripLocation originTripLocation = new TripLocation(trip.getOrigin().getName(), originLoc,
                trip.getOrigin().getTrack());
        Location destLoc = makeLocation(57.687775, 11.979341);
        TripLocation destTripLocation = new TripLocation(trip.getDestination().getName(), destLoc,
                trip.getDestination().getTrack());
        List<Route> routes = getRoutes(trip.getRoutes());
        return new Trip(trip.getName(), routes, originTripLocation,
                destTripLocation, trip.getTimes());
    }

    private List<Route> getRoutes(List<Route> routes) {
        List<Route> routes2 = new ArrayList<>();
        Location originLoc = makeLocation(57.706998, 11.938496);
        Location destLoc = makeLocation(57.689986, 11.972968);
        TripLocation t1 = new TripLocation(routes.get(0).getOrigin().getName(),
                originLoc, "30");
        TripLocation t2 = new TripLocation(routes.get(0).getDestination().getName(),
                destLoc, "D");
        routes2.add(new Route(t1, t2, routes.get(0).getTimes(), routes.get(1).getMode()));

        t1 = new TripLocation(routes.get(0).getDestination().getName(),
                destLoc, "D");
        destLoc = makeLocation(57.687775, 11.979341);
        t2 = new TripLocation("Chalmers, Göteborg", destLoc, null);
        routes2.add(new Route(t1, t2, routes.get(0).getTimes(), WALK));

        return routes2;
    }

    private Location makeLocation(double lat, double lng) {
        Location originLoc = new Location("");
        originLoc.setLatitude(lat);
        originLoc.setLongitude(lng);
        return originLoc;
    }

}
