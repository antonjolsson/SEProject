package com.example.tripplannr.data_access_layer.type_converters;

import androidx.room.TypeConverter;

import com.example.tripplannr.domain_layer.Route;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RoutesConverter {

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
            .create();

    @TypeConverter
    public String fromRoutes(ArrayList<Route> routes) {
        Type type = new TypeToken<ArrayList<Route>>(){}.getType();
        System.out.println(gson.toJson(routes.get(0).getTimes().getArrival()));
        return gson.toJson(routes, type);
    }

    @TypeConverter
    public ArrayList<Route> fromJson(String json) {
        Type type = new TypeToken<ArrayList<Route>>(){}.getType();
        return gson.fromJson(json, type);
    }


}
