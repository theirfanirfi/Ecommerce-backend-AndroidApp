package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Order {
    @SerializedName("firstname") private String FIRSTNAME;
        @SerializedName("lastname") private String LASTNAME;
    @SerializedName("address") private String ADDRESS;
    @SerializedName("town") private String TOWNCITY;
    @SerializedName("postalcode") private String POSTALCODE;
    @SerializedName("email") private String EMAIL;
    @SerializedName("phonenumber") private String PHONENUMBER;
    @SerializedName("total_price") private String TOTALPRICE;
    @SerializedName("message") private String Message;
    @SerializedName("isAuthenticated") private boolean isAuthenticated;
    @SerializedName("isFound") private boolean isFound;
    @SerializedName("isError") private boolean isError;
    @SerializedName("cat_id") private String CAT_ID;
    @SerializedName("id") private String CHECKOUT_ID;
    @SerializedName("order_id") private String ORDER_ID;
    @SerializedName("is_processed") private int IS_PROCESSED;
    @SerializedName("products_quantity") private String QUANTITY;
    @SerializedName("is_shipment_charges") private String IS_SHIPMENT_CHARGES;
    @SerializedName("shipment_charges") private String SHIPMENT_CHARGES;
    @SerializedName("cat_title") private String CAT_TITLE;
    @SerializedName("products") private ArrayList<Product> PRODUCTS;
    @SerializedName("orders") private ArrayList<Order> ORDERS;
    @SerializedName("order") private Order ORDER;
    @SerializedName("product_id") private String PRODUCT_ID;
    @SerializedName("product_name") private String PRODUCT_NAME;
    @SerializedName("quantity_ordered") private String QUANTITY_ORDERED;
    @SerializedName("product_image") private String PRODUCT_IMAGE;
    @SerializedName("product_price") private String PRODUCT_PRICE;
    @SerializedName("isShipped") private boolean IS_SHIPPED;

    public boolean isIS_SHIPPED() {
        return IS_SHIPPED;
    }

    public String getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public String getQUANTITY_ORDERED() {
        return QUANTITY_ORDERED;
    }

    public String getPRODUCT_IMAGE() {
        return PRODUCT_IMAGE;
    }

    public String getPRODUCT_PRICE() {
        return PRODUCT_PRICE;
    }

    public String getPHONENUMBER() {
        return PHONENUMBER;
    }

    public String getPOSTALCODE() {
        return POSTALCODE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public Order getORDER() {
        return ORDER;
    }

    public ArrayList<Order> getORDERS() {
        return ORDERS;
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
