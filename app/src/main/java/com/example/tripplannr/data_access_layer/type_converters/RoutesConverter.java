package com.example.tripplannr.data_access_layer.type_converters;

import androidx.room.TypeConverter;

import com.example.tripplannr.domain_layer.Route;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RoutesConverter {

    @TypeConverter
    public String fromRoutes(ArrayList<Route> routes) {
        Type type = new TypeToken<ArrayList<Route>>(){}.getType();
        return new Gson().toJson(routes, type);
    }

    @TypeConverter
    public ArrayList<Route> fromJson(String json) {
        Type type = new TypeToken<ArrayList<Route>>(){}.getType();
        return new Gson().fromJson(json, type);
    }


}
