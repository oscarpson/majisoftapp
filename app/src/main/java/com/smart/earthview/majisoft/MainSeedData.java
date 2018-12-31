package com.smart.earthview.majisoft;

import com.google.gson.annotations.SerializedName;
import com.smart.earthview.majisoft.model.Zone;
import com.smart.earthview.majisoft.model.Zones;

import java.util.ArrayList;
import java.util.List;

public class MainSeedData {
    @SerializedName("message")
    String message;

    @SerializedName("error")
    Boolean Error;



    @SerializedName("customer")
    ArrayList<CustomerClass> customerList;

    @SerializedName("zone")
    ArrayList<Zone> zoneList;

    public Boolean getError() {
        return Error;
    }

    public void setError(Boolean error) {
        Error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<CustomerClass> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(ArrayList<CustomerClass> customerList) {
        this.customerList = customerList;
    }

    public MainSeedData(String message, Boolean error, ArrayList<CustomerClass> customerList, ArrayList<Zone> zoneList) {
        this.message = message;
        Error = error;
        this.customerList = customerList;
        this.zoneList = zoneList;
    }

    public ArrayList<Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(ArrayList<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public MainSeedData() {
    }
}
