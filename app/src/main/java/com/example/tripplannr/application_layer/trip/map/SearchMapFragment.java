package com.example.tripplannr.application_layer.trip.map;

import android.content.Intent;
import android.location.Location;
import android.os.Handler;

import androidx.lifecycle.Observer;

import com.example.tripplannr.application_layer.addressservice.AddressResultReceiver;
import com.example.tripplannr.application_layer.addressservice.FetchAddressConstants;
import com.example.tripplannr.application_layer.addressservice.FetchAddressIntentService;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class SearchMapFragment extends MapFragment {

    @Override
    public void onMapClick(LatLng latLng) {
        super.onMapClick(latLng);

        updateMarker(latLng);

        clickedLocation = new Location("");
        clickedLocation.setLatitude(latLng.latitude);
        clickedLocation.setLongitude(latLng.longitude);
        startIntentService();
    }

    private void startIntentService() {
        AddressResultReceiver resultReceiver = new AddressResultReceiver(model, new Handler(),
                clickedLocation);
        Intent intent = new Intent(getContext(), FetchAddressIntentService.class);
        intent.putExtra(FetchAddressConstants.RECEIVER, resultReceiver);
        intent.putExtra(FetchAddressConstants.LOCATION_DATA_EXTRA, clickedLocation);
        Objects.requireNonNull(getActivity()).startService(intent);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        model.getAddressQuery().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean addressQuery) {
                if (addressQuery) simulateMapClick(mLastLocation);
            }
        });
    }
}
