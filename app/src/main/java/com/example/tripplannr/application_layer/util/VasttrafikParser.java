package com.example.tripplannr.application_layer.util;

import android.location.Location;

import com.example.tripplannr.domain_layer.ModeOfTransport;
import com.example.tripplannr.domain_layer.Route;
import com.example.tripplannr.domain_layer.TravelTimes;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripLocation;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VasttrafikParser {


    public VasttrafikParser() {}

    public List<Trip> getRoute(String data) throws JSONException {

        List<Trip> trips = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        JSONArray alternatives = jsonObject.getJSONObject("TripList").getJSONArray("Trip");

        // Build trips for each alternative
        for (int i = 0; i < alternatives.length(); i++){
            // Leg is a collection of sub routes
            JSONArray legs = alternatives.getJSONObject(i).getJSONArray("Leg");
            List<Route> routes = new ArrayList<>();
            for (int j = 0; j < legs.length(); j++) {
                // Build route from JSON data
                routes.add(getRoute(legs.getJSONObject(j)));
            }
            Route start_route = routes.get(0);
            Route end_route = routes.get(routes.size() - 1);
            Trip trip = new Trip.Builder()
                    .name(start_route.getOrigin().getName() + " - " +
                            end_route.getDestination().getName())
                    .routes(routes)
                    .origin(start_route.getOrigin())
                    .destination(end_route.getDestination())
                    .times(new TravelTimes(start_route.getTimes().getDeparture(),
                            end_route.getTimes().getArrival()))
                    .build();
            trips.add(trip);
        }
        return trips;
    }

    private Route getRoute(JSONObject route) throws JSONException {
        // TODO, Västtrafik API call using JourneyDetailRef
        List<TripLocation> stops = new ArrayList<>();
//                if(route.has("JourneyDetailRef")) {
//                    String journeyDetailURL = route.getJSONObject("JourneyDetailRef").getString("ref");
//                    stops = getStops(new JSONObject(journeyDetails));
//                }

        // Get origin stop info from JSON
        String origin_name = route.getJSONObject("Origin").getString("name");
        String origin_track = route.getJSONObject("Origin").getString("track");
        LatLng origin_coordinates = getCoordinates(origin_name, stops);
        Location originLocation = new Location("");
        originLocation.setLatitude(origin_coordinates.latitude);
        originLocation.setLongitude(origin_coordinates.longitude);
        TripLocation origin = new TripLocation(origin_name, originLocation, origin_track);

        // Get destination stop info from JSON
        String destination_name = route.getJSONObject("Destination").getString("name");
        String destination_track = route.getJSONObject("Destination").getString("track");
        LatLng destination_coordinates = getCoordinates(destination_name, stops);
        Location destinationLocation = new Location("");
        destinationLocation.setLongitude(destination_coordinates.longitude);
        destinationLocation.setAltitude(destination_coordinates.latitude);
        TripLocation destination = new TripLocation(destination_name, destinationLocation, destination_track);

        // Get origin time info from JSON
        String start_date = route.getJSONObject("Origin").getString("date");
        String start_time = route.getJSONObject("Origin").getString("time");
        LocalDateTime departure = LocalDateTime.parse(start_date + " " + start_time,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));

        // Get destination time info from JSON
        String end_date = route.getJSONObject("Destination").getString("date");
        String end_time = route.getJSONObject("Destination").getString("time");
        LocalDateTime arrival = LocalDateTime.parse(end_date + " " + end_time,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));

        // Get mode of transport from JSON
        String type = route.getString("type");
        ModeOfTransport mode = ModeOfTransport.valueOf(type);

        TravelTimes times = new TravelTimes(departure, arrival);

        return new Route(origin, destination, times, mode);
    }

    private List<TripLocation> getStops(JSONObject journeyDetail) throws JSONException {
        JSONArray stopLocations = journeyDetail.getJSONObject("JourneyDetail").getJSONArray("Stop");
        List<TripLocation> stops = new ArrayList<>();
        for (int i = 0; i < stopLocations.length(); i++) {
            JSONObject stop = stopLocations.getJSONObject(i);
            Location location = new Location("");
            location.setAltitude(stop.getDouble("lat"));
            location.setLongitude(stop.getDouble("lon"));
            stops.add(new TripLocation(stop.getString("name"), location, stop.getString("track")));
        }
        return stops;
    }

    private LatLng getCoordinates(String name, List<TripLocation> stops) {
        for (TripLocation stop: stops){
            if(name.equals(stop.getName()))
                return new LatLng(stop.getLocation().getLatitude(), stop.getLocation().getLongitude());
        }
        // No Match
        return new LatLng(0, 0);
    }
}
