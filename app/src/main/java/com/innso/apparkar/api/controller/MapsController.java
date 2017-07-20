package com.innso.apparkar.api.controller;

import com.innso.apparkar.api.models.maps.MapsResponse;
import com.innso.apparkar.api.models.maps.Result;
import com.innso.apparkar.api.service.MapsApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapsController {

    private MapsApi mapsApi;

    public MapsController(MapsApi mapsApi) {
        this.mapsApi = mapsApi;
    }

    public Observable<String> getParkingSlots(double lat, double lgn) {
        String latLognFormat = lat + "," + lgn;
        return mapsApi.getAddress(latLognFormat)
                .subscribeOn(Schedulers.io())
                .flatMap(this::getAddress)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<String> getAddress(MapsResponse mapsResponse) {
        List<Result> result = mapsResponse.getResults();
        String address = "";
        if (result != null && !result.isEmpty()) {
            address = result.get(0).getFormattedAddress();
        }
        return Observable.just(address);
    }


}
