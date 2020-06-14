package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;
import com.irfanullah.ecommerce.Libraries.RetroLib;

import java.util.ArrayList;

public class Participants {
    @SerializedName("isAuthenticated")
    private Boolean isAuthenticated;
    @SerializedName("isFound")
    private Boolean isFound;
    @SerializedName("isError")
    private Boolean isError;
    @SerializedName("admin_id")
    private int ADMIN_ID;
    @SerializedName("user_id")
    private int USER_ID;
    @SerializedName("name")
    private String USERNAME;

    @SerializedName("profile_image")
    private String USER_PROFILE_IMAGE;


    @SerializedName("message")
    private String MESSAGE;
    @SerializedName("participants")
    private ArrayList<Participants> PARTICIPANTS;
    @SerializedName("id")
    private int CHAT_ID;

    @SerializedName("last_msg") private String LAST_MESSAGE;
    @SerializedName("count") private int UNREAD_MSG_COUNT;

    public String getLAST_MESSAGE() {
        return LAST_MESSAGE;
    }

    public int getUNREAD_MSG_COUNT() {
        return UNREAD_MSG_COUNT;
    }

    public int getCHAT_ID() {
        return CHAT_ID;
    }

    public Boolean getError() {
        return isError;
    }

    public ArrayList<Participants> getPARTICIPANTS() {
        return PARTICIPANTS;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public Boolean getAuthenticated() {
        return isAuthenticated;
    }

    public Boolean getFound() {
        return isFound;
    }

    public int getADMIN_ID() {
        return ADMIN_ID;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getUSER_PROFILE_IMAGE() {
//        return USER_PROFILE_IMAGE;

        if(USER_PROFILE_IMAGE == null){
            return USER_PROFILE_IMAGE;
        }else {
            if (USER_PROFILE_IMAGE.contains("http")) {
                return USER_PROFILE_IMAGE;
            } else {
                return RetroLib.ASSET_URL + "profile/" + USER_PROFILE_IMAGE;
            }
        }
    }
}

