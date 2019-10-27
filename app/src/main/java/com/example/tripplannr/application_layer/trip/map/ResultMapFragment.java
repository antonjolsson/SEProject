package com.example.tripplannr.application_layer.trip.map;

import android.location.Location;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.application_layer.addressservice.LocationService;
import com.example.tripplannr.application_layer.trip.TripResultViewModel;
import com.example.tripplannr.domain_layer.Locatable;
import com.example.tripplannr.domain_layer.Route;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripLocation;
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

import static com.example.tripplannr.application_layer.search.SearchViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.application_layer.search.SearchViewModel.LocationField.ORIGIN;
import static com.example.tripplannr.application_layer.util.Utilities.locationToLatlng;
import static com.example.tripplannr.domain_layer.ModeOfTransport.WALK;

public class ResultMapFragment extends MapFragment {

    // Padding between map edge and itinerary locations in initial view
    private static final int MAP_LOC_PADDING = 120;
    private static final int POLYLINE_WIDTH = 15;
    private final List<Polyline> polyLines = new ArrayList<>();
    private int commonPolylineColor;
    private int focusedPolylineColor;

    private TripResultViewModel viewModel;
    private List<Route> routes;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.
                of(Objects.requireNonNull(getActivity())).get(TripResultViewModel.class);
        commonPolylineColor = ContextCompat.getColor(Objects.requireNonNull(getContext()),
                R.color.colorPrimary);
        focusedPolylineColor = ContextCompat.getColor(Objects.requireNonNull(getContext()),
                R.color.magenta);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        viewModel.getTripLiveData().observe(this, new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                if (allRoutesHaveLocations(trip))
                    drawTrip(trip);
            }
        });
        viewModel.getRouteLiveData().observe(this, new Observer<Route>() {
            @Override
            public void onChanged(Route route) {
                List<Route> rs = Objects.requireNonNull(viewModel.getTripLiveData().getValue()).
                        getRoutes();
                int routeIndex = 0;
                for (int i = 0; i < rs.size(); i++) {
                    if (route.equals(rs.get(i))) {
                        routeIndex = i;
                        break;
                    }
                }
                if (routeIndex < polyLines.size()) focusPolyline(polyLines.get(routeIndex));
            }
        });
    }

    private boolean allRoutesHaveLocations(Trip trip) {
        for (Route route : trip.getRoutes()) {
            if (route.getMode() == WALK) continue;
            if (route.getStops() == null && route.getLegs() == null) return false;
        }
        return true;
    }

    private void drawTrip(Trip trip) {
        drawMarkers(trip);
        drawPolyLines(trip);
        centerItinerary(trip);
    }

    private void centerItinerary(Trip trip) {
        List<LatLng> points = new ArrayList<>();
        points.add(locationToLatlng(trip.getOrigin().getLocation()));
        points.add(locationToLatlng(trip.getDestination().getLocation()));
        for (Route route : trip.getRoutes()) {
            getCoordinatesFromRoute(points, route);
        }
        centerPoints(points);
    }

    private void centerPoints(List<LatLng> points) {
        LatLngBounds bounds = getLatLngBounds(points);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, MAP_LOC_PADDING));
    }

    private LatLngBounds getLatLngBounds(List<LatLng> points) {
        double westernmost = 0, northernmost = 0, easternmost = 0, southernmost = 0;
        double lat, lng;
        for (int i = 0; i < points.size(); i++) {
            lat = points.get(i).latitude;
            lng = points.get(i).longitude;
            if (i == 0) {
                westernmost = lng;
                easternmost = lng;
                northernmost = lat;
                southernmost = lat;
            } else {
                if (lng < westernmost) westernmost = lng;
                if (lng > easternmost) easternmost = lng;
                if (lat > northernmost) northernmost = lat;
                if (lat < southernmost) southernmost = lat;
            }
        }
        LatLng sw = new LatLng(southernmost, westernmost);
        LatLng ne = new LatLng(northernmost, easternmost);
        return LatLngBounds.builder().include(sw).include(ne).build();
    }

    private void getCoordinatesFromRoute(List<LatLng> points, Route route) {
        points.add(locationToLatlng(route.getOrigin().getLocation()));
        points.add(locationToLatlng(route.getDestination().getLocation()));
    }

    private void drawPolyLines(Trip trip) {
        List<Route> routes = trip.getRoutes();
        PolylineOptions polylineOptions;
        List<PatternItem> dotLine = Arrays.asList(new Dot(), new Gap(14));

        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            polylineOptions = new PolylineOptions();
            if (route.getMode() == WALK) replaceLocations(route);
            addPoints(polylineOptions, route);
            polylineOptions.color(commonPolylineColor);
            if (route.getMode().equals(WALK)) polylineOptions.pattern(dotLine);
            polylineOptions.width(POLYLINE_WIDTH).clickable(true);
            Polyline polyline = mMap.addPolyline(polylineOptions);
            polyLines.add(polyline);
        }
        setPolylineListener(polyLines);
    }

    // todo: implement
    private void replaceWalkLocations(Trip trip, Route route, int index) {
        if (route.getOrigin().getLocation().getLatitude() < 0.01) {
            if (index > 0 && trip.getRoutes().get(index - 1).
                    getOrigin().getLocation().getLatitude() > 0.01) {
                route.setOrigin(trip.getRoutes().get(index - 1).
                        getOrigin());
            }
        }
    }

    // Tries first to add leg points, else stop points, else origin/dest points
    private void addPoints(PolylineOptions polylineOptions, Route route) {
        List<LatLng> latLngs = new ArrayList<>();
        if (route.getLegs() != null) {
            for (Location leg : route.getLegs()) {
                latLngs.add(locationToLatlng(leg));
            }
        } else if (route.getStops() == null) {
            latLngs.add(locationToLatlng(route.getOrigin().getLocation()));
            latLngs.add(locationToLatlng(route.getDestination().getLocation()));
        } else for (TripLocation location : route.getStops()) {
            latLngs.add(locationToLatlng((location.getLocation())));
        }
        addLatLng(polylineOptions, latLngs);
    }

    private void addLatLng(PolylineOptions polylineOptions, List<LatLng> latLngs) {
        for (LatLng latLng : latLngs) {
            polylineOptions.add(latLng);
        }
    }

    // Todo: remove when all Routes/Trips have valid Locations
    private void replaceLocations(Locatable locatable) {
        Location location = locatable.getOrigin().getLocation();
        if (location.getLatitude() < 0.001)
            location = LocationService.getLocation(locatable.getOrigin().getName(), getContext());
        locatable.setOrigin(new TripLocation(locatable.getOrigin().getName(), location,
                locatable.getOrigin().getTrack()));
        location = locatable.getDestination().getLocation();
        if (location.getLatitude() < 0.001)
            location = LocationService.getLocation(locatable.getDestination().getName(), getContext());
        locatable.setDestination(new TripLocation(locatable.getDestination().getName(), location,
                locatable.getDestination().getTrack()));
    }

    private void setPolylineListener(final List<Polyline> polyLines) {
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline pLine) {
                for (Polyline polyline : polyLines) {
                    if (polyline.getId().equals(pLine.getId())) {
                        focusPolyline(polyline);
                        break;
                    }
                }
            }
        });
    }

    private void focusPolyline(Polyline polyline) {
        for (Polyline pLine : polyLines) {
            if (pLine.equals(polyline)) {
                polyline.setColor(focusedPolylineColor);
                centerPoints(polyline.getPoints());
            } else pLine.setColor(commonPolylineColor);
        }
    }

    private void drawMarkers(Trip trip) {
        replaceLocations(trip);
        model.setFocusedLocationField(ORIGIN);
        model.setLocation(trip.getOrigin().getLocation(), null);
        model.setFocusedLocationField(DESTINATION);
        model.setLocation(trip.getDestination().getLocation(), null);
    }

}
