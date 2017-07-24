package com.innso.apparkar.api.controller;

import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.api.service.InformationApi;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InformationController {

    private InformationApi informationApi;

    public InformationController(InformationApi informationApi) {
        this.informationApi = informationApi;
    }

    public Observable<List<Parking>> getParkingSlots() {
        return informationApi.getParkingData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Objects> addParkingSlot(Parking parking) {
        return informationApi.addNewParking(parking)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
