package com.innso.apparkar.ui.viewModels;

import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.innso.apparkar.R;
import com.innso.apparkar.api.controller.InformationController;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.api.models.ParkingPrice;
import com.innso.apparkar.api.models.ReferencePoint;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class RegisterViewModel extends ParentViewModel {

    private final int TYPE_PARKING = 1;

    private BehaviorSubject<Boolean> showParkingInformation = BehaviorSubject.createDefault(false);

    private PublishSubject<Boolean> finishRegister = PublishSubject.create();

    private LatLng placeLocation;

    public ParkingViewModel parkingViewModel;

    public int locationType;

    @Inject
    InformationController informationController;

    public RegisterViewModel() {
        this.parkingViewModel = new ParkingViewModel();
        this.locationType = -1;
        getComponent().inject(this);
        addChild(parkingViewModel);
    }

    public void setLocation(LatLng newLocation) {
        this.placeLocation = newLocation;
    }

    public void onSelectLocationPlace(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.imageView_parking:
                locationType = TYPE_PARKING;
                showParkingInformation.onNext(!showParkingInformation.getValue());
                break;
        }
    }

    public void addNewPlace(View view) {
        if (locationType != -1) {
            if (locationType == TYPE_PARKING) {
                addNewParking();
            }
        } else {
            showSnackBarError("Seleccione un tipo de lugar");
        }
    }

    private void addNewParking() {
        showLoading();
        Parking parking = createParking();
        informationController.addParkingSlot(parking).subscribe(o -> hideLoading());
    }

    private Parking createParking() {
        Parking parking = new Parking();

        if (placeLocation != null) {
            parking.setReferencePoint(new ReferencePoint(placeLocation.latitude, placeLocation.longitude, parkingViewModel.address.get()));
        }
        parking.setName(parkingViewModel.name.get());

        parking.setPrices(getParkingPrice());

        return parking;
    }

    private ParkingPrice getParkingPrice() {
        ParkingPrice prices = null;
        if (parkingViewModel.hasCost.get()) {
            prices = new ParkingPrice(parkingViewModel.costDescription.get());
            prices.setCarCost(parkingViewModel.cartCost.get());
            prices.setBikeCost(parkingViewModel.bikeCost.get());
            prices.setCarCost(parkingViewModel.motorbikeCost.get());
        }
        return prices;
    }


    public Observable<Boolean> showParkingInformation() {
        return showParkingInformation.subscribeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> finisRegisterObserver() {
        return finishRegister.subscribeOn(AndroidSchedulers.mainThread());
    }

}
