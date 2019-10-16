package com.example.tripplannr.application_layer.trip;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.data_access_layer.repositories.TripRepository;
import com.example.tripplannr.data_access_layer.repositories.VasttrafikRepository;
import com.example.tripplannr.domain_layer.Route;
import com.example.tripplannr.domain_layer.Trip;

import java.util.List;

public class TripResultViewModel extends ViewModel implements IClickHandler<Trip> {

    private VasttrafikRepository vasttrafikRepository;

    private LiveData<List<Trip>> mTripsLiveData;

    private LiveData<Boolean> isLoading;

    private LiveData<Integer> statusCode;

    private MutableLiveData<Trip> mTripLiveData = new MutableLiveData<>();
    private MutableLiveData<Route> mRouteLiveData = new MutableLiveData<>();

    private TripRepository tripRepository;

    public TripResultViewModel(TripRepository tripRepository, VasttrafikRepository vasttrafikRepository) {
        super();
        this.tripRepository = tripRepository;
        this.vasttrafikRepository = vasttrafikRepository;
        isLoading = vasttrafikRepository.isLoading();
        mTripsLiveData = vasttrafikRepository.getData();
        statusCode = vasttrafikRepository.getStatusCode();
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

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    @Override
    public void onClick(Trip trip) {
        mTripLiveData.setValue(trip);
    }

    public void saveTrip(Trip trip) {
        System.out.println(trip.getRoutes().get(0).getTimes().getArrival());
        tripRepository.save(trip);
    }

    public List<Trip> getSavedTrips() {
        return tripRepository.findAll();
    }

    public void removeTrip(Trip trip) {
        tripRepository.delete(trip);
    }

    public LiveData<Route> getRouteLiveData() {
        return mRouteLiveData;
    }

    public void updateRoute(Route route) {mRouteLiveData.setValue(route);}


    /*private List<Trip> buildFakeTrips() {
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
    public void setSelectedRoute(Route route) {
        mRouteLiveData.setValue(route);
    }

    public LiveData<Route> getRouteLiveData() {
        return mRouteLiveData;
    }
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
        Trip trip2 = new Trip.Builder()
                .name("Lillhagens Station, Brunnsparken")
                .origin(new TripLocation("Lillhagens Station", new Location(""), "A"))
                .destination(new TripLocation("Brunnsparken", new Location(""), "A"))
                .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusMinutes(18)))
                .routes(Arrays.asList(route1))
                .build();
        return new ArrayList<>(Arrays.asList(trip, trip2));
    }*/

}
