package com.irfanullah.ecommerce.category;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

public interface CatLogic {

    interface View {
        void onCategoryAdded();
        void showProgress();
        void hideProgress();
        void onError(String message);
        void onException(String message);
    }

    interface Presenter {
        void validateFieldsAndMakeRequest(String cat_title, Uri uri);
        Uri getImageUri(Context context, Bitmap inImage);
        void makeAddCategoryRequest(String cat_title, Uri uri);
    }
}
