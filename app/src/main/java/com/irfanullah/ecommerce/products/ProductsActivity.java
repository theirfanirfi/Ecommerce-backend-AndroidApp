package com.irfanullah.ecommerce.products;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.products.Adapter.ProductsAdapter;
import com.irfanullah.ecommerce.products.Logic.ProductsLogic;
import com.irfanullah.ecommerce.products.Logic.ProductsPresenter;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity implements ProductsLogic.View {

    private RecyclerView rv;
    private ProgressBar progressBar;
    private ProductsPresenter presenter;
    private Context context;
    private ProductsAdapter productsAdapter;
    private FloatingActionButton addProductBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        initObjects();
    }

    private void initObjects() {
        context = this;
        presenter = new ProductsPresenter(context,this);

        rv = findViewById(R.id.productsRV);
        progressBar = findViewById(R.id.productsProgressBar);
        addProductBtn = findViewById(R.id.fab_btn);

        productsAdapter = presenter.setUpProductsRV(rv);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddProductActivity();
            }
        });

    }

    @Override
    public void onProductsLoaded(ArrayList<Product> products) {
        productsAdapter.notifyAdapter(products);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void gotoAddProductActivity(){

    }
}
