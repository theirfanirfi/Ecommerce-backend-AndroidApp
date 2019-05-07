package com.irfanullah.ecommerce.products.Logic;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.products.Adapter.ProductsAdapter;

import java.util.ArrayList;

public class ProductsPresenter implements ProductsLogic.Presenter {

    private Context context;
    private ProductsLogic.View view;
    private ArrayList<Product> products;
    private ProductsAdapter productsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ProductsPresenter(Context context, ProductsLogic.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public ProductsAdapter setUpProductsRV(RecyclerView rv) {
        products = new ArrayList<>();
        productsAdapter = new ProductsAdapter(products,context);
        layoutManager = new LinearLayoutManager(context);

        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(productsAdapter);

        return productsAdapter;
    }

    @Override
    public void getProductsRequest() {

    }
}
