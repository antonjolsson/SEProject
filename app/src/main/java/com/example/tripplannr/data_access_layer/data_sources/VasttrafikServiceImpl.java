package com.example.tripplannr.data_access_layer.data_sources;

import android.content.Context;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tripplannr.application_layer.util.StenaLineParser;
import com.example.tripplannr.application_layer.util.TripDictionary;
import com.example.tripplannr.application_layer.util.VasttrafikParser;
import com.example.tripplannr.domain_layer.Route;
import com.example.tripplannr.domain_layer.TravelTimes;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.domain_layer.TripLocation;
import com.example.tripplannr.domain_layer.TripQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.tripplannr.domain_layer.ModeOfTransport.WALK;

/**
 * Not sure if this should be a repo or implementation as retrofit might create an implementation under the hood
 */
public class VasttrafikServiceImpl {

    private static VasttrafikServiceImpl instance;

    public static VasttrafikServiceImpl getInstance(Context context) {
        if(instance == null) instance = new VasttrafikServiceImpl(context);
        return instance;
    }

    private VasttrafikService vasttrafikService;

    private MutableLiveData<List<Trip>> data = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private MutableLiveData<Integer> statusCode = new MutableLiveData<>();

    private MutableLiveData<List<TripLocation>> addressMatches = new MutableLiveData<>();

    private TripQuery original;

    private Context context;

    private VasttrafikServiceImpl(Context context) {
        this.context = context;
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

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    private void onFetchFail(int statusCode) {
        isLoading.postValue(false);
        data.postValue(new ArrayList<Trip>());
        this.statusCode.setValue(statusCode);
    }

    public void loadTrips(final TripQuery tripQuery) {
        data.setValue(new ArrayList<Trip>());
        isLoading.setValue(true);
        original = new TripQuery.Builder()
                        .destination(tripQuery.getDestination())
                        .origin(tripQuery.getOrigin())
                        .time(tripQuery.getTime())
                        .build();
        tripQuery.setOrigin(TripDictionary.translateTrip(tripQuery.getOrigin()));
        tripQuery.setDestination(TripDictionary.translateTrip(tripQuery.getDestination()));
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

    private void loadTripsHelper(final TripQuery tripQuery, final long originId, final long destinationId, final String token) {
        if(original.getOrigin().equals("Fredrikshamn") || original.getOrigin().equals("StenaTerminalen, Fredrikshamn"))
        {
          tripQuery.setTime(new StenaLineParser(context).getRoute(original).getTimes().getArrival().plusMinutes(35));
        }
        final String date = tripQuery.getTime().getYear() + "-" + tripQuery.getTime().getMonthValue()
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
                               // System.out.println(body); 30min att checka ut
                                List<Trip> trips = new VasttrafikParser().getTrips(body);
                                if(original.getOrigin().equals("Fredrikshamn") || original.getOrigin().equals("StenaTerminalen, Fredrikshamn")) {
                                    for(Trip trip : trips) {
                                        trip.addRouteStart(stenaToMasthugget(trip.getRoutes().get(0)));
                                        trip.addRouteStart(new StenaLineParser(context).getRoute(original));
                                    }
                                }
                                if(original.getDestination().equals("Fredrikshamn") || original.getDestination().equals("StenaTerminalen, Fredrikshamn")) {
                                    for(Trip trip : trips) {
                                        original.getTime().plusMinutes(30);
                                        trip.addRouteEnd(masthuggetToStena(trip.getRoutes().get(trip.getRoutes().size()-1)));
                                        trip.addRouteEnd(new StenaLineParser(context).getRoute(original));
                                    }
                                }
                                data.postValue(trips);
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

    public void getMatching(final String pattern) {
        vasttrafikService
                .getToken("Basic ajUyMVJTb3BVVXFIVlR5X0VqOGl1TWRsWXBnYTpzNV9ncUZZR0p2b2pydjhRb2NfNDRVcGpWYm9h",
                        "application/x-www-form-urlencoded", "client_credentials")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                      //  System.out.println(response.code());
                       // System.out.println(response.body());
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
    private Route stenaToMasthugget(Route route)
    {

        Location location = new Location("");
        location.setLongitude(11.946647);
        location.setLatitude(57.701843);
        TripLocation origin = new TripLocation("StenaTerminalen, Göteborg", location,"A");
        TravelTimes travelTimes = new TravelTimes(route.getTimes().getDeparture().minusMinutes(5),route.getTimes().getDeparture());
        Route returnRoute = new Route.Builder()
                .origin(origin)
                .destination(route.getOrigin())
                .mode(WALK)
                .times(travelTimes)
                .build();
        returnRoute.setLegs(addLegs(false));
        return returnRoute;
    }
    private Route masthuggetToStena(Route route)
    {

        Location location = new Location("");
        location.setLongitude(11.946647);
        location.setLatitude(57.701843);
        TripLocation destination = new TripLocation("StenaTerminalen, Göteborg", location,"A");
        TravelTimes travelTimes = new TravelTimes(route.getTimes().getArrival(),route.getTimes().getArrival().plusMinutes(5));
        Route returnRoute = new Route.Builder()
                .origin(route.getDestination())
                .destination(destination)
                .mode(WALK)
                .times(travelTimes)
                .build();
        returnRoute.setLegs(addLegs(true));
        return returnRoute;

    }
    private List<Location> addLegs(Boolean tillStena){
      List<Location> legs = new ArrayList<>();
      List<Double> coords = new ArrayList<>(Arrays.asList(57.699595, 11.944577,
              57.699845, 11.946201,57.700668, 11.945770,
              57.701122, 11.945705,57.701219, 11.946372,
              57.701564, 11.946369,57.701843, 11.946769));
    for(int i=0; i< coords.size(); i=i+2)
        {
            Location location = new Location("");
            location.setLatitude(i);
            location.setLongitude(i+1);
            legs.add(location);
    }
    if(!tillStena){
        Collections.reverse(legs);
    }
    return legs;
    }
}