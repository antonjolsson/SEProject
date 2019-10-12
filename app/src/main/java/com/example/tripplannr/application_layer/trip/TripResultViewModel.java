package com.example.tripplannr.application_layer.trip;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplannr.data_access_layer.data_sources.TripDAOImpl;
import com.example.tripplannr.data_access_layer.repositories.VasttafikRepository;
import com.example.tripplannr.domain_layer.Route;
import com.example.tripplannr.domain_layer.TravelTimes;
import com.example.tripplannr.domain_layer.Trip;
import com.example.tripplannr.data_access_layer.data_sources.VasttrafikServiceImpl;
import com.example.tripplannr.domain_layer.TripLocation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.tripplannr.domain_layer.ModeOfTransport.*;

public class TripResultViewModel extends ViewModel implements IClickHandler<Trip> {

    private VasttafikRepository vasttafikRepository = new VasttafikRepository();

    private LiveData<List<Trip>> mTripsLiveData;

    private LiveData<Boolean> isLoading;

    private MutableLiveData<Trip> mTripLiveData = new MutableLiveData<>();

    private TripDAOImpl tripRepository = TripDAOImpl.getInstance();

    public TripResultViewModel() {
        super();
        isLoading = vasttafikRepository.isLoading();
        mTripsLiveData = vasttafikRepository.getData();
        vasttafikRepository.loadTrips("Skogome", "Frölunda");
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

    public boolean saveTrip(Trip trip) {
        return tripRepository.save(trip).isPresent();
    }

    public List<Trip> getSavedTrips() {
        return tripRepository.findAll();
    }

    public boolean removeTrip(Trip trip) {
        return tripRepository.delete(trip);
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
        Trip trip2 = new Trip.Builder()
                .name("Lillhagens Station, Brunnsparken")
                .origin(new TripLocation("Lillhagens Station", new Location(""), "A"))
                .destination(new TripLocation("Brunnsparken", new Location(""), "A"))
                .times(new TravelTimes(LocalDateTime.now(), LocalDateTime.now().plusMinutes(18)))
                .routes(Arrays.asList(route1))
                .build();
        return new ArrayList<>(Arrays.asList(trip, trip2));
    }

}
