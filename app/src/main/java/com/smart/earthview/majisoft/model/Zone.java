package com.smart.earthview.majisoft.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Zone {
    @NonNull
    @PrimaryKey
    String EntryId;
    String zonename;
    String zonedesc;

    public Zone() {
    }

    public Zone(String entryId, String zonename, String zonedesc) {
        EntryId = entryId;
        this.zonename = zonename;
        this.zonedesc = zonedesc;
    }

    public String getEntryId() {
        return EntryId;
    }

    public void setEntryId(String entryId) {
        EntryId = entryId;
    }

    public String getZonename() {
        return zonename;
    }

    public void setZonename(String zonename) {
        this.zonename = zonename;
    }

    public String getZonedesc() {
        return zonedesc;
    }

    public void setZonedesc(String zonedesc) {
        this.zonedesc = zonedesc;
    }

}
