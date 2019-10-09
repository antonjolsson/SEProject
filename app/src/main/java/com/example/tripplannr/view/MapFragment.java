package com.example.tripplannr.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tripplannr.R;
import com.example.tripplannr.model.addressservice.AddressResultReceiver;
import com.example.tripplannr.model.addressservice.FetchAddressConstants;
import com.example.tripplannr.model.addressservice.FetchAddressIntentService;
import com.example.tripplannr.model.tripdata.TripLocation;
import com.example.tripplannr.viewmodel.TripViewModel;
import com.example.tripplannr.viewmodel.TripViewModel.LocationField;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Objects;

import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.viewmodel.TripViewModel.LocationField.ORIGIN;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener{

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    // Chalmers Lindholmen location :)
    private static final LatLng DEF_LAT_LNG = new LatLng(57.707202, 11.940108);
    private static final float DEF_ZOOM_LEVEL = 13;

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Location mLastLocation;
    private Location clickedLocation;
    private TripViewModel model;
    private Marker originMarker;
    private Marker destinationMarker;
    private float zoomLevel;
    private LatLng latLng;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        model = ViewModelProviders.of(getActivity()).get(TripViewModel.class);
        zoomLevel = DEF_ZOOM_LEVEL;
        latLng = DEF_LAT_LNG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                CameraPosition cameraPosition = mMap.getCameraPosition();
                latLng = cameraPosition.target;
                zoomLevel = cameraPosition.zoom;
            }
        });
        initLocationRequest();
        setListeners();
    }

    private void initLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //Location Permission already granted
            fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                    Looper.myLooper());
            mMap.setMyLocationEnabled(true);
        } else {
            //Request Location Permission
            checkLocationPermission();
        }
    }

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
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        updateMarker(latLng);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        //this.latLng = latLng;
        //zoomLevel = 14;

        clickedLocation = new Location("");
        clickedLocation.setLatitude(latLng.latitude);
        clickedLocation.setLongitude(latLng.longitude);
        startIntentService();
    }

    private void updateMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        if (model.isInitOriginField() || model.getFocusedLocationField() == ORIGIN) {
            removeMarker(ORIGIN);
            markerOptions.icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_RED));
            originMarker = mMap.addMarker(markerOptions);
        }
        else {
            removeMarker(DESTINATION);
            markerOptions.icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            destinationMarker = mMap.addMarker(markerOptions);
        }
    }

    private void startIntentService() {
        AddressResultReceiver resultReceiver = new AddressResultReceiver(model, new Handler(),
                clickedLocation);
        Intent intent = new Intent(getContext(), FetchAddressIntentService.class);
        intent.putExtra(FetchAddressConstants.RECEIVER, resultReceiver);
        intent.putExtra(FetchAddressConstants.LOCATION_DATA_EXTRA, clickedLocation);
        Objects.requireNonNull(getActivity()).startService(intent);
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " +
                        location.getLongitude());
                mLastLocation = location;
                if (model.isInitOriginField()) {
                    simulateMapClick(location);
                }
            }
        }
    };

    private void simulateMapClick(Location location) {
        LatLng latLng = tripLocationToLatLng(location);
        clickedLocation = mLastLocation;
        onMapClick(latLng);
    }

    private LatLng tripLocationToLatLng(Location location) {
        return new LatLng(location.getLatitude(),
                location.getLongitude());
    }

    private void removeMarker(LocationField field) {
        if (field == DESTINATION && destinationMarker != null) {
            destinationMarker.remove();
        }
        else if (field == ORIGIN && originMarker != null) {
            originMarker.remove();
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                        .setTitle("Location permission Needed")
                        .setMessage("This app needs the location permission, " +
                                "please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create().show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    private void setListeners() {
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
