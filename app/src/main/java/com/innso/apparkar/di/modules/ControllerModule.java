package com.innso.apparkar.di.modules;

import com.innso.apparkar.api.controller.ApplicationController;
import com.innso.apparkar.api.controller.PlacesController;
import com.innso.apparkar.api.controller.MapsController;
import com.innso.apparkar.api.service.ApplicationApi;
import com.innso.apparkar.api.service.PlacesApi;
import com.innso.apparkar.api.service.MapsApi;
import com.innso.apparkar.provider.ResourceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {

    @Provides
    @Singleton
    PlacesController applicationApiController(PlacesApi applicationApi) {
        return new PlacesController(applicationApi);
    }

    @Provides
    @Singleton
    MapsController mapsController(MapsApi mapsApi, ResourceProvider resourceProvider){
        return new MapsController(mapsApi, resourceProvider);
    }

    @Provides
    @Singleton
    ApplicationController applicationController(ApplicationApi applicationApi){
        return new ApplicationController(applicationApi);
    }
}
