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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VasttrafikParser implements TripParser{


    public VasttrafikParser() {}

    public List<TripLocation> getMatching(String data) throws JSONException {
        List<TripLocation> locations = new ArrayList<>();

        JSONObject json = new JSONObject(data);
        JSONArray stops = new JSONArray();
        try {
            stops = json.getJSONObject("LocationList").getJSONArray("StopLocation");
        }
        // Vasttrafik sometimes break there own JSON model and sends singles as object...
        catch (JSONException e) {
            stops.put(json.getJSONObject("LocationList").getJSONObject("StopLocation"));
        }
        if(json.getJSONObject("LocationList").has("CoordLocation")) {
            try {
                JSONArray others = json.getJSONObject("LocationList").getJSONArray("CoordLocation");
                for (int i = 0; i < others.length(); i++) {
                    stops.put(others.getJSONObject(i));
                }
            }
            // Vasttrafik sometimes break there own JSON model and sends singles as object...
            catch (JSONException e) {
                stops.put(json.getJSONObject("LocationList").getJSONObject("CoordLocation"));
            }
        }

        for (int i = 0; i < stops.length(); i++){
            Location location = new Location("");
            location.setLongitude(stops.getJSONObject(i).getDouble("lon"));
            location.setLatitude(stops.getJSONObject(i).getDouble("lat"));
            locations.add(new TripLocation(stops.getJSONObject(i).getString("name"), location));
        }
        return locations;
    }

    public List<TripLocation> getJourneyDetail(String data) throws JSONException {
        List<TripLocation> journeyDetails = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);
        JSONArray stopLocations = jsonObject.getJSONObject("JourneyDetail").getJSONArray("Stop");

        // Get all stop coordinates and names
        for (int i = 0; i < stopLocations.length(); i++) {
            JSONObject stop = stopLocations.getJSONObject(i);
            Location location = new Location("");
            location.setAltitude(stop.getDouble("lat"));
            location.setLongitude(stop.getDouble("lon"));
            journeyDetails.add(new TripLocation(stop.getString("name"), location, stop.getString("track")));
        }

        return journeyDetails;
    }

    public List<LatLng> getPoints(String data) throws JSONException {
        List<LatLng> points = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonPoints = jsonObject.getJSONObject("Points").getJSONArray("Point");

        for (int i = 0; i < jsonPoints.length(); i++){
            JSONObject coordinates = jsonPoints.getJSONObject(i);
            points.add(new LatLng(coordinates.getDouble("lat"), coordinates.getDouble("lon")));

        }

        return points;
    }

    @Override
    public List<Trip> getTrips(String data) throws JSONException {
        List<Trip> trips = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        JSONArray alternatives = jsonObject.getJSONObject("TripList").getJSONArray("Trip");

        // Build trips for each alternative
        for (int i = 0; i < alternatives.length(); i++){
            List<Route> routes = new ArrayList<>();
            // Leg is a collection of sub routes
            try {
                JSONArray legs = alternatives.getJSONObject(i).getJSONArray("Leg");
                for (int j = 0; j < legs.length(); j++) {
                    // Build route from JSON data
                    routes.add(getRoute(legs.getJSONObject(j)));
                }
            }
            // Vasttrafik sometimes break there own JSON model and sends singles as object...
            catch (JSONException e) {
                routes.add(getRoute(alternatives.getJSONObject(i).getJSONObject("Leg")));
            }

            Route start_route = routes.get(0);
            Route end_route = routes.get(routes.size() - 1);
            Trip trip = new Trip.Builder()
                    .name(start_route.getOrigin().getName() + " - " +
                            end_route.getDestination().getName())
                    .routes(routes)
                    .build();
            trips.add(trip);
        }
        return trips;
    }

    private Route getRoute(JSONObject routeJSON) throws JSONException {

        // Get origin stop info from JSON
        String origin_name = routeJSON.getJSONObject("Origin").getString("name");
        String origin_track = routeJSON.getJSONObject("Origin").getString("track");
        // We don't know coordinates at this stage, could make another call but seems redundant since
        // since its not used yet.
        Location originLocation = new Location("");
        TripLocation origin = new TripLocation(origin_name, originLocation, origin_track);

        // Get destination stop info from JSON
        String destination_name = routeJSON.getJSONObject("Destination").getString("name");
        String destination_track = routeJSON.getJSONObject("Destination").getString("track");
        Location destinationLocation = new Location("");
        TripLocation destination = new TripLocation(destination_name, destinationLocation, destination_track);

        // Get origin time info from JSON
        String start_date = routeJSON.getJSONObject("Origin").getString("date");
        String start_time = routeJSON.getJSONObject("Origin").getString("time");
        LocalDateTime departure = LocalDateTime.parse(start_date + " " + start_time,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));

        // Get destination time info from JSON
        String end_date = routeJSON.getJSONObject("Destination").getString("date");
        String end_time = routeJSON.getJSONObject("Destination").getString("time");
        LocalDateTime arrival = LocalDateTime.parse(end_date + " " + end_time,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));

        // Get mode of transport from JSON
        String type = routeJSON.getString("type");
        ModeOfTransport mode = ModeOfTransport.valueOf(type);

        TravelTimes times = new TravelTimes(departure, arrival);

        Route route = new Route(origin, destination, times, mode);

        //Get Västtrafik journey reference that can be used to get more info
//        if(routeJSON.has("JourneyDetailRef"))
//            route.setJourneyRef(refParser(routeJSON.getJSONObject("JourneyDetailRef").getString("ref")));

        return route;
    }

    private String refParser(String reference) throws UnsupportedEncodingException {
        //We don't want the full URL, only the reference key and we want to decode it
        return URLDecoder.decode(reference.substring(reference.lastIndexOf("ref=") + 4), StandardCharsets.UTF_8.toString());
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