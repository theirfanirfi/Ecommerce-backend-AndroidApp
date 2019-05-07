package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id") private String USER_ID;
    @SerializedName("name") private String USERNAME;
    @SerializedName("email") private String EMAIL;
    @SerializedName("role") private String ROLE;
    @SerializedName("role_name") private String ROLE_NAME;
    @SerializedName("isEmpty") private boolean isEmpty;
    @SerializedName("isAuthenticated") private boolean isAuthenticated;
    @SerializedName("isError") private boolean isError;
    @SerializedName("invalidCreds") private boolean invalidCreds;
    @SerializedName("isLoggedIn") private boolean isLoggedIn;
    @SerializedName("user") private User user;
    @SerializedName("token") private String TOKEN;

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
}
