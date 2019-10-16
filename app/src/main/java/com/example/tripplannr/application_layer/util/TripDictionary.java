package com.example.tripplannr.application_layer.util;

public class TripDictionary {

    public static String translateTrip(String address) {
        switch (address) {
            case "StenaTerminalen, Fredrikshamn":
            case "Fredrikshamn":
                return "Masthuggstorget";
            default:
                return address;
        }
    }

}
