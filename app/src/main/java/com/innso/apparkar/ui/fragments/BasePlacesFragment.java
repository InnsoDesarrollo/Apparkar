package com.innso.apparkar.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innso.apparkar.R;
import com.innso.apparkar.databinding.FragmentPlacesListBinding;
import com.innso.apparkar.ui.BaseFragment;
import com.innso.apparkar.ui.adapters.GenericAdapter;
import com.innso.apparkar.ui.factories.GenericAdapterFactory;
import com.innso.apparkar.ui.interfaces.GenericItemView;
import com.innso.apparkar.ui.items.ParkingItem;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class BasePlacesFragment extends BaseFragment {

    public static final int PARKING_LIST = 0;
    public static final int PETROL_STATION_LIST = 1;

    protected FragmentPlacesListBinding binding;

    protected GenericAdapter adapter;

    public static BasePlacesFragment newInstance(@fragmentType int fragmentType){
        BasePlacesFragment newFragment;
        if(fragmentType == PARKING_LIST ){
            newFragment = new ParkingListFragment();
        } else {
            newFragment = new PetrolStationsListFragment();
        }
        return newFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_places_list, container, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        adapter = new GenericAdapter(new GenericAdapterFactory() {
            @Override
            public GenericItemView onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ParkingItem(parent.getContext());
            }
        });

        binding.recyclerPlaces.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.recyclerPlaces.setAdapter(adapter);
    }

    @Retention(SOURCE)
    @IntDef({PARKING_LIST, PETROL_STATION_LIST})
    public @interface fragmentType{}
}
