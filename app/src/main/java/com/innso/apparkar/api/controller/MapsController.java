package com.innso.apparkar.api.controller;

import com.google.android.gms.maps.model.LatLng;
import com.innso.apparkar.R;
import com.innso.apparkar.api.models.maps.Geometry;
import com.innso.apparkar.api.models.maps.MapsResponse;
import com.innso.apparkar.api.models.maps.Result;
import com.innso.apparkar.api.service.MapsApi;
import com.innso.apparkar.provider.ResourceProvider;

import java.util.List;
import java.util.ResourceBundle;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapsController {

    private MapsApi mapsApi;

    private ResourceProvider resourceProvider;

    public MapsController(MapsApi mapsApi, ResourceProvider resourceProvider) {
        this.mapsApi = mapsApi;
        this.resourceProvider = resourceProvider;
    }

    public Observable<String> getAddressDescription(double lat, double lgn) {
        String latLognFormat = lat + "," + lgn;
        return mapsApi.getAddress(resourceProvider.getString(R.string.google_api_key), latLognFormat)
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

    public Observable<LatLng> getLocationByAddress(String address) {
        return mapsApi.getLatLgn(resourceProvider.getString(R.string.google_api_key), address).subscribeOn(Schedulers.io())
                .flatMap(this::getLocation);
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
