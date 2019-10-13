package com.example.tripplannr.data_access_layer;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.tripplannr.data_access_layer.data_sources.TripDAO;
import com.example.tripplannr.domain_layer.Trip;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(AndroidJUnit4ClassRunner.class)
public class DatabaseTest {

    private TripDAO tripDAO;
    private AppDatabase appDatabase;

    @Before
    public void before() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appDatabase = AppDatabase.getInstance(context);
        tripDAO = appDatabase.tripDAO();
    }

    @After
    public void after() {
        appDatabase.close();
    }


    @Test
    public void selectTest() {
        List<Trip> tripList = tripDAO.findAll();
        System.out.println(tripList.get(0).getRoutes().get(0).getTimes().getDuration());
        Assert.assertNotEquals(null,
                tripList.get(0).getRoutes().get(0).getTimes().getArrival().toString());
    }


}
