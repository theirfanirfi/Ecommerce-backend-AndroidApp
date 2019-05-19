package com.irfanullah.ecommerce.membercheckouts;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Order;

import java.util.ArrayList;

public interface MemberCheckoutsLogic {
    interface View {
        void onError(String msg);
        void onOrdersLoaded(ArrayList<Order> orders);
        void showProgress();
        void hideProgress();
        void showToast(String message);
        void onOrderShipped();
    }

    interface Presenter {
        void makeOrdersRequest();
        MemberChecoutsAdapter setUpRV(RecyclerView rv);
        void shipOrder(String CHECKOUT_ID);
    }
}
