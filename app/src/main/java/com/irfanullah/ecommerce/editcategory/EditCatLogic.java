package com.irfanullah.ecommerce.editcategory;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.irfanullah.ecommerce.Models.Category;

public interface EditCatLogic {

    interface View {
        void onCategoryUpdated();
        void showProgress();
        void hideProgress();
        void onCategoryLoaded(Category category);
        void onError(String message);
        void onException(String message);
    }

    interface Presenter {
        void validateFieldsAndMakeRequest(String cat_id,String cat_title, Uri uri);
        Uri getImageUri(Context context, Bitmap inImage);
        void makeUpdateCategoryRequest(String cat_id, String cat_title, Uri uri);
    }
}
