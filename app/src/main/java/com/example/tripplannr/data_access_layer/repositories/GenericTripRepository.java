package com.example.tripplannr.data_access_layer.repositories;

import android.content.Context;
import android.widget.Switch;

import com.example.tripplannr.application_layer.util.StenaLineParser;
import com.example.tripplannr.application_layer.util.TripParser;
import com.example.tripplannr.application_layer.util.VasttrafikParser;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericTripRepository {
    List<String> stenaStops = Arrays.asList("StenaTerminalen, Göteborg", "StenaTerminalen, Fredrikshamn");

    private List<TripParser> tripParsers;
    private StenaLineParser stenaLineApi;
    private VasttafikRepository vasttafikRepository;

    public GenericTripRepository() {
        tripParsers = new ArrayList<>();
        //TODO implement when no context needed?
        vasttafikRepository = new VasttafikRepository();
    }

    public void setStenaLineApi(Context context) {
        this.stenaLineApi = new StenaLineParser(context);
    }

    public List<Trip> makeTrip(TripQuery tripQuery) {
        if (Arrays.asList(stenaStops).contains(tripQuery.getDestination())) {
            switch (tripQuery.getDestination()) {
                case "StenaTerminalen, Fredrikshamn":
                    vasttafikRepository.loadTrips(new TripQuery.Builder()
                            .origin(tripQuery.getOrigin())
                            .destination("Masthuggstorget")
                            .time(tripQuery.getTime())
                            .timeIsDeparture(tripQuery.isTimeDeparture())
                            .build());
            }

        }
        if (Arrays.asList(stenaStops).contains(tripQuery.getOrigin())) {
            switch (tripQuery.getOrigin()) {
                case "StenaTerminalen, Fredrikshamn":
                    vasttafikRepository.loadTrips(new TripQuery.Builder()
                            .origin("Masthuggstorget")
                            .destination(tripQuery.getDestination())
                            .time(tripQuery.getTime())
                            .timeIsDeparture(tripQuery.isTimeDeparture())
                            .build());
            }
        }
        else{

        }
        List<Trip> trips = new ArrayList<>();
        return trips;
    }

}