package com.irfanullah.ecommerce.editproduct;

import android.net.Uri;

public interface EditProductLogic {
    interface View {

        void onProductAdded();
        void onError(String msg);
        void showProgress();
        void hideProgress();
    }

    interface Preseneter {
        void validateInputFieldsAndMakeProductAddRequest(String name, String price, String quantity, String cat_id, Uri image_uri);
    }
}
