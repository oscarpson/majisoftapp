package com.smart.earthview.majisoft.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import retrofit2.http.Field;

@Entity
public class MeterReading {

    @PrimaryKey(autoGenerate = true)
     int EntryId;
     String AccNumber;
     String CurrentReading;
     String MtrReader;
     String  MtrStatus;
     String Rdate;
     String Location;
     String ActiveStatus;

    public MeterReading() {
    }

    public MeterReading(int entryId, String accNumber, String currentReading, String mtrReader, String mtrStatus, String rdate, String location, String activeStatus) {
        EntryId = entryId;
        AccNumber = accNumber;
        CurrentReading = currentReading;
        MtrReader = mtrReader;
        MtrStatus = mtrStatus;
        Rdate = rdate;
        Location = location;
        ActiveStatus = activeStatus;
    }

    public int getEntryId() {
        return EntryId;
    }

    public void setEntryId(int entryId) {
        EntryId = entryId;
    }

    public String getAccNumber() {
        return AccNumber;
    }

    public void setAccNumber(String accNumber) {
        AccNumber = accNumber;
    }

    public String getCurrentReading() {
        return CurrentReading;
    }

    public void setCurrentReading(String currentReading) {
        CurrentReading = currentReading;
    }

    public String getMtrReader() {
        return MtrReader;
    }

    public void setMtrReader(String mtrReader) {
        MtrReader = mtrReader;
    }

    public String getMtrStatus() {
        return MtrStatus;
    }

    public void setMtrStatus(String mtrStatus) {
        MtrStatus = mtrStatus;
    }

    public String getRdate() {
        return Rdate;
    }

    public void setRdate(String rdate) {
        Rdate = rdate;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(String activeStatus) {
        ActiveStatus = activeStatus;
    }
}
