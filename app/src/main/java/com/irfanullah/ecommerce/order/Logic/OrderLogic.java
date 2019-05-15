package com.irfanullah.ecommerce.order.Logic;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Order;

import java.util.ArrayList;

public interface OrderLogic {
    interface View {
        void onOrderLoaded(Order order);
        void onProductLoaded(ArrayList<Order> order);
        void onError(String msg);
        void toastIt(String msg);
        void showRvProgress();
        void hideRvProgress();
        void showProgress();
        void hideProgress();
        void onOrderShipped();
    }

    interface Presenter {
        void makeCheckOutRequest();
        void makeOrderProductRequest();
        ProductsAdapter setUpProductsRV(RecyclerView rv);
        void shipOrder();
    }
}
