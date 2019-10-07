package com.example.tripplannr.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VasttrafikRepository {

    private VasttrafikService vasttrafikService;

    public VasttrafikRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vasttrafik.se/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        vasttrafikService = retrofit
                .create(VasttrafikService.class);
    }

    public VasttrafikService getVasttrafikService() {
        return vasttrafikService;
    }
}
