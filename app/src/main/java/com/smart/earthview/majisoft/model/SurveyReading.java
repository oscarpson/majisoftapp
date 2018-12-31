package com.smart.earthview.majisoft.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SurveyReading {
    @PrimaryKey(autoGenerate = true)
            int svyId;
            String zone;
                String mdate;
               String connection;
               String latslongs;
                String mastermeter;
                String serviceLine;

    public SurveyReading() {
    }

    public SurveyReading(int svyId, String zone, String mdate, String connection, String latslongs, String mastermeter, String serviceLine) {
        this.svyId = svyId;
        this.zone = zone;
        this.mdate = mdate;
        this.connection = connection;
        this.latslongs = latslongs;
        this.mastermeter = mastermeter;
        this.serviceLine = serviceLine;
    }

    public int getSvyId() {
        return svyId;
    }

    public void setSvyId(int svyId) {
        this.svyId = svyId;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getLatslongs() {
        return latslongs;
    }

    public void setLatslongs(String latslongs) {
        this.latslongs = latslongs;
    }

    public String getMastermeter() {
        return mastermeter;
    }

    public void setMastermeter(String mastermeter) {
        this.mastermeter = mastermeter;
    }

    public String getServiceLine() {
        return serviceLine;
    }

    public void setServiceLine(String serviceLine) {
        this.serviceLine = serviceLine;
    }
}
