package com.example.tripplannr.application_layer.util;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

/* Class for general (logic) utility methods */

public class Utilities {

    public static boolean isTomorrow(Calendar calendar) {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        return tomorrow.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                tomorrow.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                tomorrow.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isToday(Calendar calendar) {
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isNow(Calendar calendar) {
        Calendar rightNow = Calendar.getInstance();
        return isToday(calendar) &&
                rightNow.get(Calendar.HOUR_OF_DAY) == calendar.get(Calendar.HOUR_OF_DAY) &&
                rightNow.get(Calendar.MINUTE) == calendar.get(Calendar.MINUTE);
    }

    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }


    public static LatLng locationToLatlng(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        return new LatLng(latitude, longitude);
    }

    public static Location latlngToLocation(LatLng latLng) {
        Location location = new Location("");
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        return location;
    }

    public static String englishTransportName(String name) {
        String transportName = name.replaceAll("\\s(.)+", "");
        String tempName;
        switch (transportName) {
            case "Gå" : tempName = "Walk"; break;
            case "Spårvagn" : tempName = "Tram"; break;
            case "Färja" : tempName = "Ferry"; break;
            case "Tåg" : tempName = "Train"; break;
            case "Car" : tempName = "Bil"; break;
            default: tempName = "Bus";
        }
        return tempName + name.replaceFirst("(\\S)+", "");
    }
}
