package com.innso.apparkar.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
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
import com.innso.apparkar.api.controller.MapsController;
import com.innso.apparkar.api.controller.PlacesController;
import com.innso.apparkar.api.models.BasePlace;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.databinding.ActivityMapsBinding;
import com.innso.apparkar.databinding.PopupPlaceBinding;
import com.innso.apparkar.ui.BaseActivity;
import com.innso.apparkar.ui.adapters.PlaceInfoWindowAdapter;
import com.innso.apparkar.ui.fragments.BasePlacesFragment;
import com.innso.apparkar.ui.views.helpers.BottomNavigationViewHelper;
import com.innso.apparkar.util.BitmapUtils;
import com.innso.apparkar.util.ErrorUtil;
import com.innso.apparkar.util.KeyBoardUtils;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, BottomNavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private BottomSheetBehavior bottomSheetBehavior;

    private GoogleMap mMap;

    private ActivityMapsBinding binding;

    private BasePlacesFragment parkingListFragment;

    private BasePlacesFragment petrolStationsListFragment;

    private BasePlacesFragment carWashFragment;

    private BasePlacesFragment otherPlacesFragment;

    private GoogleApiClient googleApiClient;

    protected Location currentLocation;

    private int currentOpenItem = 0;

    @Inject
    PlacesController placesController;

    @Inject
    MapsController mapsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps);

        getComponent().inject(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        initViews();

        requestLocationPermissions();

        addListeners();
    }

    private void initViews() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.nestedScroll);
        binding.bottomNavigation.post(() -> bottomSheetBehavior.setPeekHeight(binding.bottomNavigation.getHeight()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        BottomNavigationViewHelper.disableShiftMode(binding.bottomNavigation);
        placesController.getParkingSlots().subscribe(this::updateParkingSlots, e -> showError(binding.getRoot(), ErrorUtil.getMessageError(e)));
        bottomSheetBehavior.setBottomSheetCallback(getBottomBehaviorCallback());
        initFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribe();
    }

    private void subscribe() {
        binding.headerSearch.onQueryChange().subscribe(this::moveMapToZone);
    }

    private void moveMapToZone(String zone) {
        if (!TextUtils.isEmpty(zone)) {
            binding.headerSearch.searchMode();
            mapsController.getLocationByAddress(zone).subscribe(this::updatePositionByZone, t -> showError(binding.getRoot(), ErrorUtil.getMessageError(t)));
        } else if (currentLocation != null) {
            updateMapPosition(currentLocation.getLatitude(), currentLocation.getLongitude());
            binding.headerSearch.normalMode();
        }
    }

    private void updatePositionByZone(LatLng position) {
        updateMapPosition(position.latitude, position.longitude);
        binding.headerSearch.normalMode();
    }

    @NonNull
    private BottomSheetBehavior.BottomSheetCallback getBottomBehaviorCallback() {
        return new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.buttonAddLocation.setVisibility(View.INVISIBLE);
                } else {
                    binding.buttonAddLocation.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        };
    }

    private void initFragments() {
        parkingListFragment = BasePlacesFragment.newInstance(BasePlacesFragment.PARKING_LIST);
        petrolStationsListFragment = BasePlacesFragment.newInstance(BasePlacesFragment.PETROL_STATION_LIST);
        carWashFragment = BasePlacesFragment.newInstance(BasePlacesFragment.CAR_WASH_LIST);
        otherPlacesFragment = BasePlacesFragment.newInstance(BasePlacesFragment.OTHER_PLACER_LIST);
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
            googleApiClient.connect();
        }
    }

    private void addListeners() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        binding.buttonLocation.setOnClickListener(this);
        binding.buttonAddLocation.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
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
                replaceFragment(otherPlacesFragment);
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

        initWindowsInformationMap();
    }

    private void initWindowsInformationMap() {
        PopupPlaceBinding popupPlaceBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.popup_place, null, false);
        PlaceInfoWindowAdapter placeInfoWindowAdapter = new PlaceInfoWindowAdapter(popupPlaceBinding);
        mMap.setInfoWindowAdapter(placeInfoWindowAdapter);
    }

    private CameraPosition getCameraPosition(double lat, double lgn) {
        return new CameraPosition.Builder()
                .target(new LatLng(lat, lgn))
                .zoom(15)
                .bearing(0)
                .build();
    }

    private void updateMapPosition(double lat, double lgn) {
        CameraPosition cameraPosition = getCameraPosition(lat, lgn);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        KeyBoardUtils.hideKeyboard(this, binding.getRoot());
    }

    private void updateLocation() {
        if (currentLocation != null && mMap != null) {
            updateMapPosition(currentLocation.getLatitude(), currentLocation.getLongitude());
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
            addMarket(PlaceInfoWindowAdapter.PARKING_PLACE, parking.getReferencePoint().getPosition(), marketIcon, parking);
        }
    }

    private void addMarket(String type, LatLng position, @DrawableRes int res, Object object) {
        BitmapDescriptor bitmap = BitmapUtils.getBitmap(this, res);
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(type)
                .icon(bitmap)).setTag(object);
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
