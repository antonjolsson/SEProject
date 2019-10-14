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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


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
/*
    public List<Trip> getRoute(String data) {
        TripLocation locOrgin = new TripLocation("StenaTerminalen, Göteborg", new Location(""), "");
        TripLocation locDestination = new TripLocation("StenaTerminalen, Fredrikshamn", new Location(""), "");
        Calendar dateTime = Calendar.getInstance();
        TripQuery tripQ = new TripQuery(locOrgin, locDestination, dateTime);
        return getTrip(tripQ);
    }*/

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
                    int start_hour = Integer.parseInt(start_time.substring(0, 2));
                    int start_min = Integer.parseInt(start_time.substring(3, 5));
                    Calendar departure = Calendar.getInstance();
                    departure.set(Calendar.HOUR_OF_DAY, start_hour);
                    departure.set(Calendar.MINUTE, start_min);

                    String end_time = objDes.getString("time");
                    int end_hour = Integer.parseInt(end_time.substring(0, 2));
                    int end_min = Integer.parseInt(end_time.substring(3, 5));
                    Calendar arrival = Calendar.getInstance();
                    arrival.set(Calendar.HOUR_OF_DAY, end_hour);
                    arrival.set(Calendar.MINUTE, end_min);


                    //Checks so that if the times are before current time we add 1 day
                    if (departure.before(tQ.getTime())) {
                        departure.add(Calendar.DATE, 1);
                        arrival.add(Calendar.DATE, 1);
                    }

                    //adds 1 day if they arrive after midnight
                    arrival.add(Calendar.DATE, 1);

                    TravelTimes times = new TravelTimes(departure, arrival);

                    routes.add(getRoute(arr.getJSONObject(i).getString("JourneyDetailRef"), departure, arrival));

                    //har ju bara 1 stop så enklast(?) att hämta location där ifrån, inte  super snyggt dock
                    Trip trip = new Trip.Builder()
                            .name(arr.getJSONObject(i).getString("name"))
                            .routes(routes)
                            .build();

                    //  System.out.print(departure.get(Calendar.MINUTE));
                    //  System.out.println(arrival.get(Calendar.HOUR));
                    // System.out.print(arrival.get(Calendar.MINUTE));
                    //    System.out.println(objOri.getString("name"));
                    //  System.out.println(objDes.getString("name"));

                    trips.add(trip);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trips;
    }

    private Route getRoute(String jRef, Calendar depart, Calendar arrInp) {
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
            // ferryInfo(ferryName);

            //Skapar lista av Locations
            String geoRef = objRoute.getString("GeometryRef");
            locationList = geoList(geoRef);

            //skapa add metod eller ha i constructorn
            //route.addGeoList(locationList);


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

