package com.example.tripplannr.data_access_layer.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tripplannr.application_layer.util.VasttrafikParser;
import com.example.tripplannr.data_access_layer.dao.VasttrafikServiceDAO;
import com.example.tripplannr.domain_layer.Trip;

import org.json.JSONException;

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

public class VasttrafikRepository {

    private VasttrafikServiceDAO vasttrafikServiceDAO;

    private MutableLiveData<List<Trip>> data = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public VasttrafikRepository() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vasttrafik.se/bin/rest.exe/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        vasttrafikServiceDAO = retrofit.create(VasttrafikServiceDAO.class);
    }

    public LiveData<List<Trip>> getData() {
        return data;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    private void onFetchFail() {
        isLoading.postValue(false);
        data.postValue(new ArrayList<Trip>());
    }

    public void loadTrips(final long originId, final long destinationId) {
        isLoading.setValue(true);
        vasttrafikServiceDAO
                .getJourneyDetail("Bearer ")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            Thread.sleep(2000);
                            if(response.code() >= 200 && response.code() <= 299) {
                                loadTripsHelper(response.body().string(), "", originId, destinationId);
                            }
                            onFetchFail();
                        } catch (IOException | InterruptedException ignored) {
                            onFetchFail();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        onFetchFail();
                    }
                });
    }

    private void loadTripsHelper(final String journeyDetail, String token, long originId, long destinationId) {
        vasttrafikServiceDAO
                .getTrips(originId, destinationId, "json","Bearer " + token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            if(response.code() >= 200 && response.code() <= 299) {
                                String body = response.body().string();
                                data.postValue(new VasttrafikParser().getRoute(body, journeyDetail));
                                isLoading.postValue(false);
                            }
                            else isLoading.postValue(false);
                        } catch (IOException | JSONException e) {
                            onFetchFail();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        onFetchFail();
                    }
                });
    }
}
