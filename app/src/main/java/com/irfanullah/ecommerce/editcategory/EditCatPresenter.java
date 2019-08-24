package com.irfanullah.ecommerce.editcategory;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Libraries.Util;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.category.CatLogic;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCatPresenter implements EditCatLogic.Presenter {

    private EditCatLogic.View view;
    private Context context;

    public EditCatPresenter(EditCatLogic.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void validateFieldsAndMakeRequest(String cat_id,String cat_title, Uri uri, boolean isImageChanged) {
        if(cat_title.isEmpty() || (uri == null && isImageChanged)){
            SC.toastHere(context,"None of the Fields can be empty.");
        }else if(!cat_title.isEmpty() && !isImageChanged){
            makeUpdateCategoryRequestWithOutImage(cat_id,cat_title);
        }
        else {
            if (Pref.isLoggedIn(this.context)){
               // SC.toastHere(context,uri.toString());
                makeUpdateCategoryRequest(cat_id,cat_title,uri);
            }else {
                SC.toastHere(context,"You are not logged in.");
            }
        }
    }

    @Override
    public Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void makeUpdateCategoryRequest(String cat_id,String cat_title, Uri uri) {
        String path = getRealPathFromURIPath(uri,context);
        SC.logHere(path);
        File file = new File(path);

        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"),Pref.getUser(context).getTOKEN());
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"),cat_title);
        RequestBody cat_idd = RequestBody.create(MediaType.parse("multipart/form-data"),cat_id);
        RequestBody img = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image",file.getName(),img);
        //show progress bar and status textview
        view.showProgress();

        RetroLib.getAPIServices().updateCate(tokenBody,cat_idd,title,image).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
             if(response.isSuccessful()){
                 Category cat = response.body();
                 SC.logHere(cat.getMessage());
                 if(cat.isError() || !cat.isAuthenticated()){
                     view.hideProgress();
                     view.onError(cat.getMessage());
                 }else if(cat.isSaved() && cat.isUploaded()){
                     view.hideProgress();
                     view.onCategoryUpdated();
                 }else {
                     view.hideProgress();
                     view.onError(cat.getMessage());
                 }
             }else {
                 view.hideProgress();
                 view.onError(response.raw().toString());
                 SC.logHere(response.raw().toString());
             }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                view.hideProgress();
                view.onException(t.getMessage());
            }
        });
    }

    private void showChoicDialog(){
        final String[] items = new String[]{"Capture Image", "Select from gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Action");
        builder.setPositiveButton(items[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton(items[1], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    public String getRealPathFromURIPath(Uri contentURI, Context activity) {
        String path = null;
        if (Build.VERSION.SDK_INT < 11)
            path = Util.getRealPathFromURI_BelowAPI11(context, contentURI);

            // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19)
            path = Util.getRealPathFromURI_API11to18(context, contentURI);

            // SDK > 19 (Android 4.4)
        else
            path = Util.getRealPathFromURI_API19(context, contentURI);
            return path;
      //  File file = new File(path);
    }

    public void loadCategory(String cat_id){
        RetroLib.getAPIServices().getCategory(Pref.getUser(context).getTOKEN(),cat_id).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()){
                    Category cat = response.body();
                   // SC.logHere(cat.getMessage());
                    if(cat.isError() || !cat.isAuthenticated()){
                        view.hideProgress();
                        view.onError(cat.getMessage());
                    }else if(cat.isFound()){
                        view.hideProgress();
                        view.onCategoryLoaded(cat.getCATEGORY());
                    }else {
                        view.hideProgress();
                        view.onError(cat.getMessage());
                    }
                }else {
                    view.hideProgress();
                    view.onError(response.raw().toString());
                    SC.logHere(response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                view.onException(t.getMessage());

            }
        });
    }

    @Override
    public void makeUpdateCategoryRequestWithOutImage(String cat_id, String cat_title) {

        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"),Pref.getUser(context).getTOKEN());
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"),cat_title);
        RequestBody cat_idd = RequestBody.create(MediaType.parse("multipart/form-data"),cat_id);
//        RequestBody img = RequestBody.create(MediaType.parse("multipart/form-data"),file);
//        MultipartBody.Part image = MultipartBody.Part.createFormData("image",file.getName(),img);
        //show progress bar and status textview
        view.showProgress();

        RetroLib.getAPIServices().updateCate(tokenBody,cat_idd,title,null).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()){
                    Category cat = response.body();
                    SC.logHere(cat.getMessage());
                    if(cat.isError() || !cat.isAuthenticated()){
                        view.hideProgress();
                        view.onError(cat.getMessage());
                    }else if(cat.isSaved() && cat.isUploaded()){
                        view.hideProgress();
                        view.onCategoryUpdated();
                    }else {
                        view.hideProgress();
                        view.onError(cat.getMessage());
                    }
                }else {
                    view.hideProgress();
                    view.onError(response.raw().toString());
                    SC.logHere(response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                view.hideProgress();
                view.onException(t.getMessage());
            }
        });
    }
}
