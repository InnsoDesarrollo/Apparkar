package com.innso.apparkar.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innso.apparkar.R;
import com.innso.apparkar.api.controller.InformationController;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.databinding.FragmentPlacesListBinding;
import com.innso.apparkar.ui.BaseFragment;
import com.innso.apparkar.ui.adapters.GenericAdapter;
import com.innso.apparkar.ui.factories.GenericAdapterFactory;
import com.innso.apparkar.ui.interfaces.GenericItemView;
import com.innso.apparkar.ui.items.ParkingItem;
import com.innso.apparkar.ui.viewModels.ParkingViewModel;

import java.util.List;

import javax.inject.Inject;

public class PlacesListFragment extends BaseFragment {

    private FragmentPlacesListBinding binding;

    private GenericAdapter adaper;

    @Inject
    InformationController informationController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_places_list, container, false);
        getComponent().inject(this);
        initViews();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        informationController.getParkingSlots().subscribe(this::updateParkingSlots);
    }

    private void initViews() {
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        adaper = new GenericAdapter(new GenericAdapterFactory() {
            @Override
            public GenericItemView onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ParkingItem(parent.getContext());
            }
        });

        binding.recyclerPlaces.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.recyclerPlaces.setAdapter(adaper);
    }

    private void updateParkingSlots(List<Parking> parkingSlots) {
        for (int i = 0; i < parkingSlots.size(); i++) {
            adaper.addItem(new ParkingViewModel(parkingSlots.get(i)));
        }
    }
}
