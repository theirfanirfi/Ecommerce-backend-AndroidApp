package com.irfanullah.ecommerce.main.Fragments.Categories;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.main.Adapters.CategoriesAdapter;

import java.util.ArrayList;

public interface CategoriesLogic {

    interface View {
        void onCategoriesLoaded(ArrayList<Category> categories);
        void gotoAddCategoryActivity();
        void hideProgressBar();
        void showProgressBar();
        void onError(String message);
        void onCategoryDeleted(int position, Category category);
    }

    interface Presenter {
        void makeCategoriesRequest();
        void fabButton();
        CategoriesAdapter setUpRecyclerView(RecyclerView rv);
    }
}
