package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Gallery {
    @SerializedName("id") private String GALLERY_ID;
    @SerializedName("image_name") private String IMAGE_NAME;
    @SerializedName("image_title") private String IMAGE_TITLE;
    @SerializedName("user_id") private String USER_ID;
    @SerializedName("isFound") private boolean isFound;
    @SerializedName("isError") private boolean isError;
    @SerializedName("isSaved") private boolean isSaved;
    @SerializedName("isUploaded") private boolean isUploaded;
    @SerializedName("message") private String Message;
    @SerializedName("isAuthenticated") private boolean isAuthenticated;
    @SerializedName("isDeleted") private boolean isDeleted;
    @SerializedName("gallery") private ArrayList<Gallery> GALLERY;

    public ArrayList<Gallery> getGALLERY() {
        return GALLERY;
    }

    public String getGALLERY_ID() {
        return GALLERY_ID;
    }

    public String getIMAGE_NAME() {
        return IMAGE_NAME;
    }

    public String getIMAGE_TITLE() {
        return IMAGE_TITLE;
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

    public boolean isSaved() {
        return isSaved;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public String getMessage() {
        return Message;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
