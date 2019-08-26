package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Appointment {
    @SerializedName("id") private String APPOINTMENT_ID;
    @SerializedName("day") private String DAY;
    @SerializedName("month") private String MONTH;
    @SerializedName("year") private String YEAR;
    @SerializedName("dday") private String FORMATED_DATE;
    @SerializedName("hour") private String HOUR;
    @SerializedName("minutes") private String MINUTES;
    @SerializedName("time_modulation") private String TIME_MOD;
    @SerializedName("time_in_milli") private String TIME_MILLI;
    @SerializedName("timing_id") private String TIMING_ID;
    @SerializedName("time_till") private String TIME_TILL;
    @SerializedName("user_id") private String USER_ID;
    @SerializedName("created_at") private String CREATED_AT;
    @SerializedName("updated_at") private String UPDATED_AT;
    @SerializedName("formated_time") private String FORMATED_TIME;

    @SerializedName("profile_image") private String PROFILE_IMAGE;
    @SerializedName("name") private String USERNAME;

    @SerializedName("time_range") private String TIMERANGE;

    @SerializedName("apts") private ArrayList<Appointment> appointments;

    @SerializedName("isFound") private boolean isFound;
    @SerializedName("isError") private boolean isError;
    @SerializedName("isAuthenticated") private boolean isAuthenticated;


    @SerializedName("message") private String MESSAGE;



    public String getTIMERANGE() {
        return TIMERANGE;
    }

    public String getPROFILE_IMAGE() {
        return PROFILE_IMAGE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getMESSAGE() {
        return MESSAGE;
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

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public String getAPPOINTMENT_ID() {
        return APPOINTMENT_ID;
    }

    public String getDAY() {
        return DAY;
    }

    public String getMONTH() {
        return MONTH;
    }

    public String getYEAR() {
        return YEAR;
    }

    public String getFORMATED_DATE() {
        return FORMATED_DATE;
    }

    public String getHOUR() {
        return HOUR;
    }

    public String getMINUTES() {
        return MINUTES;
    }

    public String getTIME_MOD() {
        return TIME_MOD;
    }

    public String getTIME_MILLI() {
        return TIME_MILLI;
    }

    public String getTIMING_ID() {
        return TIMING_ID;
    }

    public String getTIME_TILL() {
        return TIME_TILL;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public String getUPDATED_AT() {
        return UPDATED_AT;
    }

    public String getFORMATED_TIME() {
        return FORMATED_TIME;
    }
}
