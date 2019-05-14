package com.irfanullah.ecommerce.order.Logic;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Order;

public interface OrderLogic {
    interface View {
        void onOrderLoaded(Order order);
        void onProductLoaded(Order order);
        void onError(String msg);
        void toastIt(String msg);
        void showRvProgress();
        void hideRvProgress();
        void showProgress();
        void hideProgress();
    }

    interface Presenter {
        void makeCheckOutRequest();
        void makeOrderProductRequest();
        ProductsAdapter setUpProductsRV(RecyclerView rv);
    }
}
