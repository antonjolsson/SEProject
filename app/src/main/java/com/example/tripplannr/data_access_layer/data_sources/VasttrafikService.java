
package com.example.tripplannr.data_access_layer.data_sources;

import java.time.LocalDateTime;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VasttrafikService {

    // Get Västtrafik API access token
    @POST("token")
    @FormUrlEncoded
    Call<ResponseBody> getToken(
            @Header("Authorization") String bearer,
            @Header("Content-Type") String content_type,
            @Field("grant_type") String grant_type);

    String api_path = "bin/rest.exe/v2/";

    // Get trips between given origin and destination
    @GET(api_path + "trip")
    Call<ResponseBody> getTrips(
            @Query("destId") long destId,
            @Query("originId") long originId,
            @Query("date") String date,
            @Query("format") String format,
            @Header("Authorization") String bearer);

    // Get journey details for referenced trip
    @GET(api_path + "journeyDetail")
    Call<ResponseBody> getJourneyDetail(
            @Query("ref") String ref,
            @Query("format") String format,
            @Header("Authorization") String bearer);

    // Get geometry details for referenced trip
    @GET(api_path + "geometry")
    Call<ResponseBody> getGeometry(
            @Query("ref") String ref,
            @Query("format") String format,
            @Header("Authorization") String bearer);

    // Get a pattern matching for stops using given string
    @GET(api_path + "location.name")
    Call<ResponseBody> getName(
            @Query("input") String input,
            @Query("format") String format,
            @Header("Authorization") String bearer);

    // Get all stops nearby a given address
    @GET(api_path + "location.nearbystops")
    Call<ResponseBody> getNearbyStops(
            @Query("originCoordLat") double originLat,
            @Query("originCoordLong") double originLong,
            @Query("format") String format,
            @Header("Authorization") String bearer);

    // Get all stops in Västtrafiks database
    @GET(api_path + "location.allstops")
    Call<ResponseBody> getAllStops(
            @Query("format") String format,
            @Header("Authorization") String bearer);

    // Get the nearest address to given coordinate
    @GET(api_path + "location.nearbyaddress")
    Call<ResponseBody> getNearbyAddress(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("format") String format,
            @Header("Authorization") String bearer);

    // Get the next 20 arrivals from a given point in time to a given origin
    @GET(api_path + "arrivalBoard")
    Call<ResponseBody> getArrivalBoard(
            @Query("originId") String originId,
            @Query("date") Date date,
            @Query("time") String time,
            @Query("format") String format,
            @Header("Authorization") String bearer);

    // Get the next 20 departures from a given point in time to a given origin
    @GET(api_path + "departureBoard")
    Call<ResponseBody> getDepartureBoard(
            @Query("originId") String originId,
            @Query("date") Date date,
            @Query("time") String time,
            @Query("format") String format,
            @Header("Authorization") String bearer);

    // Get the positions of all vehicles in a given bounding box
    @GET(api_path + "livemap")
    Call<ResponseBody> getLivemap(
            @Query("minx") String minx,
            @Query("maxx") String maxx,
            @Query("miny") String miny,
            @Query("maxy") String maxy,
            @Query("onlyRealTime") String bool, // yes or no
            @Query("format") String format,
            @Header("Authorization") String bearer);

    // Get information about the journey planner and underlying data
    @GET(api_path + "systeminfo")
    Call<ResponseBody> getSysteminfo(
            @Query("format") String format,
            @Header("Authorization") String bearer);
}