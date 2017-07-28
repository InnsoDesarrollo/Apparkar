package com.innso.apparkar.api.controller;

import com.innso.apparkar.api.models.BasePlace;
import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.api.service.PlacesApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlacesController {

    private final String STATUS_ATTRIBUTE = "\"status\"";

    private final String STATUS_PENDING_REVIEW = "\"pending_review\"";
    private final String STATUS_APPROVED = "\"approved\"";

    private PlacesApi placesApi;

    public PlacesController(PlacesApi placesApi) {
        this.placesApi = placesApi;
    }

    public Observable<List<BasePlace>> getParkingSlots() {
        return placesApi.getParkingData(STATUS_ATTRIBUTE, STATUS_APPROVED)
                .subscribeOn(Schedulers.io())
                .flatMap(this::convertMapToList)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<BasePlace>> getPetrolStations() {
        return Observable.just(new ArrayList<BasePlace>());
    }

    public Observable<List<BasePlace>> getOtherPlacesSlots() {
        return placesApi.getParkingData(STATUS_ATTRIBUTE, STATUS_PENDING_REVIEW)
                .subscribeOn(Schedulers.io())
                .flatMap(this::convertMapToList)
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Observable<List<BasePlace>> convertMapToList(Map<String, Parking> data) {
        return Observable.just(new ArrayList<>(data.values()));
    }


    public Observable<Parking> addParkingSlot(Parking parking) {

        return placesApi.addNewParking(parking)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
