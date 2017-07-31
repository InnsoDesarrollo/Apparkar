package com.innso.apparkar.api.controller;

import com.innso.apparkar.BuildConfig;
import com.innso.apparkar.api.models.app.GeneralInformation;
import com.innso.apparkar.api.service.ApplicationApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApplicationController {

    private ApplicationApi applicationApi;

    public ApplicationController(ApplicationApi applicationApi) {
        this.applicationApi = applicationApi;
    }

    public Observable<Boolean> forceUpdate() {
        return applicationApi.getAppVersion()
                .subscribeOn(Schedulers.io())
                .map(this::validateForceUpdate)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Boolean validateForceUpdate(GeneralInformation information) {
        int serverVersion = information.getBuildVersion();
        return serverVersion > BuildConfig.VERSION_CODE;
    }
}
