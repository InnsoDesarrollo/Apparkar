package com.innso.apparkar.di.component;


import com.google.firebase.database.FirebaseDatabase;
import com.innso.apparkar.di.modules.AppModule;
import com.innso.apparkar.di.modules.FirebaseListenerModule;
import com.innso.apparkar.di.scope.ActivityScope;
import com.innso.apparkar.managers.preferences.PrefsManager;
import com.innso.apparkar.provider.CarWashProvider;
import com.innso.apparkar.provider.ParkingsProvider;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@ActivityScope
@Component(modules = {AppModule.class, FirebaseListenerModule.class})
public interface AppComponent {

    PrefsManager preferenceManager();

    FirebaseDatabase firebaseDatabase();


    //Listeners
    ParkingsProvider parkingsProvider();

    CarWashProvider carWashProvider();
}
