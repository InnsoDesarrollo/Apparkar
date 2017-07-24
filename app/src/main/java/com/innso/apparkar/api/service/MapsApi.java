package com.innso.apparkar.api.service;

import com.innso.apparkar.api.models.maps.MapsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsApi {

    @GET("api/geocode/json")
    Observable<MapsResponse> getAddress(@Query("latlng") String latLgn);

    @GET("api/geocode/json")
    Observable<MapsResponse> getLatLgn(@Query("address") String address);
}
