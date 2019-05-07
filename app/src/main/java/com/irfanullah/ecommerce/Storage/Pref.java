package com.irfanullah.ecommerce.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.User;

public class Pref {
    public static String PREF_FILE_NAME = "ecom_panel_file";
    public static String PREF_USER_DETAILS = "user";
    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharedPreference(Context context){
        return context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferenceEditor(Context context){
        return getSharedPreference(context).edit();
    }

    public static User getUser(Context context){

        String user_json = getSharedPreference(context).getString(PREF_USER_DETAILS,"");
        Gson gson = new Gson();
        User user = gson.fromJson(user_json,User.class);
        return user;
    }

    public static boolean isLoggedIn(Context context){
        return getSharedPreference(context).contains(PREF_USER_DETAILS) ? true : false;
    }
}
