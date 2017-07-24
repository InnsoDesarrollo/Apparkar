package com.innso.apparkar.di.modules;


import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.innso.apparkar.api.config.ApiConfig;
import com.innso.apparkar.api.config.TokenAuthenticator;
import com.innso.apparkar.managers.preferences.PrefsManager;
import com.innso.apparkar.provider.ResourceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context appContext() {
        return app;
    }

    @Provides
    @Singleton
    public ResourceProvider resourceProvider(Context context) {
        return new ResourceProvider(context);
    }

    @Provides
    @Singleton
    public PrefsManager preferenceManager(Context context) {
        PrefsManager.init(context);
        return PrefsManager.getInstance();
    }

    @Provides
    @Singleton
    public ApiConfig getApiConfig(Context context, PrefsManager prefsManager) {
        return new ApiConfig(context, prefsManager);
    }

    @Provides
    @Singleton
    public TokenAuthenticator tokenAuthenticator(Context context) {
        return new TokenAuthenticator(context);
    }

    @Provides
    @Singleton
    public FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

}
