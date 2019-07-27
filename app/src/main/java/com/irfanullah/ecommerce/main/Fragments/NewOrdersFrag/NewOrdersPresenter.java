package com.irfanullah.ecommerce.main.Fragments.NewOrdersFrag;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.main.Adapters.OrdersAdapter;
import com.irfanullah.ecommerce.main.MainActivityLogic;
import com.irfanullah.ecommerce.order.OrderActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewOrdersPresenter implements MainActivityLogic.NewOrdersPresenter, OrdersAdapter.OrderClickListener, OrdersAdapter.ShipOrderClickListener {

    private MainActivityLogic.NewOrdersView view;
    private Context context;
    private ArrayList<Order> orders;

    public NewOrdersPresenter(MainActivityLogic.NewOrdersView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void makeOrdersRequest() {
        view.showProgress();
        RetroLib.getAPIServices().getNewOrders(Pref.getUser(context).getTOKEN()).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    Order order = response.body();
                    if(order.isError()){
                        view.hideProgress();
                        view.onError(order.getMessage());
                    }else if(order.isAuthenticated()){
                        if(order.isFound()){
                            view.onOrdersLoaded(order.getORDERS());
                            view.hideProgress();
                        }else {
                            view.hideProgress();
                            view.showToast(order.getMessage());
                        }
                    }else {
                        view.hideProgress();
                        view.onError(order.getMessage());
                    }
                }else {
                    view.hideProgress();
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                view.hideProgress();
                view.onError(t.getMessage());
            }
        });
    }

    @Override
    public OrdersAdapter setUpRV(RecyclerView rv) {

        orders = new ArrayList<>();
        OrdersAdapter ordersAdapter = new OrdersAdapter(context,orders);
        ordersAdapter.setOnOrderClickListener(this);
        ordersAdapter.setOnShipOrderClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(ordersAdapter);
        return ordersAdapter;
    }

    @Override
    public void onOrderClick(int position, Order order) {
      //  view.showToast(order.getCHECKOUT_ID());
        Intent orderAct = new Intent(context, OrderActivity.class);
        orderAct.putExtra("checkout_id",order.getCHECKOUT_ID());
        orderAct.putExtra("is_processed",order.getIS_PROCESSED());
        context.startActivity(orderAct);
    }

    @Override
    public void onOrderShipClicked(int position, Order order) {
        shipOrder(order.getCHECKOUT_ID());
    }

    @Override
    public void shipOrder(String CHECKOUT_ID) {
        RetroLib.getAPIServices().shipOrder(Pref.getUser(context).getTOKEN(),CHECKOUT_ID).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    Order order = response.body();
                    if(order.isError() || !order.isAuthenticated()){
                        view.hideProgress();
                        view.onError(order.getMessage());
                    }else if(order.isIS_SHIPPED()){
                        //view.toastIt(order.getMessage());
                        view.onOrderShipped();
                        view.hideProgress();
                    }else {
                        view.hideProgress();
                        view.onError(order.getMessage());
                    }
                }else {
                   // view.hideRvProgress();
                    view.hideProgress();
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                //view.hideRvProgress();
                view.hideProgress();
                view.onError(t.getMessage());
            }
        });
    }
}
