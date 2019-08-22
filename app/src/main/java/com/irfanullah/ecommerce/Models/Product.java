package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Product {
    @SerializedName("product_id") private String PRODUCT_ID;
    @SerializedName("checkout_id") private String CHECKOUT_ID;
    @SerializedName("quantity") private String PRODUCT_QUANTITY;
    @SerializedName("sold") private String PRODUCT_SOLD;
    @SerializedName("available") private String PRODUCT_AVAILABLE;
    @SerializedName("product_name") private String PRODUCT_NAME;
    @SerializedName("quantity_ordered") private String QUANTITY_ORDERED;
    @SerializedName("product_image") private String PRODUCT_IMAGE;
    @SerializedName("created_at") private String CREATED_AT;
    @SerializedName("cat_id") private String CAT_ID;
    @SerializedName("product_price") private String PRODUCT_PRICE;
    @SerializedName("isFound") private boolean isFound;
    @SerializedName("isError") private boolean isError;
    @SerializedName("isSaved") private boolean isSaved;
    @SerializedName("isUploaded") private boolean isUploaded;
    @SerializedName("message") private String Message;
    @SerializedName("isAuthenticated") private boolean isAuthenticated;
    @SerializedName("isDeleted") private boolean isDeleted;
    @SerializedName("products") private ArrayList<Product> PRODUCTS;
    @SerializedName("product") private Product PRODUCT;
    @SerializedName("product_desc") private String PRODUCT_DESC;

    public String getPRODUCT_DESC() {
        return PRODUCT_DESC;
    }

    public String getCHECKOUT_ID() {
        return CHECKOUT_ID;
    }

    public String getQUANTITY_ORDERED() {
        return QUANTITY_ORDERED;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Product getPRODUCT() {
        return PRODUCT;
    }

    public String getPRODUCT_PRICE() {
        return PRODUCT_PRICE;
    }

    public ArrayList<Product> getPRODUCTS() {
        return PRODUCTS;
    }

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
