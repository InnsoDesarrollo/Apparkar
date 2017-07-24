package com.innso.apparkar.provider;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

public class ResourceProvider {

    private Context context;

    public ResourceProvider(Context context) {
        this.context = context;
    }

    public String getString(@StringRes int id) {
        return context.getString(id);
    }

    public String getString(@StringRes int id, Object... args) {
        return context.getString(id, args);
    }

    public String[] getStringArray(@ArrayRes int id) {
        return context.getResources().getStringArray(id);
    }

    public int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }

    public int getIdentifier(String name, String defType) {
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }

}
