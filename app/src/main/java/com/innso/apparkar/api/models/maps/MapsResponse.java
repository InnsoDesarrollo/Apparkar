package com.innso.apparkar.api.models.maps;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapsResponse {

    @SerializedName("results")
    List<Result> results;

    @SerializedName("status")
    String status;

    public List<Result> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }
}
