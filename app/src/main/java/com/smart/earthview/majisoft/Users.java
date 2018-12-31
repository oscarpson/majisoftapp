package com.smart.earthview.majisoft;

import com.smart.earthview.majisoft.meterStatus.StatusClass;

import java.util.ArrayList;

/**
 * Created by Belal on 14/04/17.
 */

public class Users {

    private ArrayList<StatusClass> users;

    public Users() {

    }

    public ArrayList<StatusClass> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<StatusClass> users) {
        this.users = users;
    }
}
