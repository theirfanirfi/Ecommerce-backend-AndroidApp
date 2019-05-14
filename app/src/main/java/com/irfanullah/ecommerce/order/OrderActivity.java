package com.irfanullah.ecommerce.order;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.order.Logic.OrderLogic;
import com.irfanullah.ecommerce.order.Logic.OrderPresenter;
import com.irfanullah.ecommerce.order.Logic.ProductsAdapter;

public class OrderActivity extends AppCompatActivity implements OrderLogic.View {
    TextView name, address,postalCode,totalPrice, quanity,towncity,email,phoneNumber;
    RecyclerView rv;
    Context context;
    OrderPresenter presenter;
    ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initObjects();
    }

    private void initObjects(){
        context = this;
        name = findViewById(R.id.ordererName);
        address = findViewById(R.id.address);
        towncity = findViewById(R.id.townCity);
        postalCode = findViewById(R.id.postalCode);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        quanity = findViewById(R.id.productsQuantity);
        totalPrice = findViewById(R.id.totalPrice);
        rv = findViewById(R.id.proRV);
        presenter = new OrderPresenter(context,this);
        presenter.setUpProductsRV(rv);
    }
    @Override
    public void onOrderLoaded(Order order) {

    }

    @Override
    public void onProductLoaded(Order order) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void toastIt(String msg) {

    }

    @Override
    public void showRvProgress() {

    }

    @Override
    public void hideRvProgress() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
