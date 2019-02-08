package com.innso.apparkar.di.modules


import android.app.Application
import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.innso.apparkar.api.config.ApiConfig
import com.innso.apparkar.api.config.TokenAuthenticator
import com.innso.apparkar.managers.preferences.PrefsManager
import com.innso.apparkar.provider.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun appContext(): Context = app

    @Provides
    @Singleton
    fun getFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun resourceProvider(context: Context): ResourceProvider = ResourceProvider(context)

    @Provides
    @Singleton
    fun getApiConfig(context: Context, prefsManager: PrefsManager): ApiConfig =
            ApiConfig(context, prefsManager)

    @Provides
    @Singleton
    fun tokenAuthenticator(context: Context): TokenAuthenticator = TokenAuthenticator(context)

    @Provides
    @Singleton
    fun preferenceManager(context: Context): PrefsManager {
        PrefsManager.init(context)
        return PrefsManager.getInstance()
    }

}
