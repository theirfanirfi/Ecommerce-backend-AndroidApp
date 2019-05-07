package com.irfanullah.ecommerce.addproduct;

import android.net.Uri;

public interface AddProductLogic {
    interface View {

        void onProductAdded();
        void onError(String msg);
        void showProgress();
        void hideProgress();
    }

    interface Preseneter {
        void validateInputFieldsAndMakeProductAddRequest(String name, String quantity, String cat_id, Uri image_uri);
    }
}
