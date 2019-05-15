package com.irfanullah.ecommerce.APIs;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.Models.Order;
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

    @GET(BASE_URL+"auth/getproducts")
    Call<Product> getProducts(@Query("token") String token);

    @GET(BASE_URL+"auth/getproduct")
    Call<Product> getProduct(@Query("token") String token, @Query("product_id") String product_id);

    @GET(BASE_URL+"auth/deleteproduct")
    Call<Product> deleteProduct(@Query("token") String token, @Query("product_id") String product_id);

    @GET(BASE_URL+"auth/getneworders")
    Call<Order> getNewOrders(@Query("token") String token);

    @GET(BASE_URL+"auth/getoldorders")
    Call<Order> getOldOrders(@Query("token") String token);

    @GET(BASE_URL+"auth/getcheckout")
    Call<Order> getCheckOutOrder(@Query("token") String token,@Query("checkout_id") String checkout_id);

    @GET(BASE_URL+"auth/getorderproducts")
    Call<Order> getOrderProducts(@Query("token") String token,@Query("checkout_id") String checkout_id);

    @GET(BASE_URL+"auth/shiporder")
    Call<Order> shipOrder(@Query("token") String token,@Query("checkout_id") String checkout_id);

    @POST(BASE_URL+"auth/addproduct")
    @Multipart
    Call<Product> addProduct(@Part("token") RequestBody token, @Part("product_name") RequestBody product_name, @Part("product_quantity") RequestBody quantity,
                            @Part("product_price") RequestBody price, @Part("cat_id") RequestBody cat_id, @Part MultipartBody.Part product_image
                             );

    @POST(BASE_URL+"auth/updateproduct")
    @Multipart
    Call<Product> updateProduct(@Part("token") RequestBody token, @Part("product_name") RequestBody product_name, @Part("product_quantity") RequestBody quantity,
                             @Part("product_price") RequestBody price,@Part("product_id") RequestBody product_id, @Part("cat_id") RequestBody cat_id, @Part MultipartBody.Part product_image
    );

    @POST(BASE_URL+"auth/updateproduct")
    @FormUrlEncoded
    Call<Product> updateProductWithOutImage(@Field("token") String token, @Field("product_name") String product_name, @Field("product_quantity") String quantity,
                                @Field("product_price") String price,@Field("product_id") String product_id, @Field("cat_id") String cat_id);
}
