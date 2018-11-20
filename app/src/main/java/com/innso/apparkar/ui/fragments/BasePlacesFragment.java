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
import com.innso.apparkar.api.controller.PlacesController;
import com.innso.apparkar.api.models.BasePlace;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.databinding.FragmentPlacesListBinding;
import com.innso.apparkar.ui.BaseActivity;
import com.innso.apparkar.ui.BaseFragment;
import com.innso.apparkar.ui.adapters.GenericAdapter;
import com.innso.apparkar.ui.factories.GenericAdapterFactory;
import com.innso.apparkar.ui.interfaces.GenericItemAbstract;
import com.innso.apparkar.ui.interfaces.GenericItemView;
import com.innso.apparkar.ui.items.ParkingItem;
import com.innso.apparkar.ui.viewModels.ParkingViewModel;
import com.innso.apparkar.util.ErrorUtil;

import java.lang.annotation.Retention;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class BasePlacesFragment extends BaseFragment {

    public static final int PARKING_LIST = 0;
    public static final int PETROL_STATION_LIST = 1;
    public static final int CAR_WASH_LIST = 2;
    public static final int OTHER_PLACER_LIST = 3;

    protected FragmentPlacesListBinding binding;

    protected GenericAdapter adapter;

    private int fragmentType;

    @Inject
    PlacesController placesController;

    public static BasePlacesFragment newInstance(@fragmentType int fragmentType) {
        BasePlacesFragment newFragment = new BasePlacesFragment();
        newFragment.fragmentType = fragmentType;
        return newFragment;
    }

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
        getPlaces().observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updatePlaces, e -> ((BaseActivity) getActivity()).showError(binding.getRoot(), ErrorUtil.getMessageError(e)));
    }

    public Observable<List<BasePlace>> getPlaces() {
        switch (fragmentType) {
            case PARKING_LIST:
                return placesController.getParkingSlots();
            case PETROL_STATION_LIST:
                return placesController.getPetrolStations();
            case OTHER_PLACER_LIST:
            default:
                return placesController.getOtherPlacesSlots();
        }
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

        binding.recyclerPlaces.setNestedScrollingEnabled(false);

        binding.recyclerPlaces.setAdapter(adapter);
    }

    private void updatePlaces(List<BasePlace> basePlaces) {
        adapter.clear();
        for (int i = 0; i < basePlaces.size(); i++) {
            if (basePlaces.get(i) instanceof Parking) {
                adapter.addItem(new GenericItemAbstract(new ParkingViewModel((Parking) basePlaces.get(i))));
            }
        }
    }

    @Retention(SOURCE)
    @IntDef({PARKING_LIST, PETROL_STATION_LIST, CAR_WASH_LIST, OTHER_PLACER_LIST})
    public @interface fragmentType {
    }
}
