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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class StenaLineParser {
    Context mContext;


    public StenaLineParser(Context context) {
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


    public List<Trip> getTrips(TripQuery tQ) {
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

                    TravelTimes times = new TravelTimes(departure, arrival);

                    routes.add(getRoute(arr.getJSONObject(i).getString("JourneyDetailRef"), departure, arrival));


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

        return trips;
    }

    private Route getRoute(String jRef, LocalDateTime depart, LocalDateTime arrInp) {
        Route route = null;
        List<Location> locationList = new ArrayList<>();
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
            destination_location.setLatitude(objRoute.getJSONObject("Destination").getDouble("lat"));
            destination_location.setLongitude(objRoute.getJSONObject("Destination").getDouble("lon"));
            TripLocation destination = new TripLocation(destination_name, destination_location, destination_track);

            TravelTimes times = new TravelTimes(depart, arrInp);

            String type = objRoute.getString("JourneyType");
            ModeOfTransport mode = ModeOfTransport.valueOf(type);

            String ferryName = objRoute.getString("Ship");


            route = new Route(origin, destination, times, mode);

            //ska ferryINfo ligga i trip isf ha en add så slipper allt annat bry sig om det
            route.setFerryinfo(ferryInfo(ferryName));

            //Skapar lista av Locations
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
            Boolean lounge = objShip.getBoolean("Lounge");
            Boolean food = objShip.getBoolean("Food");
            Boolean largeBorderShop = objShip.getBoolean("LargeBorderShop");
            Boolean conference = objShip.getBoolean("Conference");
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
