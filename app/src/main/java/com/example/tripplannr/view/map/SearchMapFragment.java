package com.example.tripplannr.view.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.model.addressservice.AddressResultReceiver;
import com.example.tripplannr.model.addressservice.FetchAddressConstants;
import com.example.tripplannr.model.addressservice.FetchAddressIntentService;
import com.example.tripplannr.model.tripdata.TripLocation;
import com.example.tripplannr.viewmodel.TripViewModel;
import com.example.tripplannr.viewmodel.TripViewModel.LocationField;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.ORIGIN;

public class SearchMapFragment extends MapFragment {

    private void locationChanged(TripLocation tripLocation, LocationField destination) {
        if (tripLocation != null) {
            LatLng latLng = tripLocationToLatLng(tripLocation.getLocation());
            updateMarker(latLng);
        }
        else removeMarker(destination);
        if (model.getAddressQuery().getValue() != null &&
                model.getAddressQuery().getValue()) {
            model.setAddressQuery(false);
            model.flattenFocLocationStack();
        }
    }

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
     void setListeners() {
        model.getDestination().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                locationChanged(tripLocation, DESTINATION);
            }
        });
        model.getOrigin().observe(this, new Observer<TripLocation>() {
            @Override
            public void onChanged(TripLocation tripLocation) {
                locationChanged(tripLocation, ORIGIN);
            }
        });
        model.getAddressQuery().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged (Boolean addressQuery) {
                if (addressQuery) simulateMapClick(mLastLocation);
            }
        });
    }
}
