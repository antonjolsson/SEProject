package com.example.tripplannr.application_layer.search;

import android.view.MotionEvent;
import android.view.View;

// Class for reusable view methods

class ViewUtilities {

    private final static float SEMI_TRANSPARENT_ALPHA = 0.5f;
    private final static float OPAQUE_ALPHA = 1f;


    // Set different levels of alpha (opacity) when view pressed and when not
    static void setAlphaLevels(View v, MotionEvent event, float nonPressedAlpha) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(ViewUtilities.SEMI_TRANSPARENT_ALPHA);
                break;
            case MotionEvent.ACTION_UP:
                v.setAlpha(nonPressedAlpha);
            default:
                v.setAlpha(nonPressedAlpha);
        }
    }

    static void setAlphaLevels(View v, MotionEvent event) {
        setAlphaLevels(v, event, OPAQUE_ALPHA);
    }

}
