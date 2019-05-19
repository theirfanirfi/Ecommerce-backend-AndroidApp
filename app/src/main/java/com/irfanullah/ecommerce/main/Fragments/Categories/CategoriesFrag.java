package com.irfanullah.ecommerce.main.Fragments.Categories;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.category.AddCategoryActivity;
import com.irfanullah.ecommerce.main.Adapters.CategoriesAdapter;
import com.irfanullah.ecommerce.products.ProductsActivity;

import java.util.ArrayList;

public class CategoriesFrag extends Fragment implements CategoriesLogic.View, CategoriesAdapter.CategoryClickListener {
    private CategoriesPresenter presenter;
    private Context context;
    private FloatingActionButton addCat;
    private RecyclerView rv;
    CategoriesAdapter categoriesAdapter;
    private ProgressBar catsLoadingProgressBar;
    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 888;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.category_frag_layout,container,false);
        initObjects(view);
        return view;
    }

    @Override
    public void onCategoriesLoaded(ArrayList<Category> categories) {
        categoriesAdapter.notifyAdapter(categories);
    }

    private void initObjects(View view){
        context = getContext();
        presenter = new CategoriesPresenter(this,context);
        addCat = view.findViewById(R.id.fab_btn);
        rv = view.findViewById(R.id.checkoutsRV);
        catsLoadingProgressBar = view.findViewById(R.id.catsLoadingProgressBar);
        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.fabButton();
            }
        });
        categoriesAdapter = presenter.setUpRecyclerView(rv);
        categoriesAdapter.setOnCategoryClickListener(this);
        presenter.makeCategoriesRequest();
    }

    @Override
    public void gotoAddCategoryActivity() {
        //not coded yet
        if(SC.checkVersion()) {
            requestStoragePermission(context);
        }else {
            Intent addCatIntent = new Intent(context, AddCategoryActivity.class);
            startActivity(addCatIntent);
        }

    }

    @Override
    public void hideProgressBar() {
        catsLoadingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        catsLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        SC.toastHere(context,message);
    }

    @Override
    public void onCategoryClickListener(int position,Category category) {
        //SC.toastHere(context,Integer.toString(position)+ " clicked working "+category.getCAT_TITLE());
        Intent catProducts = new Intent(context, ProductsActivity.class);
        catProducts.putExtra("cat_id",category.getCAT_ID());
        startActivity(catProducts);
    }

    public void requestStoragePermission(Context context){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Intent addCatIntent = new Intent(context, AddCategoryActivity.class);
            startActivity(addCatIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE && grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent addCatIntent = new Intent(context, AddCategoryActivity.class);
            startActivity(addCatIntent);
        }else {
            SC.toastHere(context,"Error occurred in granting the storage permission. Please try again.");
        }

    }
}
