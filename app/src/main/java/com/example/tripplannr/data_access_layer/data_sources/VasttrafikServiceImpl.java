package com.example.tripplannr.data_access_layer.data_sources;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tripplannr.application_layer.util.VasttrafikParser;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripLocation;
import com.example.tripplannr.domain_layer.TripQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private MutableLiveData<List<TripLocation>> addressMatches = new MutableLiveData<>();

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

    public LiveData<List<TripLocation>> getAddressMatches() {
        return addressMatches;
    }

    private void onFetchFail() {
        isLoading.postValue(false);
        data.postValue(new ArrayList<Trip>());
    }

    public void loadTrips(final TripQuery tripQuery) {
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
                                searchAndLoadTrips(tripQuery, token);
                            }
                            else onFetchFail();
                        } catch (JSONException | IOException e) {
                            onFetchFail();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        onFetchFail();
                    }
                });
    }

    private void searchAndLoadTrips(final TripQuery tripQuery, final String token) {
        isLoading.setValue(true);
        vasttrafikService
                .getName(tripQuery.getOrigin(), "json", token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.code() >= 200 && response.code() <= 299) {
                            try {
                                searchAndLoadTripsHelper(tripQuery
                                        , new JSONObject(response.body().string())
                                                .getJSONObject("LocationList")
                                                .getJSONArray("StopLocation")
                                                .getJSONObject(0)
                                                .getLong("id")
                                        , token);
                            } catch (IOException | JSONException e) {
                                onFetchFail();
                            }
                        }
                        else onFetchFail();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        onFetchFail();
                    }
                });

    }

    private void searchAndLoadTripsHelper(final TripQuery tripQuery, final long originId, final String token) {
        vasttrafikService
                .getName(tripQuery.getDestination(), "json", token)
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
                                System.out.println(originId);
                                System.out.println(destId);
                                loadTripsHelper(tripQuery, originId, destId, token);
                            } catch (IOException | JSONException e) {
                                onFetchFail();
                            }
                        }
                        else onFetchFail();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        onFetchFail();
                    }
                });
    }

    private void loadTripsHelper(TripQuery tripQuery, final long originId, final long destinationId, final String token) {
        String date = tripQuery.getTime().getYear() + "-" + tripQuery.getTime().getMonthValue()
                + "-" + tripQuery.getTime().getDayOfMonth();
        String time = tripQuery.getTime().getHour() + ":" + tripQuery.getTime().getMonthValue();
        System.out.println(date);
        System.out.println(time);
        vasttrafikService
                .getTrips(originId, destinationId, date, time, "json","Bearer " + token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            Thread.sleep(2000);
                            if(response.code() >= 200 && response.code() <= 299) {
                                String body = response.body().string();
                                System.out.println(body);
                                data.postValue(new VasttrafikParser().getTrips(body));
                                isLoading.postValue(false);
                            }
                            else onFetchFail();
                        } catch (IOException | InterruptedException | JSONException e) {
                            e.printStackTrace();
                            onFetchFail();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        onFetchFail();
                    }
                });
    }

    public void getMatching(final String pattern) {
        vasttrafikService
                .getToken("Basic ajUyMVJTb3BVVXFIVlR5X0VqOGl1TWRsWXBnYTpzNV9ncUZZR0p2b2pydjhRb2NfNDRVcGpWYm9h",
                        "application/x-www-form-urlencoded", "client_credentials")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println(response.code());
                        System.out.println(response.body());
                        try {
                            sendPatternRequest(new JSONObject(response.body().string()).getString("access_token"), pattern);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }

    private void sendPatternRequest(String token, String pattern) {
        vasttrafikService
                .getName(pattern, "json", "Bearer " + token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.code() >= 200 && response.code() <= 299) {
                                String body = response.body().string();
                                // TODO do something with response
                                addressMatches.setValue(new VasttrafikParser().getMatching(body));
                                System.out.println(getAddressMatches().getValue().get(1).getName());
                            }
                        } catch (IOException e) {
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }
}