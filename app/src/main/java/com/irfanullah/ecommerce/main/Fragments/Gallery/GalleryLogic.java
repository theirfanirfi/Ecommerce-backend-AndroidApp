package com.irfanullah.ecommerce.main.Fragments.Gallery;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.Models.Gallery;
import com.irfanullah.ecommerce.main.Adapters.CategoriesAdapter;
import com.irfanullah.ecommerce.main.Adapters.GalleryAdapter;

import java.util.ArrayList;

public interface GalleryLogic {

    interface View {
        void onGalleryLoaded(ArrayList<Gallery> gallery);
        void gotoUploadImageActivity();
        void hideProgressBar();
        void showProgressBar();
        void onError(String message);
        void onGalleryImageDeleted(String message);
    }

    interface Presenter {
        void getGalleryRequest();
        void fabButton();
        GalleryAdapter setUpRecyclerView(RecyclerView rv);
        Boolean deleteGalleryPicture(String gallery_id);
    }
}
