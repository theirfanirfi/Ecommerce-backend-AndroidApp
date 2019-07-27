package com.irfanullah.ecommerce.addgallerypic;

import android.net.Uri;

public interface AddGalleryPicLogic {
    interface View {

        void onImageAdded();
        void onError(String msg);
        void showProgress();
        void hideProgress();
    }

    interface Preseneter {
        void validateInputFieldsAndMakeImageAddRequest(String name, Uri image_uri);
    }
}
