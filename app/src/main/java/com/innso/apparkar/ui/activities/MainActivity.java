package com.innso.apparkar.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.innso.apparkar.R;
import com.innso.apparkar.api.controller.InformationController;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.api.models.ReferencePoint;
import com.innso.apparkar.databinding.ActivityMapsBinding;
import com.innso.apparkar.ui.BaseActivity;
import com.innso.apparkar.ui.fragments.BasePlacesFragment;
import com.innso.apparkar.ui.fragments.ParkingListFragment;
import com.innso.apparkar.ui.fragments.PetrolStationsListFragment;
import com.innso.apparkar.ui.views.helpers.BottomNavigationViewHelper;
import com.innso.apparkar.util.BitmapUtils;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, BottomNavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private final int REQUEST_SPLASH = 0;

    private BottomSheetBehavior bottomSheetBehavior;

    private View bottomSheet;

    private GoogleMap mMap;

    private ActivityMapsBinding binding;

    private BasePlacesFragment parkingListFragment;

    private BasePlacesFragment petrolStationsListFragment;

    private GoogleApiClient googleApiClient;

    protected Location currentLocation;

    @Inject
    InformationController informationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps);

        getComponent().inject(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        startActivityForResult(new Intent(this, SplashActivity.class), REQUEST_SPLASH);

        initViews();

        initLocation();

        addListeners();
    }

    private void initViews() {
        bottomSheet = binding.bottomSheet;
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(getResources().getInteger(R.integer.min_height_bottom_map));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        BottomNavigationViewHelper.disableShiftMode(binding.bottomNavigation);
        informationController.getParkingSlots().subscribe(this::updateParkingSlots);
        initFragments();
    }

    private void initFragments() {
        parkingListFragment = new ParkingListFragment();
        petrolStationsListFragment = new PetrolStationsListFragment();
        replaceFragment(parkingListFragment);
    }

    private void initLocation() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void addListeners() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        binding.buttonLocation.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        updateLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int currentState = bottomSheetBehavior.getState();
        if (currentState == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            addFragment(item.getItemId());
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        return true;
    }

    private void addFragment(int id) {
        switch (id) {
            case R.id.action_map:
                replaceFragment(parkingListFragment);
                break;
            case R.id.action_petrol_station:
                replaceFragment(petrolStationsListFragment);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(4.6097100, -74.0817500)));

        mMap.getUiSettings().setRotateGesturesEnabled(false);

        mMap.setOnCameraMoveListener(() -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        updateLocation();
    }


    private void updateLocation() {

        if (currentLocation != null && mMap != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                    .zoom(15)
                    .bearing(0)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void updateParkingSlots(List<Parking> parkingSlots) {

        for (int i = 0, size = parkingSlots.size(); i < size; i++) {

            drawParking(parkingSlots.get(i));
        }
    }

    private void drawParking(Parking parking) {
        int marketIcon = parking.hasCost() ? R.drawable.ic_map_parking : R.drawable.ic_map_parking_free;
        addMarket(parking.getName(), parking.getReferencePoint(), marketIcon);
    }

    private void addMarket(String name, ReferencePoint referencePoint, @DrawableRes int res) {
        BitmapDescriptor bitmap = BitmapUtils.getBitmap(this, res);
        mMap.addMarker(new MarkerOptions()
                .position(referencePoint.getPosition())
                .title(name)
                .icon(bitmap));
    }

    @Override
    public void onClick(View v) {
        updateLocation();
    }

}
