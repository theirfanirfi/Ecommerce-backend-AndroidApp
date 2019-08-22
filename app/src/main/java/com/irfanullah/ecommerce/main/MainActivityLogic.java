package com.irfanullah.ecommerce.main;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.main.Adapters.OOrdersAdapter;
import com.irfanullah.ecommerce.main.Adapters.OrdersAdapter;

import java.util.ArrayList;

public interface MainActivityLogic {

    interface View {
        void gotoProfileActivity();
        void gotoLoginActivit();
        void showToast(String message);
    }

    interface Presenter {
        void onMenuItemSelected(int id);
        void logout();
        void setUpTabsTitle(TabLayout tabLayout);

    }

    interface NewOrdersView {
        void onError(String msg);
        void onOrdersLoaded(ArrayList<Order> orders);
        void showProgress();
        void hideProgress();
        void showToast(String message);
        void onOrderShipped();
    }

    interface NewOrdersPresenter {
        void makeOrdersRequest();
        OrdersAdapter setUpRV(RecyclerView rv);
        void shipOrder(String CHECKOUT_ID);
    }

    interface OOrdersView {
        void onError(String msg);
        void onOrdersLoaded(ArrayList<Order> orders);
        void showProgress();
        void hideProgress();
        void showToast(String message);
    }

    interface OOrdersPresenter {
        void makeOrdersRequest();
        OOrdersAdapter setUpRV(RecyclerView rv);

    }
}
