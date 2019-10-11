package com.example.tripplannr.application_layer.util;

import com.example.tripplannr.R;
import com.example.tripplannr.domain_layer.ModeOfTransport;


public class ModeOfTransportIconDictionary {

    public static int getTransportIcon(ModeOfTransport modeOfTransport) {
        switch (modeOfTransport) {
            case WALK:
                return R.drawable.walk;
            case TRAM:
                return R.drawable.tram;
            case BUS:
                return R.drawable.bus;
            default:
                return R.drawable.boat;
        }
    }
}
