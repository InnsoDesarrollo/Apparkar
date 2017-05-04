package com.innso.apparkar.di.modules;


import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.innso.apparkar.provider.CarWashProvider;
import com.innso.apparkar.provider.ParkingProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseListenerModule {

    @Provides
    @Singleton
    ParkingProvider parkingProvider(Context context, FirebaseDatabase firebaseDatabase) {
        return new ParkingProvider(context, firebaseDatabase);
    }

    @Provides
    @Singleton
    CarWashProvider carWashProvider() {
        return new CarWashProvider();
    }

}
