package com.innso.apparkar.api.config;


import android.content.Context;

import com.innso.apparkar.R;
import com.innso.apparkar.managers.preferences.PrefsManager;

public class ApiConfig {

    public static final String BEARER = "Bearer ";
    public final boolean DEBUG = true;
    private Context context;

    private PrefsManager prefsManager;
    private String resourceUrl;

    public ApiConfig(Context context, PrefsManager prefsManager) {
        this.context = context;
        this.prefsManager = prefsManager;
    }

    public String getFirebaseUrl() {
        return context.getString(R.string.url_firebase);
    }

}
