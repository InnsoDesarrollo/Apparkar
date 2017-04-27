package com.innso.apparkar.di.modules;


import com.innso.apparkar.provider.CarWashProvider;
import com.innso.apparkar.provider.ParkingsProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseListenerModule {

    @Provides
    @Singleton
    ParkingsProvider parkingsProvider() {
        return new ParkingsProvider();
    }

    @Provides
    @Singleton
    CarWashProvider carWashProvider() {
        return new CarWashProvider();
    }

}
