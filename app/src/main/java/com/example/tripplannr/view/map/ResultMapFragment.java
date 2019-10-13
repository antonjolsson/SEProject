package com.example.tripplannr.view.map;

import android.graphics.Color;
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
import com.google.android.gms.maps.GoogleMap;
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
    private static final int POLYLINE_WIDTH = 15;
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
            getLatLngsFromRoute(points, route);
        }
        centerPoints(points);
    }

    private void centerPoints(List<LatLng> points) {
        LatLngBounds bounds = getLatLngBounds(points);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, MAP_LOC_PADDING));
    }

    private LatLngBounds getLatLngBounds(List<LatLng> points) {
        LatLng[] mostRemotePoints = getLongestDistance(points);
        return LatLngBounds.builder().include(mostRemotePoints[0]).
                include(mostRemotePoints[1]).build();
    }

    private void getLatLngsFromRoute(List<LatLng> points, Route route) {
        points.add(locationToLatlng(route.getOrigin().getLocation()));
        points.add(locationToLatlng(route.getDestination().getLocation()));
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
        final int primaryColor = ContextCompat.getColor(Objects.requireNonNull(getContext()),
                R.color.colorPrimary);
        final int magenta = ContextCompat.getColor(Objects.requireNonNull(getContext()),
                R.color.magenta);
        List<Route> routes = trip.getRoutes();
        PolylineOptions polylineOptions;
        List<PatternItem> dotLine = Arrays.asList(new Dot(), new Gap(14));
        final List<Polyline> polylines = new ArrayList<>();

        for (Route route : routes) {
            polylineOptions = new PolylineOptions();
            polylineOptions.add(new LatLng(route.getOrigin().getLocation().getLatitude(),
                            route.getOrigin().getLocation().getLongitude()),
                    new LatLng(route.getDestination().getLocation().getLatitude(),
                            route.getDestination().getLocation().getLongitude()));
            polylineOptions.color(primaryColor);
            if (route.getMode().equals(WALK)) polylineOptions.pattern(dotLine);
            polylineOptions.width(POLYLINE_WIDTH).clickable(true);
            Polyline polyline = mMap.addPolyline(polylineOptions);
            polylines.add(polyline);
        }
        setPolylineListener(primaryColor, magenta, polylines);
    }

    private void setPolylineListener(final int primaryColor, final int magenta,
                                     final List<Polyline> polylines) {
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline pLine) {
                for (Polyline polyline : polylines) {
                    if (polyline.getId().equals(pLine.getId())) {
                        polyline.setColor(magenta);
                        centerPoints(polyline.getPoints());
                    }
                    else polyline.setColor(primaryColor);
                }
            }
        });
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
