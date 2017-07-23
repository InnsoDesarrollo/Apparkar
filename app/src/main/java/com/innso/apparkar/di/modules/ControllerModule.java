package com.innso.apparkar.di.modules;

import com.innso.apparkar.api.controller.InformationController;
import com.innso.apparkar.api.controller.MapsController;
import com.innso.apparkar.api.service.InformationApi;
import com.innso.apparkar.api.service.MapsApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {

    @Provides
    @Singleton
    InformationController applicationApiController(InformationApi applicationApi) {
        return new InformationController(applicationApi);
    }

    @Provides
    @Singleton
    MapsController mapsController(MapsApi mapsApi){
        return new MapsController(mapsApi);
    }
}
