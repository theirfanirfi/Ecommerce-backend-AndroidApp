package com.irfanullah.ecommerce.products.Logic;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.products.Adapter.ProductsAdapter;

import java.util.ArrayList;

public interface ProductsLogic {
    interface View {
        void onProductsLoaded(ArrayList<Product> products);
        void onError(String message);
        void showProgressBar();
        void hideProgressBar();
    }

    interface Presenter {
        ProductsAdapter setUpProductsRV(RecyclerView rv);
        void getProductsRequest();
    }
}
