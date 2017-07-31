package com.innso.apparkar.api.service;

import com.innso.apparkar.api.models.app.GeneralInformation;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApplicationApi {

    @GET("general_information.json")
    Observable<GeneralInformation> getAppVersion();

}
