package com.smart.earthview.majisoft;

import com.google.gson.annotations.SerializedName;
import com.smart.earthview.majisoft.meterStatus.StatusClass;

public class NewRecordResults {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private StatusClass status;

    public NewRecordResults(Boolean error, String message, StatusClass status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public StatusClass getStatus() {
        return status;
    }
}
