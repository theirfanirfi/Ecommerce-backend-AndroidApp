package com.irfanullah.ecommerce.editcategory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.Glib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.category.CatLogic;
import com.irfanullah.ecommerce.category.CatPresenter;

public class EditCategoryActivity extends AppCompatActivity implements EditCatLogic.View {

    private EditCatPresenter presenter;
    private Context context;
    private final String ACT_TITLE = "Edit Category";
    private Button chooseImageBtn,addCatBtn;
    private EditText cat_title_field;
    private ImageView cat_image_view;
    private Uri image_uri = null;
    private int PICK_IMAGE = 87;
    private ProgressBar progressBar;
    private TextView statusTextView;
    private String CAT_ID = null;
    private boolean isImageChanged = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initObjects();
    }

    private void initObjects() {
        context = this;
        CAT_ID = getIntent().getExtras().getString("cat_id");
        presenter = new EditCatPresenter(this,context);
       // getActionBar().setTitle("Add Category");
        getSupportActionBar().setTitle(ACT_TITLE);

        chooseImageBtn = findViewById(R.id.chooseProductImageBtn);
        addCatBtn = findViewById(R.id.addCatBtn);
        addCatBtn.setText("Update Category");
        cat_title_field = findViewById(R.id.product_name);
        cat_image_view = findViewById(R.id.product_img);
        progressBar = findViewById(R.id.progressBar);
        statusTextView = findViewById(R.id.statusTextView);

        addCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validateFieldsAndMakeRequest(CAT_ID,cat_title_field.getText().toString(),image_uri,isImageChanged);
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

        presenter.loadCategory(CAT_ID);
    }

    @Override
    public void onCategoryUpdated() {
        SC.toastHere(context,"Category Updated.");
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
            isImageChanged = true;
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

    @Override
    public void onCategoryLoaded(Category category) {
        cat_title_field.setText(category.getCAT_TITLE());
        Glib.loadImage(context,category.getCAT_IMAGE()).into(cat_image_view);
        cat_image_view.setVisibility(View.VISIBLE);

    }
}
