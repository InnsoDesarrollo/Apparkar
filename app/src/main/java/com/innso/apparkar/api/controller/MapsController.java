package com.innso.apparkar.api.controller;

import com.google.android.gms.maps.model.LatLng;
import com.innso.apparkar.api.models.maps.Geometry;
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

    public Observable<String> getAddressDescription(double lat, double lgn) {
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

    public Observable<LatLng> getLocaionByAddress(String address) {
        return mapsApi.getLatLgn(address).subscribeOn(Schedulers.io())
                .flatMap(this::getLocation)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<LatLng> getLocation(MapsResponse mapsResponse) {
        List<Result> result = mapsResponse.getResults();
        LatLng locaton = null;
        if (result != null && !result.isEmpty()) {
            Geometry.MapsLocation addressLocation = result.get(0).getLocation();
            locaton = new LatLng(addressLocation.getLatitude(), addressLocation.getLongitude());
        }
        return Observable.just(locaton);
    }


}
