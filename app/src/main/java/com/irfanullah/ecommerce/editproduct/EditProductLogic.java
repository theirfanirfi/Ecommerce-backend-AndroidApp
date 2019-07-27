package com.irfanullah.ecommerce.editproduct;

import android.net.Uri;

import com.irfanullah.ecommerce.Models.Product;

public interface EditProductLogic {
    interface View {

        void onProductUpdated();
        void onError(String msg);
        void showProgress();
        void hideProgress();
        void onProudctLoaded(Product product);
    }

    interface Preseneter {
        void validateInputFieldsAndMakeProductAddRequest(String name, String price, String quantity, String cat_id, Uri image_uri,boolean hasImage,String desc);
        void loadProductData();
    }
}
