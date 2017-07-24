package com.innso.apparkar.api.models.maps;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("formatted_address")
    String formattedAddress;

    @SerializedName("place_id")
    String placeId;

    @SerializedName("geometry")
    Geometry geometry;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public Geometry.MapsLocation getLocation() {
        return geometry.getLocation();
    }

    public String getPlaceId() {
        return placeId;
    }
}
