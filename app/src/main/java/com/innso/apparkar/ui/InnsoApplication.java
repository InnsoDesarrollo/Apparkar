package com.innso.apparkar.ui;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.innso.apparkar.di.component.AppComponent;
import com.innso.apparkar.di.component.DaggerAppComponent;
import com.innso.apparkar.di.modules.AppModule;

import io.fabric.sdk.android.Fabric;


public class InnsoApplication extends Application {

    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        updateDagger();
        Fabric.with(this, new Crashlytics());
    }

    public void updateDagger() {

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

}
