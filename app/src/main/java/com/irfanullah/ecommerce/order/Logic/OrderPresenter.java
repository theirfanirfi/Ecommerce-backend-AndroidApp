package com.irfanullah.ecommerce.order.Logic;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.Models.Product;

import java.util.ArrayList;

public class OrderPresenter implements OrderLogic.Presenter {
    private Context context;
    private OrderLogic.View view;
    private ArrayList<Product> products;
    private ProductsAdapter productsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public OrderPresenter(Context context, OrderLogic.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void makeCheckOutRequest() {

    }

    @Override
    public void makeOrderProductRequest() {

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
}
