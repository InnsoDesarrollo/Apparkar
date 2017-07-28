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
import com.innso.apparkar.api.controller.PlacesController;
import com.innso.apparkar.api.models.BasePlace;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.api.models.ReferencePoint;
import com.innso.apparkar.databinding.ActivityMapsBinding;
import com.innso.apparkar.ui.BaseActivity;
import com.innso.apparkar.ui.fragments.BasePlacesFragment;
import com.innso.apparkar.ui.views.helpers.BottomNavigationViewHelper;
import com.innso.apparkar.util.BitmapUtils;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, BottomNavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private BottomSheetBehavior bottomSheetBehavior;

    private View bottomSheet;

    private GoogleMap mMap;

    private ActivityMapsBinding binding;

    private BasePlacesFragment parkingListFragment;

    private BasePlacesFragment petrolStationsListFragment;

    private BasePlacesFragment otherPlcaesFragment;

    private GoogleApiClient googleApiClient;

    protected Location currentLocation;

    private int currentOpenItem = 0;

    @Inject
    PlacesController placesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps);

        getComponent().inject(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        initViews();

        requestLocationPermissions();

        addListeners();
    }

    private void initViews() {
        bottomSheet = binding.bottomSheet;
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(getResources().getInteger(R.integer.min_height_bottom_map));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        BottomNavigationViewHelper.disableShiftMode(binding.bottomNavigation);
        placesController.getParkingSlots().subscribe(this::updateParkingSlots);
        initFragments();
    }

    private void initFragments() {
        parkingListFragment = BasePlacesFragment.newInstance(BasePlacesFragment.PARKING_LIST);
        petrolStationsListFragment = BasePlacesFragment.newInstance(BasePlacesFragment.OTHER_PLACER_LIST);
        otherPlcaesFragment = BasePlacesFragment.newInstance(BasePlacesFragment.OTHER_PLACER_LIST);
        replaceFragment(parkingListFragment);
    }

    @Override
    protected void successLocationPermission() {
        super.successLocationPermission();
        initLocation();
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
        binding.buttonAddLocation.setOnClickListener(this);
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
            addFragment(item.getItemId());
            binding.buttonAddLocation.setVisibility(View.VISIBLE);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else if (item.getItemId() == currentOpenItem) {
            binding.buttonAddLocation.setVisibility(View.INVISIBLE);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            currentOpenItem = item.getItemId();
            addFragment(item.getItemId());
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
                break;
            case R.id.action_other_places:
                replaceFragment(otherPlcaesFragment);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraPosition cameraPosition = getCameraPosition(4.6097100, -74.0817500);

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.getUiSettings().setRotateGesturesEnabled(false);

        mMap.setOnCameraMoveListener(() -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        updateLocation();
    }


    private CameraPosition getCameraPosition(double lat, double lgn) {
        return new CameraPosition.Builder()
                .target(new LatLng(lat, lgn))
                .zoom(15)
                .bearing(0)
                .build();
    }

    private void updateLocation() {
        if (currentLocation != null && mMap != null) {
            CameraPosition cameraPosition = getCameraPosition(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void updateParkingSlots(List<BasePlace> parkingSlots) {

        for (int i = 0, size = parkingSlots.size(); i < size; i++) {

            drawParking(parkingSlots.get(i));
        }
    }

    private void drawParking(BasePlace place) {
        if (place instanceof Parking) {
            Parking parking = (Parking) place;
            int marketIcon = parking.hasCost() ? R.drawable.ic_map_parking : R.drawable.ic_map_parking_free;
            addMarket(parking.getName(), parking.getReferencePoint(), marketIcon);
        }
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
        int viewId = v.getId();
        switch (viewId) {
            case R.id.button_location:
                updateLocation();
                break;
            case R.id.button_add_location:
                startActivity(new Intent(this, RegisterLocationActivity.class));
        }
    }

}
