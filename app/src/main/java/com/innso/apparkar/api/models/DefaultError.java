package com.innso.apparkar.api.models;


import com.google.gson.annotations.SerializedName;

public class DefaultError {

    @SerializedName("error")
    private Error error;

    public Error getError() {
        return error;
    }

    public class Error {

        @SerializedName("message")
        private String message;

        @SerializedName("code")
        private Object errorCode;

        public String getMessage() {
            return message;
        }

        public Object getErrorCode() {
            return errorCode;
        }
    }

}
