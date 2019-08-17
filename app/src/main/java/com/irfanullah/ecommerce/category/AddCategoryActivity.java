package com.irfanullah.ecommerce.category;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
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

public class AddCategoryActivity extends AppCompatActivity implements CatLogic.View {

    private CatPresenter presenter;
    private Context context;
    private final String ACT_TITLE = "Add Category";
    private Button chooseImageBtn,addCatBtn;
    private EditText cat_title_field;
    private ImageView cat_image_view;
    private Uri image_uri = null;
    private int PICK_IMAGE = 87;
    private ProgressBar progressBar;
    private TextView statusTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initObjects();
    }

    private void initObjects() {
        context = this;
        presenter = new CatPresenter(this,context);
       // getActionBar().setTitle("Add Category");
        getSupportActionBar().setTitle(ACT_TITLE);

        chooseImageBtn = findViewById(R.id.chooseProductImageBtn);
        addCatBtn = findViewById(R.id.addCatBtn);
        cat_title_field = findViewById(R.id.service_name);
        cat_image_view = findViewById(R.id.gallery_img);
        progressBar = findViewById(R.id.progressBar);
        statusTextView = findViewById(R.id.statusTextView);

        addCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validateFieldsAndMakeRequest(cat_title_field.getText().toString(),image_uri);
            }
        });

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    public void onCategoryAdded() {
        cat_title_field.setText("");
        cat_image_view.setVisibility(View.GONE);
        SC.toastHere(context,"Category Added.");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){

            image_uri = data.getData();
            SC.logHere(image_uri.toString());
            cat_image_view.setImageURI(image_uri);
            cat_image_view.setVisibility(View.VISIBLE);
        }else {
            SC.toastHere(context,"Error occurred in seleting the image.");
        }
    }

    @Override
    public void onError(String message) {
        SC.toastHere(context,message);
    }

    @Override
    public void onException(String message) {
        SC.toastHere(context,message);
    }

    @Override
    protected void onPause() {
        super.onPause();
       // finish();
    }
}
