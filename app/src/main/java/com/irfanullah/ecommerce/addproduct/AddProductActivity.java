package com.irfanullah.ecommerce.addproduct;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.R;

public class AddProductActivity extends AppCompatActivity implements AddProductLogic.View {

    private EditText product_name,product_quantity, product_price, product_description;
    private ImageView product_image_view;
    private Button chooseImageBtn,addProductBtn;
    private ProgressBar progressBar;
    private TextView statusTextView;
    private Context context;
    private AddProductPresenter presenter;
    private int PICK_IMAGE = 346;
    private Uri image_uri;
    private String CAT_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initObjects();
    }

    private void initObjects(){
        context = this;
        CAT_ID = getIntent().getExtras().getString("cat_id");
        product_name = findViewById(R.id.service_name);
        product_quantity = findViewById(R.id.productQuantity);
        product_price = findViewById(R.id.service_cost);
        product_image_view = findViewById(R.id.gallery_img);
        chooseImageBtn = findViewById(R.id.chooseProductImageBtn);
        addProductBtn = findViewById(R.id.addServiceBtn);
        progressBar = findViewById(R.id.progressBar);
        statusTextView = findViewById(R.id.statusTextView);
        product_description = findViewById(R.id.product_description);
        getSupportActionBar().setTitle("Add Product");
        presenter = new AddProductPresenter(context,this);

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProductImage();
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validateInputFieldsAndMakeProductAddRequest(product_name.getText().toString(),product_price.getText().toString(),product_quantity.getText().toString(),CAT_ID,image_uri,product_description.getText().toString());
            }
        });


    }

    @Override
    public void onProductAdded() {
        product_image_view.setVisibility(View.GONE);
        product_name.setText("");
        product_quantity.setText("");
        product_price.setText("");
        product_description.setText("");
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
