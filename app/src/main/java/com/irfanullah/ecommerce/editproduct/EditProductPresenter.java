package com.irfanullah.ecommerce.editproduct;

import android.content.Context;
import android.net.Uri;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.addproduct.AddProductLogic;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductPresenter implements EditProductLogic.Preseneter {
    private Context context;
    private EditProductLogic.View view;
    private String PRODUCT_ID;
    public EditProductPresenter(Context context, EditProductLogic.View view,String pid) {
        this.context = context;
        this.view = view;
        this.PRODUCT_ID = pid;
    }

    @Override
    public void validateInputFieldsAndMakeProductAddRequest(String name,String price, String quantity, String cat_id, Uri image_uri,boolean hasImage) {
        if(name.isEmpty() || quantity.isEmpty() || cat_id.isEmpty() || price.isEmpty()){
            view.onError("None of the fields can be empty.");
        }else {
            if(Pref.isLoggedIn(context)){
                if(hasImage) {
                    makeUpdateProductRequest(name, price, quantity, cat_id, image_uri, PRODUCT_ID);
                }else {
                    makeUpdateProductRequestWithOutImage(name,price,quantity,cat_id,PRODUCT_ID);
                }
            }else {
                view.onError("You are not logged in.");
            }
        }
    }

    @Override
    public void loadProductData() {
        RetroLib.getAPIServices().getProduct(Pref.getUser(context).getTOKEN(),PRODUCT_ID).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    if(!product.isAuthenticated() || product.isError()){
                        view.hideProgress();
                        view.onError(product.getMessage());
                    }else if(product.isFound()){
                        view.hideProgress();
                        view.onProudctLoaded(product.getPRODUCT());
                    }else {
                        view.hideProgress();
                        view.onError(product.getMessage());
                    }
                }else {
                    view.hideProgress();
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.hideProgress();
                view.onError(t.getMessage());
            }
        });
    }

    private void makeUpdateProductRequest(String name, String price, String quantity, String cat_id, Uri image_uri,String pid){
        String path = SC.getRealPathFromURIPath(image_uri,context);
        File file = new File(path);

        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"),Pref.getUser(context).getTOKEN());
        RequestBody pname = RequestBody.create(MediaType.parse("multipart/form-data"),name);
        RequestBody product_id = RequestBody.create(MediaType.parse("multipart/form-data"),pid);
        RequestBody pquan = RequestBody.create(MediaType.parse("multipart/form-data"),quantity);
        RequestBody pprice = RequestBody.create(MediaType.parse("multipart/form-data"),price);
        RequestBody pcat_id = RequestBody.create(MediaType.parse("multipart/form-data"),cat_id);
        RequestBody img = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image",file.getName(),img);
        view.showProgress();
        RetroLib.getAPIServices().updateProduct(tokenBody,pname,pquan,pprice,product_id,pcat_id,image).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    if(product.isError() || !product.isAuthenticated()){
                        view.hideProgress();
                        view.onError(product.getMessage());
                    }else if(product.isSaved()){
                        view.hideProgress();
                        view.onProductUpdated();
                    }
                }else {
                    view.onError(response.message());
                    view.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.hideProgress();
                view.onError(t.getMessage());
            }
        });
    }


    private void makeUpdateProductRequestWithOutImage(String name, String price, String quantity, String cat_id,String pid){

        view.showProgress();
        RetroLib.getAPIServices().updateProductWithOutImage(Pref.getUser(context).getTOKEN(),name,quantity,price,pid,cat_id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    if(product.isError() || !product.isAuthenticated()){
                        view.hideProgress();
                        view.onError(product.getMessage());
                    }else if(product.isSaved()){
                        view.hideProgress();
                        view.onProductUpdated();
                    }
                }else {
                    view.onError(response.message());
                    view.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.hideProgress();
                view.onError(t.getMessage());
            }
        });
    }
}
