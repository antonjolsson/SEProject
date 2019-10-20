package com.example.tripplannr.application_layer.trip.map;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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
import com.example.tripplannr.application_layer.search.SearchViewModel;
import com.example.tripplannr.domain_layer.TripLocation;
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

import static com.example.tripplannr.application_layer.search.SearchViewModel.LocationField.DESTINATION;
import static com.example.tripplannr.application_layer.search.SearchViewModel.LocationField.ORIGIN;

abstract class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    // Chalmers Lindholmen location :)
    private static final LatLng DEF_LAT_LNG = new LatLng(57.707202, 11.940108);
    private static final float DEF_ZOOM_LEVEL = 13;

    private static final int BOTTOM_PADDING = 130; // TODO: Remove this when map frag gets more space

    GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    Location mLastLocation;
    Location clickedLocation;
    SearchViewModel model;
    private float zoomLevel;
    private LatLng latLng;
    private Marker originMarker;
    private Marker destinationMarker;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SearchViewModel.class);
        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
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
        mMap.setPadding(0, 0, 0, BOTTOM_PADDING);
        initLocationRequest();
        setListeners();
        if (model.getOrigin().getValue() != null) {
            model.setFocusedLocationField(ORIGIN);
            model.setLocation(model.getOrigin().getValue().getLocation(),
                    model.getOrigin().getValue().getName());
        }
        if (model.getDestination().getValue() != null) {
            model.setFocusedLocationField(DESTINATION);
            LatLng latLng = new LatLng(model.getDestination().getValue().getLocation().getLatitude(),
                    model.getDestination().getValue().getLocation().getLongitude());
            updateMarker(latLng);
        }
    }

    void updateMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        if (model.isInitOriginField() || model.getFocusedLocationField() == ORIGIN) {
            removeMarker(ORIGIN);
            markerOptions.icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_RED));
            originMarker = mMap.addMarker(markerOptions);
        } else {
            removeMarker(DESTINATION);
            markerOptions.icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            destinationMarker = mMap.addMarker(markerOptions);
        }
    }

    private void removeMarker(SearchViewModel.LocationField field) {
        if (field == DESTINATION && destinationMarker != null) {
            destinationMarker.remove();
        } else if (field == ORIGIN && originMarker != null) {
            originMarker.remove();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
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

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {
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

    void simulateMapClick(Location location) {
        LatLng latLng = tripLocationToLatLng(location);
        clickedLocation = mLastLocation;
        onMapClick(latLng);
    }

    private LatLng tripLocationToLatLng(Location location) {
        return new LatLng(location.getLatitude(),
                location.getLongitude());
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
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create().show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

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
    }

    private void locationChanged(TripLocation tripLocation, SearchViewModel.LocationField destination) {
        if (tripLocation != null) {
            LatLng latLng = tripLocationToLatLng(tripLocation.getLocation());
            updateMarker(latLng);
        } else removeMarker(destination);
        if (model.getAddressQuery().getValue() != null &&
                model.getAddressQuery().getValue()) {
            model.setAddressQuery(false);
            model.flattenFocLocationStack();
        }
    }

}
