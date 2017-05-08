package com.innso.apparkar.api.models;


import com.google.gson.annotations.SerializedName;

public class Parking {

    @SerializedName("name")
    String name;

    @SerializedName("reference_point")
    ReferencePoint referencePoint;

    @SerializedName("prices")
    ParkingPrice prices;

    @SerializedName("like")
    int countLikes = 0;

    @SerializedName("dislike")
    int countDislikes = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReferencePoint getReferencePoint() {
        return referencePoint;
    }

    public ParkingPrice getPrices() {
        return prices;
    }

    public int getCountLikes() {
        return countLikes;
    }

    public int getCountDislikes() {
        return countDislikes;
    }

    public boolean hasCost() {
        return prices != null;
    }
}
