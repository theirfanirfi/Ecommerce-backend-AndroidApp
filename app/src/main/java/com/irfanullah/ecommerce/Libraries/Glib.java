package com.irfanullah.ecommerce.Libraries;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

public class Glib {

    public static RequestBuilder<Drawable> loadImage(Context context, String url){
        return Glide.with(context)
                .load(url);
    }
}
