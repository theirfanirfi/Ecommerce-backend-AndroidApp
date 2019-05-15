package com.irfanullah.ecommerce.products.Logic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.editproduct.EditProductActivity;
import com.irfanullah.ecommerce.product.ProductActivity;
import com.irfanullah.ecommerce.products.Adapter.ProductsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsPresenter implements ProductsLogic.Presenter, ProductsAdapter.ProductClickListener {

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
         this.view.showProgressBar();
        products = new ArrayList<>();
        productsAdapter = new ProductsAdapter(products,context);
        layoutManager = new LinearLayoutManager(context);
        productsAdapter.setOnProductClickListern(this);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(productsAdapter);

        return productsAdapter;
    }

    @Override
    public void getProductsRequest() {
        if(Pref.isLoggedIn(context)){
            makeGetProductRequest();
        }else {
            view.onError("You are not logged in.");
        }
    }

    private void makeGetProductRequest(){
        view.showProgressBar();
        RetroLib.getAPIServices().getProducts(Pref.getUser(context).getTOKEN()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    if(product.isError() || !product.isAuthenticated()){
                        view.hideProgressBar();
                        view.onError("Either you are not loggedin or authorized, or error has occurred during the request.");
                    }else if(product.isFound()){
                        view.hideProgressBar();
                        view.onProductsLoaded(product.getPRODUCTS());
                    }
                }else {
                    view.hideProgressBar();
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.hideProgressBar();
                view.onError(t.getMessage());
            }
        });
    }

    @Override
    public void onProductClicked(int position, Product product) {
        //SC.toastHere(context,product.getPRODUCT_NAME());
        Intent productAct  = new Intent(context, ProductActivity.class);
        productAct.putExtra("product_id",product.getPRODUCT_ID());
        context.startActivity(productAct);
    }


    @Override
    public void onProductLongClicked(final int position, final Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Action")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct(position,product.getPRODUCT_ID());
                    }
                })

                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent editProduct = new Intent(context, EditProductActivity.class);
                        editProduct.putExtra("product_id",product.getPRODUCT_ID());
                        editProduct.putExtra("cat_id",product.getCAT_ID());
                        context.startActivity(editProduct);
                    }
                })

                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    @Override
    public void deleteProduct(int position,String product_id) {
        if(Pref.isLoggedIn(context)){
            if(product_id.isEmpty()){
                view.onError("Product ID must be provided.");
            }else {
                SC.toastHere(context,"Please wait,the product is being deleted.");
                makeDeleteProductRequest(position,product_id);
            }
        }else {
            view.onError("You are not logged in.");
        }
    }

    private void makeDeleteProductRequest(final int position,String product_id){
        RetroLib.getAPIServices().deleteProduct(Pref.getUser(context).getTOKEN(),product_id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();

                    if(product.isError()){
                        view.onError(product.getMessage());
                    }else if(!product.isAuthenticated()){
                        view.onError("You are not logged in to perform this action.");
                    }else if(product.isFound()){
                        if(product.isDeleted()){
                              view.onProductDeleted(position);
                        }else {
                            view.onError(product.getMessage());
                        }
                    }else {
                        view.onError(product.getMessage());
                    }
                }else {
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.onError(t.getMessage());
            }
        });
    }
}
