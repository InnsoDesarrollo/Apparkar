package com.innso.apparkar.api.models;


import com.google.gson.annotations.SerializedName;

public class ParkingPrice {

    @SerializedName("description")
    String description;

    @SerializedName("car")
    int carCost;

    @SerializedName("bike")
    int bikeCost = -1;

    @SerializedName("motorbike")
    int motorbikeCost = -1;

    public String getDescription() {
        return description;
    }

    public int getCarCost() {
        return carCost;
    }

    public int getBikeCost() {
        return bikeCost;
    }

    public int getMotorbikeCost() {
        return motorbikeCost;
    }
}
