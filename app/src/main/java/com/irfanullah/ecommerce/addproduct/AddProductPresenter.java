package com.irfanullah.ecommerce.addproduct;

import android.content.Context;
import android.net.Uri;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.Storage.Pref;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductPresenter implements AddProductLogic.Preseneter {
    private Context context;
    private AddProductLogic.View view;

    public AddProductPresenter(Context context, AddProductLogic.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void validateInputFieldsAndMakeProductAddRequest(String name,String price, String quantity, String cat_id, Uri image_uri, String desc) {
        if(name.isEmpty() || quantity.isEmpty() || cat_id.isEmpty() || image_uri == null || price.isEmpty() || desc.isEmpty()){
            view.onError("None of the fields can be empty.");
        }else {
            if(Pref.isLoggedIn(context)){
                makeAddProductRequest(name,price,quantity,cat_id,image_uri,desc);
            }else {
                view.onError("You are not logged in.");
            }
        }
    }

    private void makeAddProductRequest(String name, String price, String quantity, String cat_id, Uri image_uri,String desc){
        String path = SC.getRealPathFromURIPath(image_uri,context);
        File file = new File(path);

        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"),Pref.getUser(context).getTOKEN());
        RequestBody pname = RequestBody.create(MediaType.parse("multipart/form-data"),name);
        RequestBody pdesc = RequestBody.create(MediaType.parse("multipart/form-data"),desc);
        RequestBody pquan = RequestBody.create(MediaType.parse("multipart/form-data"),quantity);
        RequestBody pprice = RequestBody.create(MediaType.parse("multipart/form-data"),price);
        RequestBody pcat_id = RequestBody.create(MediaType.parse("multipart/form-data"),cat_id);
        RequestBody img = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image",file.getName(),img);
        view.showProgress();
        RetroLib.getAPIServices().addProduct(tokenBody,pname,pquan,pprice,pcat_id,image,pdesc).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    if(product.isError() || !product.isAuthenticated()){
                        view.hideProgress();
                        view.onError(product.getMessage());
                    }else if(product.isSaved()){
                        view.hideProgress();
                        view.onProductAdded();
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
