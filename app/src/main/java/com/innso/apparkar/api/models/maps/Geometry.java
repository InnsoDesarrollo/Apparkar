package com.innso.apparkar.api.models.maps;

import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("location")
    MapsLocation location;

    public MapsLocation getLocation() {
        return location;
    }

    public static class MapsLocation{

        @SerializedName("lat")
        double latitude;

        @SerializedName("lng")
        double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

}
