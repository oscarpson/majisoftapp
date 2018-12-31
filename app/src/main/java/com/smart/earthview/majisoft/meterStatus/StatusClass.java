package com.smart.earthview.majisoft.meterStatus;

public class StatusClass {
    //String pReading,acc_no,userName,cDate,mactive,zone;
    String EntryId,AccNumber, CurrentReading, MtrReader, MtrStatus,Rdate,Location,ActiveStatus;

    public StatusClass() {
    }

    public StatusClass(String entryId, String accNumber, String currentReading, String mtrReader, String mtrStatus, String rdate, String location, String activeStatus) {
        EntryId = entryId;
        AccNumber = accNumber;
        CurrentReading = currentReading;
        MtrReader = mtrReader;
        MtrStatus = mtrStatus;
        Rdate = rdate;
        Location = location;
        ActiveStatus = activeStatus;
    }

    public String getEntryId() {
        return EntryId;
    }

    public void setEntryId(String entryId) {
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
