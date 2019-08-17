package com.irfanullah.ecommerce.product;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.Glib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.R;

public class ProductActivity extends AppCompatActivity implements ProductLogic.View {

    private TextView product_name, product_price,product_quantity,products_sold, product_description;
    private ImageView product_image;
    private Context context;
    private String PRODUCT_ID = "";
    private ProductPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        PRODUCT_ID = getIntent().getExtras().getString("product_id");
        initObjects();
    }

    private void initObjects(){
        context = this;
        product_name = findViewById(R.id.service_name);
        product_price = findViewById(R.id.product_price);
        product_quantity = findViewById(R.id.product_quantity);
        products_sold = findViewById(R.id.products_sold);
        product_image = findViewById(R.id.product_image);
        product_description = findViewById(R.id.product_description);

        presenter = new ProductPresenter(context,this,PRODUCT_ID);
        presenter.getProductRequest();


    }


    @Override
    public void onProductLoaded(Product product) {
        product_name.setText(product.getPRODUCT_NAME());
        product_price.setText("Price: " +product.getPRODUCT_PRICE());
        product_quantity.setText("Quantity: "+product.getPRODUCT_QUANTITY());
        products_sold.setText("Sold: "+product.getPRODUCT_SOLD());
        product_description.setText(product.getPRODUCT_DESC());

        if(!product.getPRODUCT_IMAGE().isEmpty()) {
            Glib.loadImage(context,product.getPRODUCT_IMAGE()).into(product_image);
        }
    }

    @Override
    public void onError(String msg) {
        SC.toastHere(context,msg);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onProductLoadFail() {
        SC.toastHere(context,"Loading Product failed.");
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SC.logHere("back is pressed: ");
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

}
