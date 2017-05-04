package com.innso.apparkar.di.modules;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innso.apparkar.api.config.ApiConfig;
import com.innso.apparkar.api.config.TokenAuthenticator;
import com.innso.apparkar.api.service.InformationApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final int TIME_OUT = 20;

    @Provides
    @Singleton
    public InformationApi informtaionApi(@Named("firebase") Retrofit retrofit) {
        return retrofit.create(InformationApi.class);
    }

    @Provides
    @Singleton
    public Gson gson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    @Named("firebase")
    public Retrofit retrofitFirebase(ApiConfig apiConfig, Gson gson, TokenAuthenticator authenticator) {

        OkHttpClient.Builder httpClient = getHttpClientBuilder(apiConfig)
                .authenticator(authenticator);

        return getRetrofitBuilder(httpClient.build(), apiConfig.getFirebaseUrl(), gson).build();
    }

    @Provides
    @Singleton
    public Retrofit retrofit(ApiConfig apiConfig, Gson gson, TokenAuthenticator authenticator) {

        OkHttpClient.Builder httpClient = getHttpClientBuilder(apiConfig)
                .authenticator(authenticator);

        return getRetrofitBuilder(httpClient.build(), "andrespae9z0.com", gson).build();
    }

    private OkHttpClient.Builder getHttpClientBuilder(ApiConfig apiConfig) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS);

        if (apiConfig.DEBUG) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            clientBuilder.addInterceptor(logging);
        }

        return clientBuilder;
    }

    private Retrofit.Builder getRetrofitBuilder(OkHttpClient httpClient, String url, Gson gson) {
        return new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
    }

}
