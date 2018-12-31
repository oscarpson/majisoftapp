package com.smart.earthview.majisoft;
import com.google.gson.annotations.SerializedName;
import com.smart.earthview.majisoft.meterStatus.StatusClass;

public class RecordingMeter {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private StatusClass user;

    public RecordingMeter(Boolean error, String message, StatusClass user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public StatusClass getUser() {
        return user;
    }
}