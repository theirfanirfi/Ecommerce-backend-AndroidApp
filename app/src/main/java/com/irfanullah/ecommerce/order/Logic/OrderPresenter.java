package com.irfanullah.ecommerce.order.Logic;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.Storage.Pref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPresenter implements OrderLogic.Presenter {
    private Context context;
    private OrderLogic.View view;
    private ArrayList<Order> products;
    private ProductsAdapter productsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String CHECKOUT_ID = "";

    public OrderPresenter(Context context, OrderLogic.View view, String ck_id) {
        this.context = context;
        this.view = view;
        this.CHECKOUT_ID = ck_id;
    }

    @Override
    public void makeCheckOutRequest() {
        RetroLib.getAPIServices().getCheckOutOrder(Pref.getUser(context).getTOKEN(),CHECKOUT_ID).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    Order order = response.body();
                    if(order.isError() || !order.isAuthenticated()){
                        view.hideProgress();
                        view.onError(order.getMessage());
                    }else if(order.isFound()){
                        view.onOrderLoaded(order.getORDER());
                        view.hideProgress();
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
    public void makeOrderProductRequest() {
        RetroLib.getAPIServices().getOrderProducts(Pref.getUser(context).getTOKEN(),CHECKOUT_ID).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    Order order = response.body();
                    if(order.isError() || !order.isAuthenticated()){
                        view.hideProgress();
                        view.onError(order.getMessage());
                    }else if(order.isFound()){
                        //view.onOrderLoaded(order.getORDER());
                        view.onProductLoaded(order.getORDERS());
                        view.hideProgress();
                    }else {
                        view.hideProgress();
                        view.onError(order.getMessage());
                    }
                }else {
                    view.hideRvProgress();
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

    @Override
    public ProductsAdapter setUpProductsRV(RecyclerView rv) {
        products = new ArrayList<>();
        productsAdapter = new ProductsAdapter(products,context);
        layoutManager = new LinearLayoutManager(context);
       // productsAdapter.setOnProductClickListern(this);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(productsAdapter);
        return productsAdapter;
    }

    @Override
    public void shipOrder() {
        RetroLib.getAPIServices().shipOrder(Pref.getUser(context).getTOKEN(),CHECKOUT_ID).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    Order order = response.body();
                    if(order.isError() || !order.isAuthenticated()){
                        view.hideProgress();
                        view.onError(order.getMessage());
                    }else if(order.isIS_SHIPPED()){
                        view.toastIt(order.getMessage());
                        view.hideProgress();
                    }else {
                        view.hideProgress();
                        view.onError(order.getMessage());
                    }
                }else {
                    view.hideRvProgress();
                    view.hideProgress();
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                view.hideRvProgress();
                view.hideProgress();
                view.onError(t.getMessage());
            }
        });
    }
}
