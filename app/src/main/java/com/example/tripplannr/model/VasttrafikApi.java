package com.example.tripplannr.model;

import android.content.Context;
import android.graphics.Point;

import com.example.tripplannr.stdanica.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
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
                List<Location> stops = new ArrayList<>();
                if(route.has("JourneyDetailRef")) {
                    String journeyDetailURL = route.getJSONObject("JourneyDetailRef").getString("ref");
                    String journey_response = loadJSONFromResources("json/vasttrafik_journey_detail.json");
                    assert journey_response != null;
                    stops = getStops(new JSONObject(journey_response));
                }

                String origin_name = route.getJSONObject("Origin").getString("name");
                String origin_track = route.getJSONObject("Origin").getString("track");
                Point origin_coords = getLatLon(origin_name, stops);
                Location origin = new Location(origin_name, origin_coords, origin_track);

                String destination_name = route.getJSONObject("Destination").getString("name");
                String destination_track = route.getJSONObject("Destination").getString("track");
                Point destination_coords = getLatLon(destination_name, stops);
                Location destination = new Location(destination_name, destination_coords, destination_track);

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
            // TODO, real trip data other than routes
            Trip trip = new Trip.Builder()
                    .name("Chalmers - Lindholmen")
                    .routes(routes)
                    .origin(new Location("Chalmers", new Point(0,0), "A"))
                    .destination(new Location("Lindholmen", new Point(0, 0), "B"))
                    .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusHours(1)))
                    .build();
            trips.add(trip);
        }
        return trips;
    }

    private List<Location> getStops(JSONObject journeyDetail) throws JSONException {
        JSONArray stopLocations = journeyDetail.getJSONObject("JourneyDetail").getJSONArray("Stop");
        List<Location> stops = new ArrayList<>();
        for (int i = 0; i < stopLocations.length(); i++) {
            JSONObject stop = stopLocations.getJSONObject(i);
            stops.add(new Location(stop.getString("name"), new Point(stop.getInt("lat"),
                    stop.getInt("lon")), stop.getString("track")));
        }
        return stops;
    }

    private Point getLatLon (String name, List<Location> stops) {
        for (Location stop: stops){
            if(name.equals(stop.getName()))
                return new Point(0,0);
                //return stop.getCoords();
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