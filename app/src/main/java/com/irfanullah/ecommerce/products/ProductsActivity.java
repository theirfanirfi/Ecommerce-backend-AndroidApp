package com.irfanullah.ecommerce.products;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.addproduct.AddProductActivity;
import com.irfanullah.ecommerce.category.AddCategoryActivity;
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
    private ArrayList<Product> products;
    private FloatingActionButton addProductBtn;
    private String CAT_ID = "";

    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        initObjects();
    }

    private void initObjects() {
        context = this;
        CAT_ID = getIntent().getExtras().getString("cat_id");
        presenter = new ProductsPresenter(context,this,CAT_ID);

        rv = findViewById(R.id.productsRV);
        progressBar = findViewById(R.id.productsProgressBar);
        addProductBtn = findViewById(R.id.fab_btn);

        productsAdapter = presenter.setUpProductsRV(rv);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SC.checkVersion()){
                    requestStoragePermission(context);
                }else {
                    gotoAddProductActivity();
                }
            }
        });

        presenter.getProductsRequest();

    }

    @Override
    public void onProductsLoaded(ArrayList<Product> products) {
        this.products = products;
        productsAdapter.notifyAdapter(products);
    }

    @Override
    public void onError(String message) {
        SC.toastHere(context,message);
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
        Intent productAct = new Intent(context, AddProductActivity.class);
        productAct.putExtra("cat_id",CAT_ID);
        startActivity(productAct);
    }



    @Override
    public void onProductDeleted(int position) {
        SC.iLogHere(products.size());
        products.remove(position);
        SC.iLogHere(products.size());
        productsAdapter.notifyAdapter(products);

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

    public void requestStoragePermission(Context context){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
             gotoAddProductActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE && grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
            gotoAddProductActivity();
        }else {
            SC.toastHere(context,"Error occurred in granting the storage permission. Please try again.");
        }

    }

}
