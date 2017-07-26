package com.innso.apparkar.api.controller;

import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.api.service.PlacesApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlacesController {

    private PlacesApi placesApi;

    public PlacesController(PlacesApi placesApi) {
        this.placesApi = placesApi;
    }

    public Observable<List<Parking>> getParkingSlots() {
        return placesApi.getParkingData()
                .subscribeOn(Schedulers.io())
                .flatMap(this::convertMapToList)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<Parking>> convertMapToList(Map<String, Parking> data) {
        return Observable.just(new ArrayList<>(data.values()));
    }

    public Observable<Parking> addParkingSlot(Parking parking) {

        return placesApi.addNewParking(parking)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
