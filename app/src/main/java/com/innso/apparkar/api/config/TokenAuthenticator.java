package com.innso.apparkar.api.config;

import android.content.Context;

import com.innso.apparkar.managers.preferences.PrefsManager;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


public class TokenAuthenticator implements Authenticator {

    private Context context;

    protected PrefsManager prefsManager;


    public TokenAuthenticator(Context context) {
        this.context = context;
        this.prefsManager = PrefsManager.getInstance();
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        return null;
    }

}
