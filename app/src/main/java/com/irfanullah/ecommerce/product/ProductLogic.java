package com.irfanullah.ecommerce.product;

import com.irfanullah.ecommerce.Models.Product;

public interface ProductLogic {
    interface View {
        void onProductLoaded(Product product);
        void onError(String msg);
        void showProgress();
        void hideProgress();
        void onProductLoadFail();
    }

    interface Presenter {
        void getProductRequest();

    }
}
