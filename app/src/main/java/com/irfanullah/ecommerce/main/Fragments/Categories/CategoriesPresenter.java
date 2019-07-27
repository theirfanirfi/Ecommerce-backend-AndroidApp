package com.irfanullah.ecommerce.main.Fragments.Categories;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.main.Adapters.CategoriesAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesPresenter implements CategoriesLogic.Presenter {
    private CategoriesLogic.View view;
    private Context context;
    ArrayList<Category> categories;
    public CategoriesPresenter(CategoriesLogic.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void makeCategoriesRequest() {
        if(Pref.isLoggedIn(context)){
            view.showProgressBar();
            RetroLib.getAPIServices().loadCategories(Pref.getUser(context).getTOKEN()).enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    if(response.isSuccessful()){
                        Category cat = response.body();
                        if(cat.isError() || !cat.isAuthenticated()){
                            view.onError("Either you are not logged in, or some kind of error occurred. Try again");
                        }else if(cat.isFound()){
                            view.hideProgressBar();
                            view.onCategoriesLoaded( cat.getCATEGORIES());
                        }else {
                            view.onError("Error occurred, please try again");
                        }
                    }else {
                        view.hideProgressBar();
                        view.onError(response.message());
                    }
                }

                @Override
                public void onFailure(Call<Category> call, Throwable t) {
                    view.hideProgressBar();
                    view.onError(t.getMessage());
                }
            });
        }else {
            view.hideProgressBar();
            view.onError("You are not logged in.");
        }
    }

    @Override
    public void fabButton() {
        view.gotoAddCategoryActivity();
    }

    @Override
    public CategoriesAdapter setUpRecyclerView(RecyclerView rv) {

        categories = new ArrayList<>();
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(context,categories);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(categoriesAdapter);
        return categoriesAdapter;
    }
}
