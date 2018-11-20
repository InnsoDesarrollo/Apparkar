package com.innso.apparkar.ui.viewModels;

import android.content.Intent;
import android.databinding.ObservableField;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.innso.apparkar.R;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.api.models.ReferencePoint;
import com.innso.apparkar.provider.ResourceProvider;
import com.innso.apparkar.arch.BaseViewModel;

import java.util.Locale;

import javax.inject.Inject;

public class ParkingViewModel extends BaseViewModel {

    public static final String BY_MINUTE = "by_minute";

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> costDescription = new ObservableField<>();
    public ObservableField<String> cartCost = new ObservableField<>();
    public ObservableField<String> bikeCost = new ObservableField<>();
    public ObservableField<String> motorbikeCost = new ObservableField<>();
    public ObservableField<Integer> countLikes = new ObservableField<>(0);
    public ObservableField<Integer> countDislikes = new ObservableField<>(0);
    public ObservableField<Integer> countComments = new ObservableField<>(0);

    public ObservableField<Boolean> hasCost = new ObservableField<>(false);

    private ReferencePoint referencePoint;

    @Inject
    ResourceProvider resourceProvider;

    public ParkingViewModel() {
        getComponent().inject(this);
    }

    public ParkingViewModel(Parking parking) {
        this();
        this.name.set(parking.getName());
        this.address.set(parking.getReferencePoint().getAddress());
        this.countLikes.set(parking.getCountLikes());
        this.countDislikes.set(parking.getCountDislikes());
        this.hasCost.set(parking.hasCost());
        this.referencePoint = parking.getReferencePoint();
        addValues(parking);
    }

    private void addValues(Parking parking) {
        if (hasCost.get()) {
            this.costDescription.set(parking.getPrices().getDescription());
            this.cartCost.set(String.valueOf(parking.getPrices().getCarCost()));
            this.bikeCost.set(String.valueOf(parking.getPrices().getBikeCost()));
            this.motorbikeCost.set(String.valueOf(parking.getPrices().getMotorbikeCost()));
        } else {
            this.cartCost.set("0");
            this.bikeCost.set("0");
            this.motorbikeCost.set("0");
        }
    }

    public String[] getChargeTypes() {
        return resourceProvider.getStringArray(R.array.parking_charge_type);
    }

    public void setChargeType(int chargeType) {
        String[] charges = getChargeTypes();
        String charge = charges[chargeType];
        if (charge.equals(resourceProvider.getString(R.string.copy_free))) {
            hasCost.set(false);
            return;
        }
        hasCost.set(true);
        if (charge.equals(resourceProvider.getString(R.string.copy_by_minute))) {
            costDescription.set(BY_MINUTE);
        }
    }

    boolean isValidToSave() {
        return !hasCost.get() || !TextUtils.isEmpty(name.get()) && (!TextUtils.isEmpty(cartCost.get()) || !TextUtils.isEmpty(motorbikeCost.get()));
    }

    public void onNavegate(View view) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", referencePoint.getLatitude(), referencePoint.getLongitude());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        view.getContext().startActivity(intent);
    }


}
