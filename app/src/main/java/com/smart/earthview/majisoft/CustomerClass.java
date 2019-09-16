package com.smart.earthview.majisoft;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
/**
data base entity */

@Entity
public class CustomerClass {
    @NonNull
    @PrimaryKey
    String acc_no;
    String meter_no;
    String userName;
    String prDate;
    String zone;
    String meter_location;

    public CustomerClass() {
    }

    public CustomerClass(String acc_no, String meter_no, String userName, String prDate, String zone, String meter_location) {
        this.acc_no = acc_no;
        this.meter_no = meter_no;
        this.userName = userName;
        this.prDate = prDate;
        this.zone = zone;
        this.meter_location = meter_location;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getMeter_no() {
        return meter_no;
    }

    public void setMeter_no(String meter_no) {
        this.meter_no = meter_no;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrDate() {
        return prDate;
    }

    public void setPrDate(String prDate) {
        this.prDate = prDate;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getMeter_location() {
        return meter_location;
    }

    public void setMeter_location(String meter_location) {
        this.meter_location = meter_location;
    }
}
