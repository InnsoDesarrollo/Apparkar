package com.innso.apparkar.api.models;


import com.google.gson.annotations.SerializedName;

public class Parking {

    @SerializedName("name")
    private String name;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("free")
    private boolean free;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitud() {
        return latitude;
    }

    public void setLatitud(double latitud) {
        this.latitude = latitud;
    }

    public double getLongitud() {
        return longitude;
    }

    public void setLongitud(double longitud) {
        this.longitude = longitud;
    }

    public Boolean isFree() {
        return free;
    }
}
