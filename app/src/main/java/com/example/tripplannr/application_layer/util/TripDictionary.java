package com.example.tripplannr.application_layer.util;

import android.location.Location;

import com.example.tripplannr.domain_layer.TripQuery;

public class TripDictionary {

    public static void translateTrip(TripQuery tripQuery) {
        switch (tripQuery.getOrigin()) {
            case "StenaTerminalen, Fredrikshamn":
            case "Fredrikshamn":
            case "Fredrikshamn, Danmark":
                tripQuery.setOriginLocation(masthuggetLocation());
                tripQuery.setOrigin("Masthugget");
        }
        switch (tripQuery.getDestination()) {
            case "StenaTerminalen, Fredrikshamn":
            case "Fredrikshamn":
            case "Fredrikshamn, Danmark":
                tripQuery.setDestinationLocation(masthuggetLocation());
                tripQuery.setDestination("Masthugget");
        }
    }

    private static Location masthuggetLocation() {
        Location location = new Location("");
        location.setLatitude(57.6989854);
        location.setLongitude(11.9415757);
        return location;
    }

}
