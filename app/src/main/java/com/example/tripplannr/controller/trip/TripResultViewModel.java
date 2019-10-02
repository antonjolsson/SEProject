package com.example.tripplannr.controller.trip;

import android.graphics.Point;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.model.Location;
import com.example.tripplannr.model.ModeOfTransport;
import com.example.tripplannr.model.Route;
import com.example.tripplannr.model.TravelTimes;
import com.example.tripplannr.model.Trip;
import com.example.tripplannr.model.TripLocation;
import com.example.tripplannr.model.VasttrafikApi;
import com.example.tripplannr.model.VasttrafikRepository;

import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripResultViewModel extends ViewModel implements IClickHandler<Trip> {

    private VasttrafikRepository vasttrafikRepository = new VasttrafikRepository();

    private MutableLiveData<List<Trip>> mTripsLiveData = new MutableLiveData<>();

    private MutableLiveData<Trip> mTripLiveData = new MutableLiveData<>();

    public TripResultViewModel() {
        super();
    }

    public LiveData<Trip> getTripLiveData() {
        return mTripLiveData;
    }

    public LiveData<List<Trip>> getTripsLiveData() {
        return mTripsLiveData;
    }

    @Override
    public void onClick(Trip trip) {
        mTripLiveData.setValue(trip);
    }

    public void sendRequest() throws IOException {
        vasttrafikRepository
                .getVasttrafikService().
                getJourneyDetail("Bearer c6e31c27-70b3-32a2-a6cf-544f4184b995")
                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            System.out.println(response.code());
                                            sendSecondRequest(response.body().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
        });
    }

    private void sendSecondRequest(final String journeyDetail) {

        vasttrafikRepository
                .getVasttrafikService()
                .getTrips(9021014001960000L, 9022014004490030L, "json","Bearer c6e31c27-70b3-32a2-a6cf-544f4184b995")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String body = response.body().string();
                            mTripsLiveData.postValue(new VasttrafikApi().getRoute(body, journeyDetail));

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("fail");
                    }
                });
    }

}
