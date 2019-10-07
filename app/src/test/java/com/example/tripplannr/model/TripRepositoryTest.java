package com.example.tripplannr.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TripRepositoryTest {

    private TripRepository tripRepository = TripRepository.getInstance();

    private Trip testTrip = new Trip.Builder().build();

    @Before
    public void before() {
        tripRepository.save(testTrip);
    }

    @After
    public void after() {
        tripRepository.delete(testTrip);
    }

    @Test
    public void saveTripTest() {
        System.out.println(tripRepository.getSavedTrips());
        Assert.assertEquals(1, tripRepository.getSavedTrips().size());
    }

    @Test
    public void getSavedTripTest() {
        Assert.assertEquals(testTrip, tripRepository.getSavedTrips().get(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getSavedTripImmutableTest() {
        tripRepository.getSavedTrips().remove(testTrip);
    }

}
