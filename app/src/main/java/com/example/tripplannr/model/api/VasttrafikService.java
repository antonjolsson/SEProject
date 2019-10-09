
package com.example.tripplannr.model.api;

import com.google.api.client.auth.oauth2.TokenResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VasttrafikService  {

    @GET("bin/rest.exe/v2/trip")
    Call<ResponseBody> getTrips(
            @Query("destId") long destId
            , @Query("originId") long originId
            , @Query("format") String format
            , @Header("Authorization") String bearer);

    @GET("bin/rest.exe/v2/journeyDetail")
    Call<ResponseBody> getJourneyDetail(
            @Query("ref") String ref,
            @Header("Authorization") String bearer);

    @GET("bin/rest.exe/v2/nearbystops")
    Call<ResponseBody> getNearbyStops(
            @Query("originCoordLat") double originLat,
            @Query("originCoordLong") double originLong,
            @Header("Authorization") String bearer);

    @POST("token")
    @FormUrlEncoded
    Call<TokenResponse> getToken(
            @Field("client_id") String client_id, // j521RSopUUqHVTy_Ej8iuMdlYpga
            @Field("client_secret") String client_secret, // s5_gqFYGJvojrv8Qoc_44UpjVboa
            @Field("grant_type") String grant_type); // Client Credentials

}