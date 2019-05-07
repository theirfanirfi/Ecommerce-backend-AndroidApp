package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category {

    @SerializedName("cat_id") private String CAT_ID;
    @SerializedName("cat_title") private String CAT_TITLE;
    @SerializedName("user_id") private String USER_ID;
    @SerializedName("cat_image") private String CAT_IMAGE;
    @SerializedName("isFound") private boolean isFound;
    @SerializedName("isError") private boolean isError;
    @SerializedName("isSaved") private boolean isSaved;
    @SerializedName("isUploaded") private boolean isUploaded;
    @SerializedName("message") private String Message;
    @SerializedName("isAuthenticated") private boolean isAuthenticated;
    @SerializedName("cats") private ArrayList<Category> CATEGORIES;

    public boolean isSaved() {
        return isSaved;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public String getMessage() {
        return Message;
    }

    public Category(String CAT_TITLE, String CAT_IMAGE) {
        this.CAT_TITLE = CAT_TITLE;
        this.CAT_IMAGE = CAT_IMAGE;
    }

    public String getCAT_ID() {
        return CAT_ID;
    }

    public String getCAT_TITLE() {
        return CAT_TITLE;
    }

    public String getUSER_ID() {
        return USER_ID;
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

    public ArrayList<Category> getCATEGORIES() {
        return CATEGORIES;
    }

    public String getCAT_IMAGE() {
        return CAT_IMAGE;
    }
}
