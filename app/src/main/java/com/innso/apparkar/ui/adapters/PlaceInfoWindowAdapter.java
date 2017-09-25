package com.innso.apparkar.ui.adapters;


import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.databinding.PopupPlaceBinding;
import com.innso.apparkar.ui.binding.ParkingBinding;
import com.innso.apparkar.ui.viewModels.ParkingViewModel;

public class PlaceInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private PopupPlaceBinding binding;

    public static final String PARKING_PLACE = "parking";

    public PlaceInfoWindowAdapter(PopupPlaceBinding binding) {
        this.binding = binding;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return renderInformationWindows(marker);
    }

    @Nullable
    private View renderInformationWindows(Marker marker) {
        String placeTag = marker.getTitle();
        switch (placeTag) {
            case PARKING_PLACE:
                renderParking(new ParkingViewModel((Parking) marker.getTag()));
                return binding.getRoot();
        }
        return null;
    }

    private void renderParking(ParkingViewModel parkingViewModel) {
        binding.textViewPlaceName.setText(parkingViewModel.name.get());
        binding.textViewPlaceAddress.setText(parkingViewModel.address.get());
        binding.textViewPlaceNavigator.setOnClickListener(parkingViewModel::onNavegate);
        ParkingBinding.setParkingValue(binding.imageViewValueCar, parkingViewModel.cartCost.get());
        ParkingBinding.setParkingValue(binding.imageViewValueMotorbike, parkingViewModel.motorbikeCost.get());
        ParkingBinding.setParkingValue(binding.imageViewValueBike, parkingViewModel.bikeCost.get());
        ParkingBinding.setCharge(binding.textViewValuesTitle, parkingViewModel.costDescription.get());
    }
}
