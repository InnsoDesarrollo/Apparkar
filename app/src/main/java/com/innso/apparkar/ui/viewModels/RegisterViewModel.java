package com.innso.apparkar.ui.viewModels;

public class RegisterViewModel extends ParentViewModel {

    public ParkingViewModel parkingViewModel;

    public RegisterViewModel() {
        this.parkingViewModel = new ParkingViewModel();
        addChild(parkingViewModel);
    }
}
