package com.irfanullah.ecommerce.main.Fragments.Gallery;

import android.Manifest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.Models.Gallery;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.addgallerypic.AddGalleryPicActivity;
import com.irfanullah.ecommerce.category.AddCategoryActivity;
import com.irfanullah.ecommerce.main.Adapters.CategoriesAdapter;
import com.irfanullah.ecommerce.main.Adapters.GalleryAdapter;
import com.irfanullah.ecommerce.main.Fragments.Categories.CategoriesLogic;
import com.irfanullah.ecommerce.main.Fragments.Categories.CategoriesPresenter;
import com.irfanullah.ecommerce.main.MainActivity;
import com.irfanullah.ecommerce.products.ProductsActivity;

import java.util.ArrayList;

public class GalleryFrag extends Fragment implements GalleryLogic.View, GalleryAdapter.GalleryImageClickListener {
    private GalleryPresenter presenter;
    private Context context;
    private FloatingActionButton uploadImage;
    private RecyclerView rv;
    GalleryAdapter galleryAdapter;
    private ProgressBar galleryLoadingProgressBar;
    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 888;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.frag_gallery,container,false);
        initObjects(view);
        return view;
    }

    @Override
    public void onGalleryLoaded(ArrayList<Gallery> gallery) {
       // categoriesAdapter.notifyAdapter(categories);
        galleryAdapter.notifyAdapter(gallery);
    }

    private void initObjects(View view){
        context = getContext();
        presenter = new GalleryPresenter(this,context);
        uploadImage = view.findViewById(R.id.fab_btn);
        rv = view.findViewById(R.id.galleryRV);
        galleryLoadingProgressBar = view.findViewById(R.id.progressBar);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.fabButton();
            }
        });
        galleryAdapter = presenter.setUpRecyclerView(rv);
        galleryAdapter.setOnImageClickListener(this);
        presenter.getGalleryRequest();
    }

    @Override
    public void gotoUploadImageActivity() {
        //not coded yet
        if(SC.checkVersion()) {
            requestStoragePermission(context);
        }else {
            Intent uploadImageIntent = new Intent(context, AddGalleryPicActivity.class);
            startActivity(uploadImageIntent);
        }

    }

    @Override
    public void hideProgressBar() {
        galleryLoadingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        galleryLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        SC.toastHere(context,message);
    }

//    @Override
//    public void (int position,Category category) {
//        //SC.toastHere(context,Integer.toString(position)+ " clicked working "+category.getCAT_TITLE());
//        Intent catProducts = new Intent(context, ProductsActivity.class);
//        catProducts.putExtra("cat_id",category.getCAT_ID());
//        startActivity(catProducts);
//    }


    @Override
    public void onImageClickListener(int position, final Gallery gallery) {
//        SC.snackHere(getView(),gallery.getIMAGE_TITLE());
//        SC.toastHere(context,gallery.getGALLERY_ID());

        TextView textView = new TextView(context);
        textView.setText("Do you want to delete this image?");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundColor(Color.WHITE);
        textView.setTextColor(Color.BLACK);
        new AlertDialog.Builder(context)
                .setCustomTitle(textView)
                .setMessage("Do you want to delete this image?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.deleteGalleryPicture(gallery.getGALLERY_ID());
                    }})
                .setNegativeButton("Cancel", null).show();

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
            Intent addCatIntent = new Intent(context, AddGalleryPicActivity.class);
            startActivity(addCatIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE && grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent addCatIntent = new Intent(context, AddGalleryPicActivity.class);
            startActivity(addCatIntent);
        }else {
            SC.toastHere(context,"Error occurred in granting the storage permission. Please try again.");
        }

    }

    @Override
    public void onGalleryImageDeleted(String message) {
        SC.toastHere(context,message);

    }
}
