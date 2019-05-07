package com.irfanullah.ecommerce.APIs;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.Models.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIs {
     final static String BASE_URL = RetroLib.BASE_URL;

    @POST(BASE_URL+"nigol")
    @FormUrlEncoded
    Call<User> loginUser(@Field("email") String email,@Field("password") String password);

    @Multipart
    @POST(BASE_URL+"auth/addcat")
    Call<Category> addcat(@Part("token") RequestBody token, @Part("cat_title") RequestBody cat_title, @Part MultipartBody.Part cat_image);

    @GET(BASE_URL+"auth/getCategories")
    Call<Category> loadCategories(@Query("token") String token);

    @POST(BASE_URL+"auth/addproduct")
    @Multipart
    Call<Product> addProduct(@Part("token") RequestBody token, @Part("product_name") RequestBody product_name, @Part("product_quantity") RequestBody quantity,
                             @Part("cat_id") RequestBody cat_id, @Part MultipartBody.Part product_image
                             );
}
