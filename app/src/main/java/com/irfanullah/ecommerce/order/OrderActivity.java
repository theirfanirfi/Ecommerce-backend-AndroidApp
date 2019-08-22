package com.irfanullah.ecommerce.order;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.order.Logic.OrderLogic;
import com.irfanullah.ecommerce.order.Logic.OrderPresenter;
import com.irfanullah.ecommerce.order.Logic.ProductsAdapter;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements OrderLogic.View {
    TextView name, address,postalCode,totalPrice, quanity,towncity,email,phoneNumber,orderedOn, orderShippedOn;
    RecyclerView rv;
    Context context;
    OrderPresenter presenter;
    ProductsAdapter productsAdapter;
    String CHECKOUT_ID = "";
    int IS_PROCESSED = 0;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initObjects();
    }

    private void initObjects(){
        CHECKOUT_ID = getIntent().getExtras().getString("checkout_id");
        IS_PROCESSED = getIntent().getExtras().getInt("is_processed");
        context = this;
        name = findViewById(R.id.orderName);
        address = findViewById(R.id.address);
        towncity = findViewById(R.id.townCity);
        postalCode = findViewById(R.id.postalCode);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        quanity = findViewById(R.id.productsQuantity);
        totalPrice = findViewById(R.id.totalPrice);
        orderedOn = findViewById(R.id.orderedOnDate);
        orderShippedOn = findViewById(R.id.orderShippedOn);
        rv = findViewById(R.id.proRV);
        presenter = new OrderPresenter(context,this,CHECKOUT_ID);
        SC.logHere(this.CHECKOUT_ID);

        productsAdapter = presenter.setUpProductsRV(rv);
        presenter.makeCheckOutRequest();
        presenter.makeOrderProductRequest();
    }
    @Override
    public void onOrderLoaded(Order order) {
        name.setText("Full name: "+order.getFIRSTNAME()+" "+order.getLASTNAME());
        address.setText("Address: "+order.getADDRESS());
        towncity.setText("Town City: "+order.getTOWNCITY());
        postalCode.setText("Postal Code: "+order.getPOSTALCODE());
        email.setText("Email: "+order.getEMAIL());
        phoneNumber.setText("Phone#: "+order.getPHONENUMBER());
        quanity.setText("Products Ordered: "+order.getQUANTITY());
        totalPrice.setText("Total Price: "+order.getTOTALPRICE());
        orderedOn.setText("Ordered on: "+order.getCREATED_AT());
        orderShippedOn.setText("Order Shipped on: "+order.getDISPATCHED_AT());
        totalPrice.setText("Total Price: "+order.getTOTALPRICE());

        if(order.getIS_PROCESSED() == 1){
            onCreateOptionsMenu(menu);
        }
    }

    @Override
    public void onProductLoaded(ArrayList<Order> order) {
        SC.iLogHere(order.size());
        productsAdapter.notifyAdapter(order);
    }

    @Override
    public void onError(String msg) {
        SC.toastHere(context,msg);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        if(IS_PROCESSED == 0) {
            getMenuInflater().inflate(R.menu.order_menu, menu);
        }else {
            menu.removeItem(R.id.ship_order_menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.ship_order_menu){
            presenter.shipOrder();
        } else if(item.getItemId() == android.R.id.home){
            onBackPressed();

        }


        return true;
    }

    @Override
    public void onOrderShipped() {
        this.IS_PROCESSED = 1;
        onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
