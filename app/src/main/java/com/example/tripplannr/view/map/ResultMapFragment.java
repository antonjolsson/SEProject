package com.example.tripplannr.view.map;

import android.location.Location;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.model.Trip;
import com.example.tripplannr.model.Utilities;
import com.example.tripplannr.model.tripdata.Route;
import com.example.tripplannr.model.tripdata.TripLocation;
import com.example.tripplannr.viewmodel.TripResultViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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

    // Padding between map edge and itinerary locations in initial view
    private static final int MAP_LOC_PADDING = 120;
    private TripResultViewModel tripResultViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripResultViewModel = ViewModelProviders.
                of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
    }

    @Override
    void setListeners() {
        super.setListeners();
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
        centerItinerary(trip);
    }

    private void centerItinerary(Trip trip) {
        List<LatLng> points = new ArrayList<>();
        points.add(locationToLatlng(trip.getOrigin().getLocation()));
        points.add(locationToLatlng(trip.getDestination().getLocation()));
        for (Route route : trip.getRoutes()) {
            points.add(locationToLatlng(route.getOrigin().getLocation()));
            points.add(locationToLatlng(route.getDestination().getLocation()));
        }
        LatLng[] mostRemotePoints = getLongestDistance(points);
        LatLngBounds bounds = LatLngBounds.builder().include(mostRemotePoints[0]).
                include(mostRemotePoints[1]).build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, MAP_LOC_PADDING));
    }

    private LatLng[] getLongestDistance(List<LatLng> points) {
        float[] results = new float[3];
        LatLng[] mostRemotePoints = new LatLng[2];
        double longestDist = 0;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Location.distanceBetween(points.get(i).latitude, points.get(i).longitude,
                        points.get(j).latitude, points.get(j).longitude, results);
                if (results[0] > longestDist) {
                    longestDist = results[0];
                    mostRemotePoints[0] = points.get(i);
                    mostRemotePoints[1] = points.get(j);
                }
            }
        }
        return mostRemotePoints;
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

    private static LatLng locationToLatlng(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        return new LatLng(latitude, longitude);
    }

}
