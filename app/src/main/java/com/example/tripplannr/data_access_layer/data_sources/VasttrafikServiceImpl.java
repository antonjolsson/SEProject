package com.example.tripplannr.data_access_layer.data_sources;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tripplannr.application_layer.util.VasttrafikParser;
import com.example.tripplannr.data_access_layer.data_sources.VasttrafikService;
import com.example.tripplannr.domain_layer.Trip;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Not sure if this should be a repo or implementation as retrofit might create an implementation under the hood
 */
public class VasttrafikServiceImpl {

    private static VasttrafikServiceImpl instance;

    public static VasttrafikServiceImpl getInstance() {
        if(instance == null) instance = new VasttrafikServiceImpl();
        return instance;
    }

    private VasttrafikService vasttrafikService;

    private MutableLiveData<List<Trip>> data = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private MutableLiveData<Integer> statusCode = new MutableLiveData<>();

    private VasttrafikServiceImpl() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vasttrafik.se/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        vasttrafikService = retrofit.create(VasttrafikService.class);
    }

    public LiveData<List<Trip>> getData() {
        return data;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    private void onFetchFail(int statusCode) {
        isLoading.postValue(false);
        data.postValue(new ArrayList<Trip>());
        this.statusCode.setValue(statusCode);
    }

    public void loadTrips(final String origin, final String destination, final LocalDateTime date) {
        data.setValue(new ArrayList<Trip>());
        isLoading.setValue(true);
        vasttrafikService
                .getToken("Basic ajUyMVJTb3BVVXFIVlR5X0VqOGl1TWRsWXBnYTpzNV9ncUZZR0p2b2pydjhRb2NfNDRVcGpWYm9h",
                        "application/x-www-form-urlencoded", "client_credentials")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            if(response.code() >= 200 && response.code() <= 299) {
                                String token = "Bearer " + new JSONObject(response.body().string())
                                        .getString("access_token");
                                searchAndLoadTrips(origin, destination, token, date);
                            }
                            else onFetchFail(response.code());
                        } catch (JSONException | IOException e) {
                            onFetchFail(response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        onFetchFail(500);
                    }
                });
    }

    private void searchAndLoadTrips(String origin, final String destination, final String token, final LocalDateTime date) {
        vasttrafikService
                .getName(origin, "json", token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.code() >= 200 && response.code() <= 299) {
                            try {
                                searchAndLoadTripsHelper(destination
                                        , new JSONObject(response.body().string())
                                                .getJSONObject("LocationList")
                                                .getJSONArray("StopLocation")
                                                .getJSONObject(0)
                                                .getLong("id")
                                        , token
                                        , date);
                            } catch (IOException | JSONException e) {
                                onFetchFail(response.code());
                            }
                        }
                        else onFetchFail(response.code());
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        onFetchFail(500);
                    }
                });

    }

    private void searchAndLoadTripsHelper(String destination, final long originId, final String token, final LocalDateTime date) {
        vasttrafikService
                .getName(destination, "json", token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.code() >= 200 && response.code() <= 299) {
                            try {
                                long destId = new JSONObject(response.body().string())
                                        .getJSONObject("LocationList")
                                        .getJSONArray("StopLocation")
                                        .getJSONObject(0)
                                        .getLong("id");
                                loadTripsHelper(token, destId, originId, date);
                            } catch (IOException | JSONException e) {
                                onFetchFail(response.code());
                            }
                        }
                        else onFetchFail(response.code());
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        onFetchFail(500);
                    }
                });
    }

    private void loadTripsHelper(final String token, final long originId, final long destinationId, final LocalDateTime date) {
        vasttrafikService
                .getTrips(originId, destinationId, date.toString(),"json","Bearer " + token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            Thread.sleep(2000);
                            if(response.code() >= 200 && response.code() <= 299) {
                                String body = response.body().string();
                                System.out.println(body);
                                data.postValue(new VasttrafikParser().getRoute(body));
                                isLoading.postValue(false);
                            }
                            else onFetchFail(response.code());
                        } catch (IOException | InterruptedException | JSONException e) {
                            e.printStackTrace();
                            onFetchFail(response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        onFetchFail(500);
                    }
                });
    }
}