package com.example.tripplannr.application_layer.addressservice;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;

public class LocationService {

    public static Location getLocation(String address, Context context) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location location = null;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                location = new Location("");
                location.setLatitude(addresses.get(0).getLatitude());
                location.setLongitude(addresses.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

}
