package com.example.tripplannr.view.trip;

import com.example.tripplannr.R;
import com.example.tripplannr.model.tripdata.FerryInfo;
import com.example.tripplannr.model.tripdata.ModeOfTransport;


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
