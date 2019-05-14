package com.irfanullah.ecommerce.main.Fragments.Categories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
        rv = view.findViewById(R.id.newOrdersRv);
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
        Intent addCatIntent = new Intent(context, AddCategoryActivity.class);
        startActivity(addCatIntent);
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
}
