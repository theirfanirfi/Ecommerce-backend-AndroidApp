package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("product_id") private String PRODUCT_ID;
    @SerializedName("quantity") private String PRODUCT_QUANTITY;
    @SerializedName("sold") private String PRODUCT_SOLD;
    @SerializedName("available") private String PRODUCT_AVAILABLE;
    @SerializedName("product_name") private String PRODUCT_NAME;
    @SerializedName("product_image") private String PRODUCT_IMAGE;
    @SerializedName("created_at") private String CREATED_AT;
    @SerializedName("cat_id") private String CAT_ID;
    @SerializedName("isFound") private boolean isFound;
    @SerializedName("isError") private boolean isError;
    @SerializedName("isSaved") private boolean isSaved;
    @SerializedName("isUploaded") private boolean isUploaded;
    @SerializedName("message") private String Message;
    @SerializedName("isAuthenticated") private boolean isAuthenticated;

    public String getPRODUCT_QUANTITY() {
        return PRODUCT_QUANTITY;
    }

    public String getPRODUCT_SOLD() {
        return PRODUCT_SOLD;
    }

    public String getPRODUCT_AVAILABLE() {
        return PRODUCT_AVAILABLE;
    }

    public String getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public String getPRODUCT_IMAGE() {
        return PRODUCT_IMAGE;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public String getCAT_ID() {
        return CAT_ID;
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
}
