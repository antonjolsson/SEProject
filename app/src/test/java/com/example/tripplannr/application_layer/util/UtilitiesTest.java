package com.example.tripplannr.application_layer.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilitiesTest {

    @Test
    public void englishTransportName() {
        assertEquals("Tram 11", Utilities.englishTransportName("Spårvagn 11"));
    }
}