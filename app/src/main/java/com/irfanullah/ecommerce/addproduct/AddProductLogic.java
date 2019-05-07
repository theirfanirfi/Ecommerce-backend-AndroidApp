package com.irfanullah.ecommerce.addproduct;

public interface AddProductLogic {
    interface View {

        void onProductAdded();
        void onError(String msg);
        void showProgress();
        void hideProgress();
    }

    interface Preseneter {
        void validateInputFieldsAndMakeProductAddRequest();
    }
}
