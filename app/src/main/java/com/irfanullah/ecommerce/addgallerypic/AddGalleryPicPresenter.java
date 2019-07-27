package com.irfanullah.ecommerce.addgallerypic;

import android.content.Context;
import android.net.Uri;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Gallery;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.Storage.Pref;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGalleryPicPresenter implements AddGalleryPicLogic.Preseneter {
    private Context context;
    private AddGalleryPicLogic.View view;

    public AddGalleryPicPresenter(Context context, AddGalleryPicLogic.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void validateInputFieldsAndMakeImageAddRequest(String name,Uri image_uri) {
        if(name.isEmpty() ||  image_uri == null){
            view.onError("None of the fields can be empty.");
        }else {
            if(Pref.isLoggedIn(context)){
                makeAddProductRequest(name,image_uri);
            }else {
                view.onError("You are not logged in.");
            }
        }
    }

    private void makeAddProductRequest(String name,Uri image_uri){
        String path = SC.getRealPathFromURIPath(image_uri,context);
        File file = new File(path);

        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"),Pref.getUser(context).getTOKEN());
        RequestBody pname = RequestBody.create(MediaType.parse("multipart/form-data"),name);

        RequestBody img = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image",file.getName(),img);
        view.showProgress();
        RetroLib.getAPIServices().addGalleryImage(tokenBody,pname,image).enqueue(new Callback<Gallery>() {
            @Override
            public void onResponse(Call<Gallery> call, Response<Gallery> response) {
                if(response.isSuccessful()){
                    Gallery gallery = response.body();
                    if(gallery.isError() || !gallery.isAuthenticated()){
                        view.hideProgress();
                        view.onError(gallery.getMessage());
                    }else if(gallery.isSaved()){
                        view.hideProgress();
                        view.onImageAdded();
                    }
                }else {
                    view.onError(response.message());
                    view.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<Gallery> call, Throwable t) {
                view.hideProgress();
                view.onError(t.getMessage());
            }
        });
    }
}
