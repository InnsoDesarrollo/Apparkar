package com.innso.apparkar.api.models.app;

import com.google.gson.annotations.SerializedName;

public class GeneralInformation {

    @SerializedName("app_version")
    int buildVersion;

    public int getBuildVersion() {
        return buildVersion;
    }
}
