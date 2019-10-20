package com.example.tripplannr.application_layer.util;

import android.content.Context;
import android.location.Location;

import com.example.tripplannr.domain_layer.FerryInfo;
import com.example.tripplannr.domain_layer.ModeOfTransport;
import com.example.tripplannr.domain_layer.Route;
import com.example.tripplannr.domain_layer.TravelTimes;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripLocation;
import com.example.tripplannr.domain_layer.TripQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class StenaLineParser {
    private final Context mContext;


    public StenaLineParser(Context context) {
        mContext = context.getApplicationContext();
    }

    private String loadJSONFromAsset(String jsonFil) {
        String json;
        try {
            InputStream is = mContext.getAssets().open(jsonFil);
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


    public Route getRoute(TripQuery tQ) {
        System.out.println(tQ.getOrigin());
        System.out.println(tQ.getDestination());
        if (tQ.getOrigin().equals("Fredrikshamn"))
            tQ.setOrigin("StenaTerminalen, Fredrikshamn");
        else if (!tQ.getOrigin().equals("StenaTerminalen, Fredrikshamn"))
            tQ.setOrigin("StenaTerminalen, Göteborg");
        if (tQ.getDestination().equals("Fredrikshamn"))
            tQ.setDestination("StenaTerminalen, Fredrikshamn");
        else if (!tQ.getDestination().equals("StenaTerminalen, Fredrikshamn"))
            tQ.setDestination("StenaTerminalen, Göteborg");
        System.out.println(tQ.getOrigin());
        System.out.println(tQ.getDestination());
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
                if (0 == objOri.getString("name").compareTo(tQ.getOrigin()) && 0 == objDes.getString("name").compareTo(tQ.getDestination())) {

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

                    routes.add(parseRoute(arr.getJSONObject(i).getString("JourneyDetailRef"), departure, arrival));

                    Trip trip = new Trip.Builder()
                            .name(arr.getJSONObject(i).getString("name"))
                            .routes(routes)
                            .build();

                    trips.add(trip);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(trips, new Comparator<Trip>() {

            public int compare(Trip t1, Trip t2) {
                return t1.getTimes().getDeparture().compareTo(t2.getTimes().getDeparture());
            }
        });

        return trips.get(0).getRoutes().get(0);
    }

    private Route parseRoute(String jRef, LocalDateTime depart, LocalDateTime arrInp) {
        Route route = null;
        List<Location> locationList;
        try {
            // TODO Add name in JSON?
            String name = "Stena";

            JSONObject obj = new JSONObject(loadJSONFromAsset("stenaJourneyDet.json"));
            JSONObject objRoute = obj.getJSONObject("JourneyDetail").getJSONObject(jRef);

            String origin_name = objRoute.getJSONObject("Origin").getString("name");
            String origin_track = "";
            Location origin_location = new Location("");
            origin_location.setLatitude(objRoute.getJSONObject("Origin").getDouble("lat"));
            origin_location.setLongitude(objRoute.getJSONObject("Origin").getDouble("lon"));
            TripLocation origin = new TripLocation(origin_name, origin_location, origin_track);

            String destination_name = objRoute.getJSONObject("Destination").getString("name");
            String destination_track = "";
            Location destination_location = new Location("");
            destination_location.setLatitude(objRoute.getJSONObject("Destination").getDouble("lat"));
            destination_location.setLongitude(objRoute.getJSONObject("Destination").getDouble("lon"));
            TripLocation destination = new TripLocation(destination_name, destination_location, destination_track);

            TravelTimes times = new TravelTimes(depart, arrInp);

            String type = objRoute.getString("JourneyType");
            ModeOfTransport mode = ModeOfTransport.valueOf(type);

            String ferryName = objRoute.getString("Ship");


            route = new Route(name, origin, destination, times, mode);

            //ska ferryINfo ligga i trip isf ha en add så slipper allt annat bry sig om det, TODO?
            route.setFerryInfo(ferryInfo(ferryName));

            // Create a list of locations
            String geoRef = objRoute.getString("GeometryRef");
            locationList = geoList(geoRef);


            route.setLegs(locationList);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return route;

    }

    private FerryInfo ferryInfo(String shipName) {
        FerryInfo ferryInfo = null;
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("stenaFerries.json"));
            JSONObject objShip = obj.getJSONObject("ShipList").getJSONObject(shipName);
            boolean lounge = objShip.getBoolean("Lounge");
            boolean food = objShip.getBoolean("Food");
            boolean largeBorderShop = objShip.getBoolean("LargeBorderShop");
            boolean conference = objShip.getBoolean("Conference");
            String url = objShip.getString("Url");
            ferryInfo = new FerryInfo(shipName, food, largeBorderShop, conference, lounge, url);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ferryInfo;
    }

    private List<Location> geoList(String geoRef) {
        List<Location> locationList = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("stenaGeo.json"));
            JSONArray geoArray = obj.getJSONObject("Geometry").getJSONArray(geoRef);
            for (int i = 0; i < geoArray.length(); i++) {
                Location location = new Location("");
                location.setLatitude(geoArray.getJSONObject(i).getDouble("lat"));
                location.setLongitude(geoArray.getJSONObject(i).getDouble("lon"));
                locationList.add(location);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locationList;
    }


}
