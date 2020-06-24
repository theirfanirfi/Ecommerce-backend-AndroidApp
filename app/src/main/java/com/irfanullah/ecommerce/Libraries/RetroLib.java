package com.irfanullah.ecommerce.Libraries;

import com.irfanullah.ecommerce.APIs.APIs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroLib {
//    public final static String IP = "192.168.10.5";
    public final static String IP = "techgiantz.com";
    public final static String BASE_URL = "http://"+IP+"/barber/public/api/";
    public final static String ASSET_URL = "http://"+IP+"/barber/public/uploads/";

    public static APIs getAPIServices(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(APIs.class);
    }
}
