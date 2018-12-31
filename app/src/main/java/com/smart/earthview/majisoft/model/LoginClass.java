package com.smart.earthview.majisoft.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LoginClass {
    @PrimaryKey(autoGenerate = true)
    int logId;
    String uname;
    String password;

    public LoginClass() {
    }

    public LoginClass(int logId, String uname, String password) {
        this.logId = logId;
        this.uname = uname;
        this.password = password;
    }



    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
