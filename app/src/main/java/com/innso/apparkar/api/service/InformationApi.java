package com.innso.apparkar.api.service;

import com.innso.apparkar.api.models.Parking;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface InformationApi {

    @GET("parking_lots.json")
    Observable<List<Parking>> getParkingData();

    @PUT("parking_lots.json")
    Observable<Objects> addNewParking(Parking newParking);

}
