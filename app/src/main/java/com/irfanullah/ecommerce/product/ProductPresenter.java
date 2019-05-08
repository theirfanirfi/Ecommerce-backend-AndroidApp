package com.irfanullah.ecommerce.product;

import android.content.Context;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.Storage.Pref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPresenter implements ProductLogic.Presenter {
    private Context context;
    private ProductLogic.View view;
    private String PRODUCT_ID = "";

    public ProductPresenter(Context context, ProductLogic.View view, String PRODUCT_ID) {
        this.context = context;
        this.view = view;
        this.PRODUCT_ID = PRODUCT_ID;
    }

    @Override
    public void getProductRequest() {
        if(Pref.isLoggedIn(context)){
            if(PRODUCT_ID.isEmpty()){
                view.onError("Product ID must be provided.");
            }else {
                makeRequest();
            }
        }else {
            view.onError("You are not logged in.");
        }
    }

    public void makeRequest(){
        RetroLib.getAPIServices().getProduct(Pref.getUser(context).getTOKEN(),PRODUCT_ID).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    if(product.isError() || !product.isAuthenticated()){
                        view.onError("Either you are not logged in, or an error has occurred.");
                        view.onProductLoadFail();
                    }else if(product.isFound()) {
                        view.onProductLoaded(product.getPRODUCT());
                    }
                }else {
                    view.onError(response.message());
                    view.onProductLoadFail();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.onError(t.getMessage());
                view.onProductLoadFail();
            }
        });
    }


}
