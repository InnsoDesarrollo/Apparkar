package com.innso.apparkar.ui.viewModels;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.innso.apparkar.R;
import com.innso.apparkar.api.controller.PlacesController;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.api.models.ParkingPrice;
import com.innso.apparkar.api.models.ReferencePoint;
import com.innso.apparkar.provider.ResourceProvider;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class RegisterViewModel extends ParentViewModel {

    private final int TYPE_PARKING = 1;

    private BehaviorSubject<Boolean> showParkingInformation = BehaviorSubject.createDefault(false);

    private PublishSubject<Boolean> finishRegister = PublishSubject.create();

    public ObservableField<String> placeAddress = new ObservableField<>();

    private LatLng placeLocation;

    private ParkingViewModel parkingViewModel;

    public int locationType;

    @Inject
    PlacesController placesController;

    @Inject
    ResourceProvider resourceProvider;

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
        if (parkingViewModel.isValidToSave()) {
            showProgressDialog(R.string.copy_please_wait);
            Parking parking = createParking();
            placesController.addParkingSlot(parking).subscribe(this::registerComplete, this::showServiceError);
        } else {
            showSnackBarError(resourceProvider.getString(R.string.error_complete_all_fields));
        }
    }

    private void registerComplete(Parking object) {
        hideProgressDialog();
        finishRegister.onNext(true);
    }

    private Parking createParking() {
        Parking parking = new Parking();

        if (placeLocation != null) {

            String address = placeAddress.get().split(",")[0];

            parking.setReferencePoint(new ReferencePoint(placeLocation.latitude, placeLocation.longitude, address));
        }
        parking.setName(parkingViewModel.name.get());

        parking.setPrices(getParkingPrice());

        return parking;
    }

    private ParkingPrice getParkingPrice() {
        ParkingPrice prices = null;
        if (parkingViewModel.hasCost.get()) {
            String carCost = parkingViewModel.cartCost.get();
            String motorbikeCost = parkingViewModel.motorbikeCost.get();
            String bikeCost = parkingViewModel.bikeCost.get();
            prices = new ParkingPrice(parkingViewModel.costDescription.get());
            prices.setCarCost(TextUtils.isEmpty(carCost) ? -1 : Integer.parseInt(carCost));
            prices.setMotorbikeCost(TextUtils.isEmpty(motorbikeCost) ? -1 : Integer.parseInt(motorbikeCost));
            prices.setBikeCost(TextUtils.isEmpty(bikeCost) ? -1 : Integer.parseInt(bikeCost));
        }
        return prices;
    }

    public ParkingViewModel getParkingViewModel() {
        return parkingViewModel;
    }

    public Observable<Boolean> showParkingInformation() {
        return showParkingInformation.subscribeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> finisRegisterObserver() {
        return finishRegister.subscribeOn(AndroidSchedulers.mainThread());
    }
}
