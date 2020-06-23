package com.irfanullah.ecommerce.main.Fragments.Gallery;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.Models.Gallery;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.main.Adapters.CategoriesAdapter;
import com.irfanullah.ecommerce.main.Adapters.GalleryAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryPresenter implements GalleryLogic.Presenter {
    private GalleryLogic.View view;
    private Context context;
    ArrayList<Gallery> gallery;

    public GalleryPresenter(GalleryLogic.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getGalleryRequest() {
        if (Pref.isLoggedIn(context)) {
            view.showProgressBar();
            RetroLib.getAPIServices().getGallery(Pref.getUser(context).getTOKEN()).enqueue(new Callback<Gallery>() {
                @Override
                public void onResponse(Call<Gallery> call, Response<Gallery> response) {
                    if (response.isSuccessful()) {
                        Gallery gallery = response.body();
                        if (gallery.isError() || !gallery.isAuthenticated()) {
                            view.onError("Either you are not logged in, or some kind of error occurred. Try again");
                        } else if (gallery.isFound()) {
                            view.hideProgressBar();
                            view.onGalleryLoaded(gallery.getGALLERY());
                        } else {
                            view.onError("Error occurred, please try again");
                        }
                    } else {
                        view.hideProgressBar();
                        view.onError(response.message());
                    }
                }

                @Override
                public void onFailure(Call<Gallery> call, Throwable t) {
                    view.hideProgressBar();
                    view.onError(t.getMessage());
                }
            });
        } else {
            view.hideProgressBar();
            view.onError("You are not logged in.");
        }
    }

    @Override
    public void fabButton() {
        view.gotoUploadImageActivity();
    }

    @Override
    public GalleryAdapter setUpRecyclerView(RecyclerView rv) {

        gallery = new ArrayList<>();
        GalleryAdapter galleryAdapter = new GalleryAdapter(context, gallery);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(galleryAdapter);
        return galleryAdapter;
    }

    @Override
    public Boolean deleteGalleryPicture(String gallery_id) {
        if (Pref.isLoggedIn(context)) {
            view.showProgressBar();
            RetroLib.getAPIServices().deleteGalleryPic(Pref.getUser(context).getTOKEN(), gallery_id).enqueue(new Callback<Gallery>() {
                @Override
                public void onResponse(Call<Gallery> call, Response<Gallery> response) {
                    if (response.isSuccessful()) {
                        Gallery gallery = response.body();
                        if (gallery.isError() || !gallery.isAuthenticated()) {
                            view.onError("Either you are not logged in, or some kind of error occurred. Try again");
                        } else if (gallery.isDeleted()) {
                            view.hideProgressBar();
                            view.onGalleryImageDeleted("Image deleted.");
                        } else {
                            view.onError(gallery.getMessage());
                        }
                    } else {
                        view.hideProgressBar();
                        view.onError(response.message());
                    }
                }

                @Override
                public void onFailure(Call<Gallery> call, Throwable t) {
                    view.hideProgressBar();
                    view.onError(t.getMessage());
                }
            });
            return null;
        }else
        {
            view.hideProgressBar();
            view.onError("You are not logged in.");
            return null;
        }

}
}