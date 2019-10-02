package com.example.tripplannr.model;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VasttrafikApi implements TripApi {

    private String apiAddress;
    // TODO, remove this context when ever we can...
    private Context context;


    public  VasttrafikApi(Context context) {
        this.context = context;
        //TODO
    }


    @Override
    public List<Trip> getRoute(String data ) throws JSONException {
        // TODO, real API call
        String response = loadJSONFromResources("json/vasttrafik_trip.json");

        List<Trip> trips = new ArrayList<>();

        assert response != null;
        JSONObject jsonObject = new JSONObject(response);
        JSONArray alternatives = jsonObject.getJSONObject("TripList").getJSONArray("Trip");

        for (int i = 0; i < alternatives.length(); i++){
            JSONArray legs = alternatives.getJSONObject(i).getJSONArray("Leg");
            List<Route> routes = new ArrayList<>();
            for (int j = 0; j < legs.length(); j++) {
                JSONObject route = legs.getJSONObject(j);

                // TODO, Västtrafik API call using JourneyDetailRef
                List<TripLocation> stops = new ArrayList<>();
                if(route.has("JourneyDetailRef")) {
                    String journeyDetailURL = route.getJSONObject("JourneyDetailRef").getString("ref");
                    String journey_response = loadJSONFromResources("json/vasttrafik_journey_detail.json");
                    assert journey_response != null;
                    stops = getStops(new JSONObject(journey_response));
                }

                String origin_name = route.getJSONObject("Origin").getString("name");
                String origin_track = route.getJSONObject("Origin").getString("track");
                Location origin_coordinates = getCoordinates(origin_name, stops);
                TripLocation origin = new TripLocation(origin_name, origin_coordinates, origin_track);

                String destination_name = route.getJSONObject("Destination").getString("name");
                String destination_track = route.getJSONObject("Destination").getString("track");
                Location destination_coordinates = getCoordinates(destination_name, stops);
                TripLocation destination = new TripLocation(destination_name, destination_coordinates, destination_track);

                String start_date = route.getJSONObject("Origin").getString("date");
                String start_time = route.getJSONObject("Origin").getString("time");
                LocalDateTime departure = LocalDateTime.parse(start_date + " " + start_time,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));

                String end_date = route.getJSONObject("Destination").getString("date");
                String end_time = route.getJSONObject("Destination").getString("time");
                LocalDateTime arrival = LocalDateTime.parse(end_date + " " + end_time,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));

                String type = route.getString("type");
                ModeOfTransport mode = ModeOfTransport.valueOf(type);

                TravelTimes times = new TravelTimes(departure, arrival);
                routes.add(new Route(origin, destination, times, mode));
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

    private List<TripLocation> getStops(JSONObject journeyDetail) throws JSONException {
        JSONArray stopLocations = journeyDetail.getJSONObject("JourneyDetail").getJSONArray("Stop");
        List<TripLocation> stops = new ArrayList<>();
        for (int i = 0; i < stopLocations.length(); i++) {
            JSONObject stop = stopLocations.getJSONObject(i);
            Location location = new Location("");
            location.setLongitude(stop.getDouble("lon"));
            location.setLatitude(stop.getDouble("lat"));
            stops.add(new TripLocation(stop.getString("name"), location, stop.getString("track")));
        }
        return stops;
    }

    private Location getCoordinates(String name, List<TripLocation> stops) {
        for (TripLocation stop: stops){
            if(name.equals(stop.getName()))
                return stop.getLocation();
        }
        return null;
    }

    // TODO, remove when we have real data
    private String loadJSONFromResources(String name) {
        String json;
        try {
            InputStream is = context.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}