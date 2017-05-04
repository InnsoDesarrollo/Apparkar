package com.innso.apparkar.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.innso.apparkar.R;
import com.innso.apparkar.api.controller.InformationController;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.databinding.ActivityMapsBinding;
import com.innso.apparkar.provider.ParkingProvider;
import com.innso.apparkar.ui.BaseActivity;
import com.innso.apparkar.ui.views.helpers.BottomNavigationViewHelper;

import java.util.List;

import javax.inject.Inject;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private final int REQUEST_SPLASH = 0;

    private BottomSheetBehavior bottomSheetBehavior;

    private View bottomSheet;

    private GoogleMap mMap;

    private ActivityMapsBinding binding;

    @Inject
    ParkingProvider parkingProvider;

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
    }

    private void initViews() {
        bottomSheet = binding.bottomSheet;
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(getResources().getInteger(R.integer.min_height_bottom_map));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        BottomNavigationViewHelper.disableShiftMode(binding.bottomNavigation);
        informationController.getParkingSlots().subscribe(this::updateParkingSlots);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnCameraMoveListener(() -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void updateParkingSlots(List<Parking> parkingSlots) {
        Log.i("maps", "data");
    }
}
