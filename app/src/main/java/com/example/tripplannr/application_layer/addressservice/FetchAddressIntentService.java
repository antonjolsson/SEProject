package com.example.tripplannr.application_layer.addressservice;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static com.example.tripplannr.application_layer.addressservice.FetchAddressConstants.LOCATION_DATA_EXTRA;
import static com.example.tripplannr.application_layer.addressservice.FetchAddressConstants.RECEIVER;

public class FetchAddressIntentService extends IntentService {

    protected ResultReceiver receiver;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p>
     * //@param name Used to name the worker thread, important only for debugging.
     */
    /* Not used, should probably be removed
    public FetchAddressIntentService(String name) {
        super(name);
    }*/
    public FetchAddressIntentService() {
        super("FetchAddressIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        receiver = intent.getParcelableExtra(RECEIVER);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String errorMessage = "";

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(
                LOCATION_DATA_EXTRA);

        // ...

        List<Address> addresses = null;

        try {
            assert location != null;
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single name.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "Service not available";
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "Invalid lat/lng used";
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no name was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No name found";
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(FetchAddressConstants.FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();

            // Fetch the name lines using getAddressLine,
            // join them, and send them to the thread.
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, "Address found");
            deliverResultToReceiver(FetchAddressConstants.SUCCESS_RESULT,
                    TextUtils.join(Objects.requireNonNull(System.getProperty("line.separator")),
                            addressFragments));
        }
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(FetchAddressConstants.RESULT_DATA_KEY, message);
        receiver.send(resultCode, bundle);
    }
}
