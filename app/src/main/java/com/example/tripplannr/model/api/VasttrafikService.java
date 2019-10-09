
package com.example.tripplannr.model.api;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface VasttrafikService  {

    @GET("trip")
    Call<ResponseBody> getTrips(
            @Query("destId") long destId
            , @Query("originId") long originId
            , @Query("format") String format
            , @Header("Authorization") String bearer);

    @GET("journeyDetail?ref=887394%2F306599%2F243152%2F174227%2F80%3Fdate%3D2019-10-02%26station_evaId%3D1961001%26station_type%3Ddep%26format%3Djson%26")
    Call<ResponseBody> getJourneyDetail(@Header("Authorization") String bearer);

}