package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Service {

    @SerializedName("id") private String SERVICE_ID;
    @SerializedName("service_name") private String SERVICENAME;
    @SerializedName("service_cost") private String SERVICE_COST;
    @SerializedName("services") private ArrayList<Service> services;


    @SerializedName("isFound") private boolean isFound;
    @SerializedName("isSaved") private boolean isSaved;
    @SerializedName("isError") private boolean isError;
    @SerializedName("isAuthenticated") private boolean isAuthenticated;


    @SerializedName("message") private String MESSAGE;


    public boolean isSaved() {
        return isSaved;
    }

    public String getSERVICE_ID() {
        return SERVICE_ID;
    }

    public String getSERVICENAME() {
        return SERVICENAME;
    }

    public String getSERVICE_COST() {
        return SERVICE_COST;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public boolean isFound() {
        return isFound;
    }

    public boolean isError() {
        return isError;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }
}
