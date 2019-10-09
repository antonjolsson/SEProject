package com.example.tripplannr.viewmodel;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.model.api.TripRepository;
import com.example.tripplannr.model.tripdata.Route;
import com.example.tripplannr.model.tripdata.TravelTimes;
import com.example.tripplannr.model.Trip;
import com.example.tripplannr.model.api.VasttrafikApi;
import com.example.tripplannr.model.api.VasttrafikRepository;
import com.example.tripplannr.model.tripdata.TripLocation;
import com.google.api.client.auth.oauth2.TokenResponse;

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

import static com.example.tripplannr.model.tripdata.ModeOfTransport.*;

public class TripResultViewModel extends ViewModel implements IClickHandler<Trip> {

    private VasttrafikRepository vasttrafikRepository = new VasttrafikRepository();

    private MutableLiveData<List<Trip>> mTripsLiveData = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private MutableLiveData<Trip> mTripLiveData = new MutableLiveData<>();

    private TripRepository tripRepository = TripRepository.getInstance();

    public TripResultViewModel(long originId, long destinationId) {
        super();
        isLoading.setValue(false);
        sendTokenRequest(originId, destinationId);
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

    public void sendTokenRequest(final long originId, final long destinationId) {
        vasttrafikRepository
                .getVasttrafikService()
                .getToken("j521RSopUUqHVTy_Ej8iuMdlYpga", "s5_gqFYGJvojrv8Qoc_44UpjVboa",
                        "Client Credentials")
                .enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        try {
                            Thread.sleep(2000);
                            if(response.code() >= 200 && response.code() <= 299) {
                                sendTripRequest(response.body().getAccessToken(), originId,  destinationId);
                            }
                        } catch (InterruptedException ignored) {}
                        onFetchFail();
                        mTripsLiveData.postValue(buildFakeTrips());
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {

                    }
                });
    }

    public void sendTripRequest(final String token, final long originId, final long destinationId) {
        isLoading.setValue(true);
       vasttrafikRepository
                .getVasttrafikService()
                .getTrips(originId, destinationId, "json","Bearer " + token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Thread.sleep(2000);
                            if(response.code() >= 200 && response.code() <= 299) {
                                String body = response.body().string();
                                mTripsLiveData.postValue(new VasttrafikApi().getRoute(body));
                                isLoading.postValue(false);
                            }
                        } catch (IOException | InterruptedException ignored) {} catch (JSONException e) {
                            e.printStackTrace();
                        }
                        onFetchFail();
                        mTripsLiveData.postValue(buildFakeTrips());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onFetchFail();
                    }
        });
    }

    public boolean saveTrip(Trip trip) {
        return tripRepository.save(trip).isPresent();
    }

    public List<Trip> getSavedTrips() {
        return tripRepository.getSavedTrips();
    }

    private void sendSecondRequest(final String journeyDetail, String token, long originId, long destinationId, String ref) {
        vasttrafikRepository
                .getVasttrafikService()
                .getJourneyDetail(ref, "Bearer " + token)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if(response.code() >= 200 && response.code() <= 299) {
                                String body = response.body().string();
                                mTripsLiveData.postValue(new VasttrafikApi().getRoute(body));
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

    private List<Trip> buildFakeTrips() {
        Route route1 = new Route.Builder()
                .origin(new TripLocation("Lillhagens Station", new Location(""), "A"))
                .destination(new TripLocation("Brunnsparken", new Location(""), "A"))
                .mode(BUS)
                .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusMinutes(18)))
                .build();

        Route route2 = new Route.Builder()
                .mode(TRAM)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(18), LocalDateTime.now().plusMinutes(18)))
                .origin(new TripLocation("Brunnsparken", new Location(""), "A"))
                .destination(new TripLocation("Brunnsparken", new Location(""), "C"))
                .mode(WALK)
                .build();

        Route route3 = new Route.Builder()
                .mode(WALK)
                .origin(new TripLocation("Brunnsparken", new Location(""), "C"))
                .destination(new TripLocation("Masthuggstorget", new Location(""), "B"))
                .mode(TRAM)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(21), LocalDateTime.now().plusMinutes(30)))
                .build();

        Route route4 = new Route.Builder()
                .origin(new TripLocation("Masthuggstorget", new Location(""), "B"))
                .destination(new TripLocation("Danmarksterminalen", new Location(""), "Gate A"))
                .mode(WALK)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(30), LocalDateTime.now().plusMinutes(32)))
                .build();
        Route route5 = new Route.Builder()
                .origin(new TripLocation("Danmarksterminalen", new Location(""), "Gate A"))
                .destination(new TripLocation("Fredrikshavn", new Location(""), "Gate B"))
                .mode(FERRY)
                .times(new TravelTimes(LocalDateTime.now().plusMinutes(58), LocalDateTime.now().plusDays(1)))
                .build();
        Trip trip = new Trip.Builder()
                .name("Göteborg, Fredrikshavn")
                .origin(new TripLocation("Lillhagens Station", new Location(""), "A"))
                .destination(new TripLocation("Fredrikshavn", new Location(""), "Gate B"))
                .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusDays(1).plusMinutes(58)))
                .routes(Arrays.asList(route1, route2, route3, route4, route5))
                .build();
        return Collections.singletonList(trip);
    }

}
