package com.example.tripplannr.application_layer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.tripplannr.R;
import com.example.tripplannr.application_layer.trip.TripActivity;

@SuppressWarnings({"FieldCanBeLocal"})
public class IntroScreenActivity extends Activity {

     // vvv Set this to false to bypass intro screen

    private final boolean introScreen = false;

    private static final int DISPLAY_TIME = 3000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroScreenActivity.this, TripActivity.class);
                startActivity(intent);
                finish();
            }
        }, introScreen ? DISPLAY_TIME : 0);
    }
}
