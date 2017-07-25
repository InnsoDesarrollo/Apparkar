package com.innso.apparkar.api.controller;

import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.api.service.InformationApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                .flatMap(this::convertMapToList)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<Parking>> convertMapToList(Map<String, Parking> data) {
        return Observable.just(new ArrayList<>(data.values()));
    }

    public Observable<Parking> addParkingSlot(Parking parking) {

        return informationApi.addNewParking(parking)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
