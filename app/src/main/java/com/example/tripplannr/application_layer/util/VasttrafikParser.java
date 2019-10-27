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

public class VasttrafikParser implements TripParser {


    public VasttrafikParser() {
    }

    // Return stops and locations from json object
    public List<TripLocation> getMatching(String data) throws JSONException {
        List<TripLocation> locations = new ArrayList<>();

        JSONObject json = new JSONObject(data);
        JSONArray stops = new JSONArray();
        // Get Stops
        try {
            stops = json.getJSONObject("LocationList").getJSONArray("StopLocation");
        }
        // Vasttrafik sometimes break there own JSON model and sends singles as object...
        catch (JSONException e) {
            stops.put(json.getJSONObject("LocationList").getJSONObject("StopLocation"));
        }
        // Get locations
        if (json.getJSONObject("LocationList").has("CoordLocation")) {
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

        // Add coordinates
        for (int i = 0; i < stops.length(); i++) {
            Location location = new Location("");
            location.setLongitude(stops.getJSONObject(i).getDouble("lon"));
            location.setLatitude(stops.getJSONObject(i).getDouble("lat"));
            locations.add(new TripLocation(stops.getJSONObject(i).getString("name"), location));
        }
        return locations;
    }

    // Parse journey details from JSON
    public void addJourneyDetails(String data, Route route) throws JSONException {
        List<TripLocation> journeyDetails = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);
        JSONArray stopLocations = jsonObject.getJSONObject("JourneyDetail").getJSONArray("Stop");
        route.setGeometryRef(jsonObject.getJSONObject("JourneyDetail").getString("GeometryRef"));

        // Get all relevant stop coordinates and names
        for (int i = 0; i < stopLocations.length(); i++) {
            JSONObject stop = stopLocations.getJSONObject(i);
            Location location = new Location("");
            location.setLatitude(stop.getDouble("lat"));
            location.setLongitude(stop.getDouble("lon"));
            TripLocation tripLocation = new TripLocation(stop.getString("name"), location, stop.getString("track"));
            // Only get the stops from the current route
            if (route.getOrigin().getLocation().getLatitude() != 0)
                journeyDetails.add(tripLocation);
                // This is the first stop we want
            else if (route.getOrigin().getName().equals(tripLocation.getName())) {
                journeyDetails.add(tripLocation);
                route.setOrigin(tripLocation);
            }
            // This is the last stop we want
            if (route.getDestination().getName().equals(tripLocation.getName())) {
                route.setDestination(tripLocation);
                break;
            }
        }
        route.setStops(journeyDetails);
    }

    public List<LatLng> getPoints(String data) throws JSONException {
        List<LatLng> points = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonPoints = jsonObject.getJSONObject("Points").getJSONArray("Point");

        for (int i = 0; i < jsonPoints.length(); i++) {
            JSONObject coordinates = jsonPoints.getJSONObject(i);
            points.add(new LatLng(coordinates.getDouble("lat"), coordinates.getDouble("lon")));

        }

        return points;
    }

    // Get trips from JSON
    @Override
    public List<Trip> getTrips(String data) throws JSONException {
        List<Trip> trips = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        JSONArray alternatives = jsonObject.getJSONObject("TripList").getJSONArray("Trip");

        // Build trips for each alternative
        for (int i = 0; i < alternatives.length(); i++) {
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

    // Get routes from JSON
    private Route getRoute(JSONObject routeJSON) throws JSONException {

        // Get name from JSON
        String name = Utilities.englishTransportName(routeJSON.getString("name"));

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

        Route route = new Route(name, origin, destination, times, mode);

        //Get Västtrafik journey reference that can be used to get more info
        if (routeJSON.has("JourneyDetailRef")) {
            try {
                route.setJourneyRef(refParser(routeJSON.getJSONObject("JourneyDetailRef").getString("ref")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

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
        for (TripLocation stop : stops) {
            if (name.equals(stop.getName()))
                return new LatLng(stop.getLocation().getLatitude(), stop.getLocation().getLongitude());
        }
        // No Match
        return new LatLng(0, 0);
    }

    public String getGeometryRef(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        return jsonObject.getJSONObject("JourneyDetail").getJSONObject("GeometryRef").
                getString("ref");
    }

    public void addLegDetails(String data, Route route) throws JSONException {
        List<Location> legs = new ArrayList<>();

        JSONArray points = new JSONObject(data).getJSONObject("Points").getJSONArray("Point");
        for (int i = 0; i < points.length(); i++) {
            double lon = Double.valueOf(points.getJSONObject(i).getString("lon"));
            double lat = Double.valueOf(points.getJSONObject(i).getString("lat"));
            legs.add(Utilities.latlngToLocation(new LatLng(lat, lon)));
        }
        route.setLegs(legs);
    }
}
