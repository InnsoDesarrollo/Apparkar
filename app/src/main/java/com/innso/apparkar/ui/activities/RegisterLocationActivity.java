package com.innso.apparkar.ui.activities;

import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.innso.apparkar.R;
import com.innso.apparkar.api.controller.MapsController;
import com.innso.apparkar.databinding.ActivityRegisterLocationBinding;
import com.innso.apparkar.ui.BaseActivity;
import com.innso.apparkar.ui.adapters.listeners.OnMarkerDragListenerAdapter;

import javax.inject.Inject;

public class RegisterLocationActivity extends BaseActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    private GoogleMap mMap;

    protected Location currentLocation;

    ActivityRegisterLocationBinding binding;

    @Inject
    MapsController mapsController;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_location);
        getComponent().inject(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        requestLocationPermissions();
    }

    @Override
    protected void successLocationPermission() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(4.6097100, -74.0817500)));
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        addMapListeners();
    }

    private void addMapListeners() {

        mMap.setOnMarkerDragListener(new OnMarkerDragListenerAdapter() {

            @Override
            public void onMarkerDrag(Marker marker) {
                LatLng latLng = marker.getPosition();
                currentLocation.setLongitude(latLng.longitude);
                currentLocation.setLatitude(latLng.latitude);
                updateLocationAddress();
            }
        });
    }

    private void updateLocation() {

        if (currentLocation != null && mMap != null) {
            LatLng newPosition = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(newPosition)
                    .zoom(15)
                    .bearing(0)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            mMap.addMarker(new MarkerOptions().position(newPosition).draggable(true));
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        updateLocation();
        updateLocationAddress();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    private void updateLocationAddress() {
        mapsController.getAddressDescription(currentLocation.getLatitude(), currentLocation.getLongitude())
                .subscribe(address -> binding.editTextAddress.setText(address));
    }
}
