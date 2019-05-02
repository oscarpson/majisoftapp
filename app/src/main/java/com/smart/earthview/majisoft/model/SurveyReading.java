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
                String connectionNo;
                String customer;
                String existingconn;
                String subzone;
                String sanitationType;


    public SurveyReading() {
    }

    public SurveyReading(int svyId, String zone, String mdate, String connection, String latslongs, String mastermeter, String serviceLine, String connectionNo, String customer, String existingconn, String subzone, String sanitationType) {
        this.svyId = svyId;
        this.zone = zone;
        this.mdate = mdate;
        this.connection = connection;
        this.latslongs = latslongs;
        this.mastermeter = mastermeter;
        this.serviceLine = serviceLine;
        this.connectionNo = connectionNo;
        this.customer = customer;
        this.existingconn = existingconn;
        this.subzone = subzone;
        this.sanitationType = sanitationType;
    }

    public String getConnectionNo() {
        return connectionNo;
    }

    public void setConnectionNo(String connectionNo) {
        this.connectionNo = connectionNo;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getExistingconn() {
        return existingconn;
    }

    public void setExistingconn(String existingconn) {
        this.existingconn = existingconn;
    }

    public String getSubzone() {
        return subzone;
    }

    public void setSubzone(String subzone) {
        this.subzone = subzone;
    }

    public String getSanitationType() {
        return sanitationType;
    }

    public void setSanitationType(String sanitationType) {
        this.sanitationType = sanitationType;
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
