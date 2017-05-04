package com.innso.apparkar.api.service;


import com.innso.apparkar.api.models.Parking;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface InformationApi {

    @GET("Parkings.json")
    Observable<List<Parking>> getParkingData();

}
