package com.irfanullah.ecommerce.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Messenger {

    @SerializedName("isAuthenticated")
    private Boolean IS_AUTHENTICATED;
    @SerializedName("isError")
    private Boolean IS_ERROR;
    @SerializedName("isEmpty")
    private Boolean IS_EMPTY;
    @SerializedName("isFound")
    private Boolean IS_FOUND;
    @SerializedName("msg")
    private String MESSAGE;
    @SerializedName("sender_id")
    private int SENDER_ID;
    @SerializedName("reciever_id")
    private int RECIEVER_ID;
    @SerializedName("p_id")
    private int CHAT_ID;
    @SerializedName("created_at")
    private String CREATED_AT;
    @SerializedName("updated_at")
    private String UPDATED_AT;
    @SerializedName("message")
    private String RESPONSE_MESSAGE;
    @SerializedName("messages")
    private ArrayList<Messenger> MESSENGER;
    @SerializedName("isSent")
    private Boolean IS_SENT;
    @SerializedName("last_message")
    private Messenger LAST_MESSAGE;
    @SerializedName("count_unread_messages")
    private int COUNT_READ_MESSAGES;

    @SerializedName("last_message_count")
    private int LAST_MESSAGE_COUNT;

    @SerializedName("is_reade")
    private int is_read;

    public int getLAST_MESSAGE_COUNT() {
        return LAST_MESSAGE_COUNT;
    }

    public Messenger getLAST_MESSAGE() {
        return LAST_MESSAGE;
    }

    public int getCOUNT_READ_MESSAGES() {
        return COUNT_READ_MESSAGES;
    }

    public Boolean getIS_SENT() {
        return IS_SENT;
    }

    public Boolean getIS_AUTHENTICATED() {
        return IS_AUTHENTICATED;
    }

    public Boolean getIS_ERROR() {
        return IS_ERROR;
    }

    public Boolean getIS_EMPTY() {
        return IS_EMPTY;
    }

    public Boolean getIS_FOUND() {
        return IS_FOUND;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public int getSENDER_ID() {
        return SENDER_ID;
    }

    public int getRECIEVER_ID() {
        return RECIEVER_ID;
    }

    public int getCHAT_ID() {
        return CHAT_ID;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public String getUPDATED_AT() {
        return UPDATED_AT;
    }

    public String getRESPONSE_MESSAGE() {
        return RESPONSE_MESSAGE;
    }

    public ArrayList<Messenger> getMESSENGER() {
        return MESSENGER;
    }

    public int getIs_read() {
        return is_read;
    }
}

