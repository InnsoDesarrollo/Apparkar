package com.innso.apparkar.api.models;


import com.google.gson.annotations.SerializedName;

public class Parking {

    private transient final String KEY_PENDING_REVIEW = "pending_review";

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

    @SerializedName("status")
    String status;

    public Parking() {
        status = KEY_PENDING_REVIEW;
    }

    public void setReferencePoint(ReferencePoint referencePoint) {
        this.referencePoint = referencePoint;
    }

    public void setPrices(ParkingPrice prices) {
        this.prices = prices;
    }

    public void setCountLikes(int countLikes) {
        this.countLikes = countLikes;
    }

    public void setCountDislikes(int countDislikes) {
        this.countDislikes = countDislikes;
    }

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
