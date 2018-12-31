package com.smart.earthview.majisoft;

import com.google.gson.annotations.SerializedName;

public class ResponseRetro {
    @SerializedName("error")
    Boolean error;
    @SerializedName("message")
    String message;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
