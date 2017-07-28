package com.innso.apparkar.api.models;


import com.google.gson.annotations.SerializedName;

public class BasePlace {

    @SerializedName("status")
    String status;

    @SerializedName("reference_point")
    ReferencePoint referencePoint;

}
