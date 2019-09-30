package com.example.tripplannr.trip;

import com.example.tripplannr.model.ModeOfTransport;
import com.example.tripplannr.stdanica.R;

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
