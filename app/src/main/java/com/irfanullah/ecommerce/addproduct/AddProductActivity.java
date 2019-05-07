package com.irfanullah.ecommerce.addproduct;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.R;

public class AddProductActivity extends AppCompatActivity implements AddProductLogic.View {

    private EditText product_name,product_quantity;
    private ImageView product_image_view;
    private Button chooseImageBtn,addProductBtn;
    private ProgressBar progressBar;
    private TextView statusTextView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initObjects();
    }

    private void initObjects(){
        context = this;
        product_name = findViewById(R.id.product_name);
        product_quantity = findViewById(R.id.productQuantity);
        product_image_view = findViewById(R.id.product_img);
        chooseImageBtn = findViewById(R.id.chooseProductImageBtn);
        addProductBtn = findViewById(R.id.addProductBtn);
        progressBar = findViewById(R.id.progressBar);
        statusTextView = findViewById(R.id.statusTextView);
        getSupportActionBar().setTitle("Add Product");
    }

    @Override
    public void onProductAdded() {
        product_image_view.setVisibility(View.GONE);
        product_name.setText("");
        product_quantity.setText("");
        SC.toastHere(context,"Product Added.");
    }

    @Override
    public void onError(String msg) {
        SC.toastHere(context,msg);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        statusTextView.setVisibility(View.GONE);
    }
}
