package com.innso.apparkar.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innso.apparkar.api.controller.PlacesController;
import com.innso.apparkar.api.models.Parking;

import java.util.List;

import javax.inject.Inject;

public class PetrolStationsListFragment extends BasePlacesFragment {

    @Inject
    PlacesController placesController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getComponent().inject(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        placesController.getParkingSlots().subscribe(this::updateParkingSlots);
    }

    private void updateParkingSlots(List<Parking> parkingSlots) {
        for (int i = 0; i < parkingSlots.size(); i++) {
            //adapter.addItem(new ParkingViewModel(parkingSlots.get(i)));
        }
    }
}
