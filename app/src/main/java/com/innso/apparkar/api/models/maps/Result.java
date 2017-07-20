package com.innso.apparkar.api.models.maps;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("formatted_address")
    String formattedAddress;

    @SerializedName("place_id")
    String placeId;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public String getPlaceId() {
        return placeId;
    }
}
