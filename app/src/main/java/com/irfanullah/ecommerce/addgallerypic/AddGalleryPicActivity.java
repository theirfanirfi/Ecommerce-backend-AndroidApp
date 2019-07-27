package com.irfanullah.ecommerce.addgallerypic;

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

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.R;

public class AddGalleryPicActivity extends AppCompatActivity implements AddGalleryPicLogic.View {

    private EditText image_title;
    private ImageView image_view;
    private Button chooseImageBtn,addImageBtn;
    private ProgressBar progressBar;
    private TextView statusTextView;
    private Context context;
    private AddGalleryPicPresenter presenter;
    private int PICK_IMAGE = 346;
    private Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgallery_pic);
        initObjects();
    }

    private void initObjects(){
        context = this;
        image_title = findViewById(R.id.image_title);

        image_view = findViewById(R.id.gallery_img);
        chooseImageBtn = findViewById(R.id.chooseProductImageBtn);
        addImageBtn = findViewById(R.id.uploadImage);
        progressBar = findViewById(R.id.progressBar);
        statusTextView = findViewById(R.id.statusTextView);


        getSupportActionBar().setTitle("Add Image to Gallery");
        presenter = new AddGalleryPicPresenter(context,this);

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validateInputFieldsAndMakeImageAddRequest(image_title.getText().toString(),image_uri);
            }
        });


    }

    @Override
    public void onImageAdded() {
        image_view.setVisibility(View.GONE);
        image_title.setText("");
        SC.toastHere(context,"Image Uploaded");
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

    private void chooseImage(){
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
            image_view.setImageURI(image_uri);
            makeImageViewVisible();
        }
    }

    private void makeImageViewVisible(){
        image_view.setVisibility(View.VISIBLE);
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
