package com.innso.apparkar.ui.items;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.innso.apparkar.R;
import com.innso.apparkar.databinding.ItemParkingBinding;
import com.innso.apparkar.ui.interfaces.GenericItemView;
import com.innso.apparkar.ui.viewModels.ParkingViewModel;

public class ParkingItem extends FrameLayout implements GenericItemView<ParkingViewModel> {

    private ItemParkingBinding binding;

    public ParkingItem(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_parking, this, true);
    }

    @Override
    public void bind(ParkingViewModel item) {
        binding.setParking(item);
        binding.executePendingBindings();
    }

    @Override
    public ParkingViewModel getData() {
        return null;
    }
}
