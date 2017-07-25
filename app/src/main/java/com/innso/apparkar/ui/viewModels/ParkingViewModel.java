package com.innso.apparkar.ui.viewModels;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.innso.apparkar.api.models.Parking;

public class ParkingViewModel extends BaseViewModel {

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> costDescription = new ObservableField<>();
    public ObservableField<Integer> cartCost = new ObservableField<>(0);
    public ObservableField<Integer> bikeCost = new ObservableField<>(0);
    public ObservableField<Integer> motorbikeCost = new ObservableField<>(0);
    public ObservableField<Integer> countLikes = new ObservableField<>(0);
    public ObservableField<Integer> countDislikes = new ObservableField<>(0);
    public ObservableField<Integer> countComments = new ObservableField<>(0);

    public ObservableField<Boolean> hasCost = new ObservableField<>(false);

    public ParkingViewModel() {
    }

    public ParkingViewModel(Parking parking) {
        this.name.set(parking.getName());
        this.address.set(parking.getReferencePoint().getAddress());
        this.countLikes.set(parking.getCountLikes());
        this.countDislikes.set(parking.getCountDislikes());
        this.hasCost.set(parking.hasCost());
        if (hasCost.get()) {
            this.costDescription.set(parking.getPrices().getDescription());
            this.cartCost.set(parking.getPrices().getCarCost());
            this.bikeCost.set(parking.getPrices().getBikeCost());
            this.motorbikeCost.set(parking.getPrices().getMotorbikeCost());
        }
    }

}
