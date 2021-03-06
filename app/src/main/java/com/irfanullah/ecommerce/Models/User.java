package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;
import com.irfanullah.ecommerce.Libraries.RetroLib;

import java.util.ArrayList;

public class User {

    @SerializedName("id") private String USER_ID;
    @SerializedName("name") private String USERNAME;
    @SerializedName("email") private String EMAIL;
    @SerializedName("role") private String ROLE;
    @SerializedName("role_name") private String ROLE_NAME;
    @SerializedName("isEmpty") private boolean isEmpty;
    @SerializedName("isAuthenticated") private boolean isAuthenticated;
    @SerializedName("isError") private boolean isError;
    @SerializedName("is_barber") private int isBarber;
    @SerializedName("invalidCreds") private boolean invalidCreds;
    @SerializedName("isLoggedIn") private boolean isLoggedIn;
    @SerializedName("isFound") private boolean isFound;
    @SerializedName("isUpdated") private boolean isUpdated;
    @SerializedName("user") private User user;
    @SerializedName("token") private String TOKEN;
    @SerializedName("profile_image") private String PROFILE_IMAGE;

    @SerializedName("off_day") private String OFFDAY;
    @SerializedName("openingtime") private String OPENINGTIME;
    @SerializedName("closingtime") private String CLOSINGTIME;



    @SerializedName("message") private String MESSAGE;
    @SerializedName("time_diff_minutes") private String TIMEDIFF;
    @SerializedName("members") private ArrayList<User> MEMBERS;

    @SerializedName("totalapp") private String TotalAppointments;


    public String getTotalAppointments() {
        return TotalAppointments;
    }

    public int isBarber() {
        return isBarber;
    }

    public String getOFFDAY() {
        return OFFDAY;
    }

    public String getOPENINGTIME() {
        return OPENINGTIME;
    }

    public String getCLOSINGTIME() {
        return CLOSINGTIME;
    }

    public String getTIMEDIFF() {
        return TIMEDIFF;
    }

    public boolean isUpdated() {
        return isUpdated;
    }


    public String getMESSAGE() {
        return MESSAGE;
    }

    public boolean isFound() {
        return isFound;
    }

    public ArrayList<User> getMEMBERS() {
        return MEMBERS;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getROLE() {
        return ROLE;
    }

    public String getROLE_NAME() {
        return ROLE_NAME;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public boolean isError() {
        return isError;
    }

    public boolean isInvalidCreds() {
        return invalidCreds;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public User getUser() {
        return user;
    }

    public int getIsBarber() {
        return isBarber;
    }

    public String getPROFILE_IMAGE() {
        if(PROFILE_IMAGE == null){
            return PROFILE_IMAGE;
        }else {
            if (PROFILE_IMAGE.contains("http")) {
                return PROFILE_IMAGE;
            } else {
                return RetroLib.ASSET_URL + "profile/" + PROFILE_IMAGE;
            }
        }
    }
}
