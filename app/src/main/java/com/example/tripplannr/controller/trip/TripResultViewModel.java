package com.example.tripplannr.controller.trip;

import android.graphics.Point;
import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
import java.util.ArrayList;
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

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private MutableLiveData<Trip> mTripLiveData = new MutableLiveData<>();

    public TripResultViewModel() {
        super();
        isLoading.setValue(false);
        try {
            sendRequest("No token", 9021014001960000L,  9022014004490030L);
            System.out.println(mTripsLiveData.getValue());
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

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    @Override
    public void onClick(Trip trip) {
        mTripLiveData.setValue(trip);
    }

    private void onFetchFail() {
        mTripsLiveData.postValue(new ArrayList<Trip>());
        isLoading.postValue(false);
    }

    public void sendRequest(final String token, final long originId, final long destinationId) throws IOException {
        isLoading.setValue(true);
        vasttrafikRepository
                .getVasttrafikService().
                getJourneyDetail("Bearer " + token)
                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            Thread.sleep(2000);
                                            if(response.code() >= 200 && response.code() <= 299) {
                                                sendSecondRequest(response.body().string(), token, originId, destinationId);
                                            }
                                        } catch (IOException | InterruptedException ignored) {}
                                        buildFakeTrip();
                                        //onFetchFail();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        onFetchFail();
                                    }
        });
    }

    private void buildFakeTrip() {
        Route route1 = new Route.Builder()
                .origin(new TripLocation("Lillhagens Station", new Location(""), "A"))
                .destination(new TripLocation("Brunnsparken", new Location(""),"A"))
                .mode(ModeOfTransport.BUS)
                .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusMinutes(18)))
                .build();

        Route route2 = new Route.Builder()
                .mode(ModeOfTransport.TRAM)
                .origin(new TripLocation("Brunnsparken", new Location(""), "A"))
                .destination(new TripLocation("Brunnsparken", new Location(""), "C"))
                .mode(ModeOfTransport.WALK)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(16), LocalDateTime.now().plusMinutes(16)))
                .build();

        Route route3 = new Route.Builder()
                .mode(ModeOfTransport.WALK)
                .origin(new TripLocation("Brunnsparken", new Location(""),"C"))
                .destination(new TripLocation("Masthuggstorget", new Location(""), "B"))
                .mode(ModeOfTransport.TRAM)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(21), LocalDateTime.now().plusMinutes(30)))
                .build();

        Route route4 = new Route.Builder()
                .origin(new TripLocation("Masthuggstorget", new Location(""), "B"))
                .destination(new TripLocation("Danmarksterminalen", new Location(""), "Gate A"))
                .mode(ModeOfTransport.WALK)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(30), LocalDateTime.now().plusMinutes(32)))
                .build();
        Route route5 = new Route.Builder()
                .origin(new TripLocation("Danmarksterminalen", new Location(""), "Gate A"))
                .destination(new TripLocation("Fredrikshavn", new Location(""), "Gate B"))
                .mode(ModeOfTransport.FERRY)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(58), LocalDateTime.now().plusDays(1)))
                .build();
        Trip trip = new Trip.Builder()
                .name("Göteborg, Fredrikshavn")
                .origin(new TripLocation("Lillhagens Station", new Location(""), "A"))
                .destination(new TripLocation("Fredrikshavn", new Location(""), "Gate B"))
                .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusDays(1).plusMinutes(58)))
                .routes(Arrays.asList(route1, route2, route3, route4, route5))
                .build();
        mTripsLiveData.setValue(Collections.singletonList(trip));
        isLoading.setValue(false);

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
                                isLoading.postValue(false);
                            }
                        } catch (IOException | JSONException ignored) {}
                        onFetchFail();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onFetchFail();
                    }
                });
    }

}
