package com.innso.apparkar.di.modules;

import com.innso.apparkar.api.controller.InformationController;
import com.innso.apparkar.api.service.InformationApi;

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

}
