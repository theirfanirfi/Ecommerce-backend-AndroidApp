package com.irfanullah.ecommerce.editproduct;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.Glib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.addproduct.AddProductLogic;
import com.irfanullah.ecommerce.addproduct.AddProductPresenter;

public class EditProductActivity extends AppCompatActivity implements EditProductLogic.View {

    private EditText product_name,product_quantity, product_price, product_description;
    private ImageView product_image_view;
    private Button chooseImageBtn,addProductBtn;
    private ProgressBar progressBar;
    private TextView statusTextView;
    private Context context;
    private EditProductPresenter presenter;
    private int PICK_IMAGE = 346;
    private Uri image_uri;
    private String CAT_ID = "";
    private String PRODUCT_ID = "";
    private boolean hasImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initObjects();
    }

    private void initObjects(){
        context = this;
        CAT_ID = getIntent().getExtras().getString("cat_id");
        PRODUCT_ID = getIntent().getExtras().getString("product_id");
        product_name = findViewById(R.id.product_name);
        product_quantity = findViewById(R.id.productQuantity);
        product_price = findViewById(R.id.productPrice);
        product_image_view = findViewById(R.id.product_img);
        chooseImageBtn = findViewById(R.id.chooseProductImageBtn);
        addProductBtn = findViewById(R.id.addProductBtn);
        addProductBtn.setText("Update Product");
        progressBar = findViewById(R.id.progressBar);
        statusTextView = findViewById(R.id.statusTextView);
        product_description = findViewById(R.id.product_description);
        getSupportActionBar().setTitle("Update Product");
        presenter = new EditProductPresenter(context,this,PRODUCT_ID);
        presenter.loadProductData();
        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProductImage();
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validateInputFieldsAndMakeProductAddRequest(product_name.getText().toString(),product_price.getText().toString(),product_quantity.getText().toString(),CAT_ID,image_uri,hasImage,product_description.getText().toString());
            }
        });


    }

    @Override
    public void onProductUpdated() {
       // product_image_view.setVisibility(View.GONE);
      //  product_name.setText("");
//        product_quantity.setText("");
//        product_price.setText("");
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

    @Override
    public void onProudctLoaded(Product product) {
        product_name.setText(product.getPRODUCT_NAME());
        product_price.setText(product.getPRODUCT_PRICE());
        product_quantity.setText(product.getPRODUCT_QUANTITY());
        product_description.setText(product.getPRODUCT_DESC());
        Glib.loadImage(context,product.getPRODUCT_IMAGE()).into(product_image_view);
        product_image_view.setVisibility(View.VISIBLE);
    }

    private void chooseProductImage(){
        Intent createChooser = new Intent();
        createChooser.setType("image/*");
        createChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(createChooser, "Select Picture"), PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            image_uri = data.getData();
            product_image_view.setImageURI(image_uri);
            hasImage = true;
            makeImageViewVisible();
        }
    }

    private void makeImageViewVisible(){
        product_image_view.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
