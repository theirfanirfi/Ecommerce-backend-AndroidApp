package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Order {
    @SerializedName("firstname") private String FIRSTNAME;
    @SerializedName("lastname") private String LASTNAME;
    @SerializedName("address") private String ADDRESS;
    @SerializedName("town") private String TOWNCITY;
    @SerializedName("total_price") private String TOTALPRICE;
    @SerializedName("message") private String Message;
    @SerializedName("isAuthenticated") private boolean isAuthenticated;
    @SerializedName("isFound") private boolean isFound;
    @SerializedName("isError") private boolean isError;
    @SerializedName("cat_id") private String CAT_ID;
    @SerializedName("checkout_id") private String CHECKOUT_ID;
    @SerializedName("order_id") private String ORDER_ID;
    @SerializedName("is_processed") private int IS_PROCESSED;
    @SerializedName("products_quantity") private String QUANTITY;
    @SerializedName("is_shipment_charges") private String IS_SHIPMENT_CHARGES;
    @SerializedName("shipment_charges") private String SHIPMENT_CHARGES;
    @SerializedName("cat_title") private String CAT_TITLE;
    @SerializedName("products") private ArrayList<Product> PRODUCTS;
    @SerializedName("orders") private ArrayList<Order> ORDER;

    public ArrayList<Order> getORDERS() {
        return ORDER;
    }

    public String getTOWNCITY() {
        return TOWNCITY;
    }

    public String getTOTALPRICE() {
        return TOTALPRICE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public String getCHECKOUT_ID() {
        return CHECKOUT_ID;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public int getIS_PROCESSED() {
        return IS_PROCESSED;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public String getIS_SHIPMENT_CHARGES() {
        return IS_SHIPMENT_CHARGES;
    }

    public String getSHIPMENT_CHARGES() {
        return SHIPMENT_CHARGES;
    }

    public String getMessage() {
        return Message;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public boolean isFound() {
        return isFound;
    }

    public boolean isError() {
        return isError;
    }

    public String getCAT_ID() {
        return CAT_ID;
    }

    public String getCAT_TITLE() {
        return CAT_TITLE;
    }

    public ArrayList<Product> getPRODUCTS() {
        return PRODUCTS;
    }
}
