package com.example.tripplannr.model;

import java.text.ParseException;


import android.content.Context;
import android.location.Location;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

public class StenaLineApi implements TripApi {
    Context mContext;



    public StenaLineApi(Context context) {
        mContext = context.getApplicationContext();
    }

    public String loadJSONFromAsset(String jsonFil) {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open(jsonFil);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public List<Trip> getTrip(TripQuery tQ) {
        List<Trip> trips = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("stenaTripList.json"));
            JSONArray arr = obj.getJSONArray("Trip");


            for (int i = 0; i < arr.length(); i++) {
                List<Route> routes = new ArrayList<>();
                //Get the origin and destination
                JSONObject objOri = arr.getJSONObject(i).getJSONObject("Origin");
                JSONObject objDes = arr.getJSONObject(i).getJSONObject("Destination");

                //Compare the origin and destination from json file to the one in tripQuery object
                if (0 == objOri.getString("name").compareTo(tQ.getOrigin().getName()) && 0 == objDes.getString("name").compareTo(tQ.getDestination().getName())) {

                    //Checks time in tripQuery and compare time to the one in json file, if time is later adds 1 day to json date
                    String start_time = objOri.getString("time");
                    String start_date = tQ.getTime().toLocalDate().toString();
                    LocalDateTime departure = LocalDateTime.parse(start_date + " " + start_time,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));

                    String end_time = objDes.getString("time");
                    String end_date = tQ.getTime().toLocalDate().toString();
                    LocalDateTime arrival = LocalDateTime.parse(end_date + " " + end_time,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));

                    if (departure.isBefore(tQ.getTime())) {
                        departure = departure.plusDays(1);
                        arrival = arrival.plusDays(1);
                    }
                                       arrival = arrival.plusDays(objDes.getLong("date"));

                    TravelTimes times = new TravelTimes(departure, arrival);

                    routes.add(getRoute( arr.getJSONObject(i).getString("JourneyDetailRef"), departure, arrival));

                    //har ju bara 1 stop så enklast(?) att hämta location där ifrån, inte  super snyggt dock
                    Trip trip = new Trip.Builder()
                            .name(arr.getJSONObject(i).getString("name"))
                            .routes(routes)
                            .origin(routes.get(0).getOrigin())
                            .destination(routes.get(0).getDestination())
                            .times(times)
                            .build();
                    trips.add(trip);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trips;
    }

    private Route getRoute(String jRef, LocalDateTime depart, LocalDateTime arrInp) {
        Route route = null;
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("stenaJourneyDet.json"));
            JSONObject objRoute = obj.getJSONObject("JourneyDetail").getJSONObject(jRef);

            String origin_name = objRoute.getJSONObject("Origin").getString("name");
            String origin_track = "";
            Location origin_location = new Location("");
            origin_location.setLatitude(objRoute.getJSONObject("Origin").getDouble("lat"));
            origin_location.setLongitude(objRoute.getJSONObject("Origin").getDouble("lon"));
            //LatLng origin_coords =new LatLng(objRoute.getJSONObject("Origin").getDouble("lat"),objRoute.getJSONObject("Origin").getDouble("lon"));
            TripLocation origin = new TripLocation(origin_name, origin_location, origin_track);

            String destination_name = objRoute.getJSONObject("Destination").getString("name");
            String destination_track = "";
            Location destination_location = new Location("");
            origin_location.setLatitude(objRoute.getJSONObject("Destination").getDouble("lat"));
            origin_location.setLongitude(objRoute.getJSONObject("Destination").getDouble("lon"));
            TripLocation destination = new TripLocation(destination_name, destination_location, destination_track);

            TravelTimes times = new TravelTimes(depart, arrInp);

            String type = objRoute.getJSONObject("JourneyType").getString("type");
            ModeOfTransport mode = ModeOfTransport.valueOf(type);

            route = new Route(origin, destination, times, mode);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return route;

    }
}