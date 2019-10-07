package com.example.tripplannr.view;

import com.example.tripplannr.R;
import com.example.tripplannr.model.tripdata.FerryInfo;


public class ModeOfTransportIconDictionary {

    public static int getTransportIcon(FerryInfo.ModeOfTransport modeOfTransport) {
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
