package com.example.tripplannr.domain_layer;

import com.example.tripplannr.data_access_layer.dao.TripDAO;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TripRepositoryTest {

    private TripDAO tripRepository = TripDAO.getInstance();

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
        System.out.println(tripRepository.findAll());
        Assert.assertEquals(1, tripRepository.findAll().size());
    }

    @Test
    public void getSavedTripTest() {
        Assert.assertEquals(testTrip, tripRepository.findAll().get(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getSavedTripImmutableTest() {
        tripRepository.findAll().remove(testTrip);
    }

}
