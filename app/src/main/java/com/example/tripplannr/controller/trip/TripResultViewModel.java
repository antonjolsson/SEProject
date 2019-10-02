package com.example.tripplannr.controller.trip;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.model.Trip;
import com.example.tripplannr.model.VasttrafikApi;
import com.example.tripplannr.model.VasttrafikRepository;

import org.json.JSONException;

import java.io.IOException;
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
        try {
            sendRequest("No token", 9021014001960000L,  9022014004490030L);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void sendRequest(final String token, final long originId, final long destinationId) throws IOException {
        vasttrafikRepository
                .getVasttrafikService().
                getJourneyDetail("Bearer " + token)
                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            if(response.code() >= 200 && response.code() <= 299) {
                                                sendSecondRequest(response.body().string(), token, originId, destinationId);
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    }
        });
    }

    private void sendSecondRequest(final String journeyDetail, String token, long originId, long destinationId) {
        vasttrafikRepository
                .getVasttrafikService()
                .getTrips(originId, destinationId, "json","Bearer " + token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if(response.code() >= 200 && response.code() <= 299) {
                                String body = response.body().string();
                                mTripsLiveData.postValue(new VasttrafikApi().getRoute(body, journeyDetail));
                            }
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
