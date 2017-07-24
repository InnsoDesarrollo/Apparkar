package com.innso.apparkar.ui.viewModels;

import android.view.View;

import com.innso.apparkar.R;
import com.innso.apparkar.api.controller.InformationController;
import com.innso.apparkar.api.models.Parking;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class RegisterViewModel extends ParentViewModel {

    private final int TYPE_PARKING = 1;

    private BehaviorSubject<Boolean> showParkingInformation = BehaviorSubject.createDefault(false);
    private BehaviorSubject<Boolean> showPetrolStationInformation = BehaviorSubject.createDefault(false);

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

    public void onSelectLocationPlace(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.imageView_parking:
                locationType = TYPE_PARKING;
                showParkingInformation.onNext(!showParkingInformation.getValue());
                break;
        }
    }

    public void addNewPlace() {
        if (locationType != -1) {
            if (locationType == TYPE_PARKING) {
                createNewParking();
            }
        } else {
            showSnackBarError("Seleccione un tipo de lugar");
        }
    }

    private void createNewParking() {
        informationController.addParkingSlot(new Parking());
    }


    public Observable<Boolean> showParkingInformation() {
        return showParkingInformation.subscribeOn(AndroidSchedulers.mainThread());
    }

}
