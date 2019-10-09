package com.example.tripplannr.model.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VasttrafikRepository {

    private VasttrafikService vasttrafikService;

    public VasttrafikRepository() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vasttrafik.se/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        vasttrafikService = retrofit
                .create(VasttrafikService.class);
    }

    public VasttrafikService getVasttrafikService() {
        return vasttrafikService;
    }
}
