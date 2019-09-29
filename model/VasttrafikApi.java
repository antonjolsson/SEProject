import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VasttrafikApi implements TripApi {

    private String apiAddress;


    public  VasttrafikApi( ) {

        //TODO
    }


    // @override
    public List<List<Route>> getRoute( String data ) throws ParseException {
        // TODO, real API call
        String response = loadJSONFromAsset("vasttrafik_trip.json");

        List<List<Route>> trips = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(response);
        JSONArray alternatives = jsonObject.getJSONObject("TripList").getJSONArray("Trip");

        for (int i = 0; i < alternatives.length(); i++){
            JSONArray routes = alternatives.getJSONObject(i).getJSONArray("Leg");
            List<Route> tmp = new ArrayList<>();
            for (int j = 0; j < routes.length(); j++) {
                JSONObject route = routes.getJSONObject(j);

                // TODO, Västtrafik API call using JourneyDetailRef
                List<Location> stops = new ArrayList<>();
                if(route.has("JourneyDetailRef")) {
                    String journeyDetailURL = route.getJSONObject("JourneyDetailRef").getString("ref");
                    String journey_response = loadJSONFromAsset("vasttrafik_trip.json");
                    stops = getStops(new JSONObject(journey_response));
                }

                String origin_name = route.getJSONObject("Origin").getString("name");
                Point origin_coords = getLatLon(origin_name, stops);
                Location origin = new Location(origin_name, origin_coords);

                String destination_name = route.getJSONObject("Destination").getString("name");
                Point destination_coords = getLatLon(destination_name, stops);
                Location destination = new Location(destination_name, destination_coords);

                String start_date = route.getJSONObject("Origin").getString("date");
                String start_time = route.getJSONObject("Origin").getString("time");
                LocalDateTime departure = LocalDateTime.parse(start_date + " " + start_time,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));

                String end_date = route.getJSONObject("Destination").getString("date");
                String end_time = route.getJSONObject("Destination").getString("time");
                LocalDateTime arrival = LocalDateTime.parse(end_date + " " + end_time,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));

                String type = route.getString("type");

                TravelTimes times = new TravelTimes(departure, arrival);
                tmp.add(new Route(origin, destination, times, type));
            }
            trips.add(tmp);
        }
        return trips;
    }

    private List<Location> getStops(JSONObject journeyDetail) {
        JSONArray stopLocations = journeyDetail.getJSONObject("JourneyDetail").getJSONArray("Stop");
        List<Location> stops = new ArrayList<>();
        for (int i = 0; i < stopLocations.length(); i++) {
            JSONObject stop = stopLocations.getJSONObject(i);
            stops.add(new Location(stop.getString("name"), new Point(stop.getInt("lat"), stop.getInt("lon"))));
        }
        return stops;
    }

    private Point getLatLon (String name, List<Location> stops) {
        for (Location stop: stops){
            if(name.equals(stop.getName()))
                return stop.getCoords();
        }
        return null;
    }

    // TODO, remove when we have real data
    private String loadJSONFromAsset(Context context, String name) {
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