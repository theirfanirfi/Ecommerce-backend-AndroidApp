package com.irfanullah.ecommerce.main.Fragments.OlderOrdersFrag;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.main.Adapters.OOrdersAdapter;
import com.irfanullah.ecommerce.main.Adapters.OrdersAdapter;
import com.irfanullah.ecommerce.main.MainActivityLogic;
import com.irfanullah.ecommerce.order.OrderActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OOrdersPresenter implements MainActivityLogic.OOrdersPresenter, OOrdersAdapter.OrderClickListener {

    private MainActivityLogic.OOrdersView view;
    private Context context;
    private ArrayList<Order> orders;

    public OOrdersPresenter(MainActivityLogic.OOrdersView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void makeOrdersRequest() {
        view.showProgress();
        RetroLib.getAPIServices().getOldOrders(Pref.getUser(context).getTOKEN()).enqueue(new Callback<Order>() {
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
    public OOrdersAdapter setUpRV(RecyclerView rv) {

        orders = new ArrayList<>();
        OOrdersAdapter ordersAdapter = new OOrdersAdapter(context,orders);
        ordersAdapter.setOnOrderClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(ordersAdapter);
        return ordersAdapter;
    }

    @Override
    public void onOrderClick(int position, Order order) {
        Intent orderAct = new Intent(context, OrderActivity.class);
        orderAct.putExtra("checkout_id",order.getCHECKOUT_ID());
        orderAct.putExtra("is_processed",order.getIS_PROCESSED());
        context.startActivity(orderAct);
    }
}
