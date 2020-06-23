package com.irfanullah.ecommerce.APIs;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.Models.Gallery;
import com.irfanullah.ecommerce.Models.Messenger;
import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.Models.Participants;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.Models.Service;
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
    Call<Product> getProducts(@Query("token") String token, @Query("cat_id") String cat_id);

    @GET(BASE_URL+"auth/getmemcheckouts")
    Call<Order> getMemberCheckouts(@Query("token") String token, @Query("mem_id") String mem_id);


    @GET(BASE_URL+"auth/getmem")
    Call<User> getMembers(@Query("token") String token);

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

    @GET(BASE_URL+"auth/getprofile")
    Call<User> getProfile(@Query("token") String token);

    @POST(BASE_URL+"auth/addproduct")
    @Multipart
    Call<Product> addProduct(@Part("token") RequestBody token, @Part("product_name") RequestBody product_name, @Part("product_quantity") RequestBody quantity,
                            @Part("product_price") RequestBody price, @Part("cat_id") RequestBody cat_id, @Part MultipartBody.Part product_image
                             ,@Part("desc") RequestBody product_desc);


    @POST(BASE_URL+"auth/updateproduct")
    @Multipart
    Call<Product> updateProduct(@Part("token") RequestBody token, @Part("product_name") RequestBody product_name, @Part("product_quantity") RequestBody quantity,
                             @Part("product_price") RequestBody price,@Part("product_id") RequestBody product_id, @Part("cat_id") RequestBody cat_id, @Part MultipartBody.Part product_image
    ,@Part("desc") RequestBody product_description);

    @POST(BASE_URL+"auth/updateproduct")
    @FormUrlEncoded
    Call<Product> updateProductWithOutImage(@Field("token") String token, @Field("product_name") String product_name, @Field("product_quantity") String quantity,
                                @Field("product_price") String price,@Field("product_id") String product_id, @Field("cat_id") String cat_id,@Field("desc") String product_description);

    @POST(BASE_URL+"auth/updateprofile")
    @FormUrlEncoded
    Call<User> updateProfile(@Field("token") String token, @Field("name") String name,
                             @Field("email") String email,@Field("uc") int isPassword,
                             @Field("cpass") String cpass, @Field("npass") String npass,
                             @Field("openingtime") String openingtime,@Field("closingtime") String closingtime,
                             @Field("service_time") String service_time);



    //Gallery

    @POST(BASE_URL+"auth/uploadimage")
    @Multipart
    Call<Gallery> addGalleryImage(@Part("token") RequestBody token, @Part("image_title") RequestBody image_title, @Part MultipartBody.Part product_image);

    @GET(BASE_URL+"auth/getgallery")
    Call<Gallery> getGallery(@Query("token") String token);

    @GET(BASE_URL+"auth/deletegallery")
    Call<Gallery> deleteGalleryPic(@Query("token") String token, @Query("id") String id);

    //appointments
    @GET(BASE_URL+"auth/getmonthappointments")
    Call<Appointment> getMonthApts(@Query("token") String token,@Query("month") String month,@Query("year") String year);
    @GET(BASE_URL+"auth/getdayappointments")
    Call<Appointment> getDayApts(@Query("token") String token,@Query("month") String month,@Query("year") String year,@Query("day") String day);

    @GET(BASE_URL+"auth/getuserappointments")
    Call<Appointment> getUserAppointments(@Query("token") String token,@Query("id") String user_id);


    //services
    @GET(BASE_URL+"auth/getservices")
    Call<Service> getservices(@Query("token") String token);
    @GET(BASE_URL+"auth/addservice")
    Call<Service> addservice(@Query("token") String token,@Query("service_name") String service_name,
                             @Query("service_cost") String service_cost);

    @GET(BASE_URL+"auth/updateservice")
    Call<Service> updateService(@Query("token") String token,@Query("service_name") String service_name,
                             @Query("service_cost") String service_cost,@Query("service_id") String service_id);
    @GET(BASE_URL+"auth/deleteservice")
    Call<Service> deleteService(@Query("token") String token,@Query("service_id") String service_id);


    //chat
    @GET(BASE_URL+"auth/getparticipants")
    Call<Participants> getParticipants(@Query("token") String token);

    @GET(BASE_URL+"auth/getmessages")
    Call<Messenger> getMessages(@Query("token") String token, @Query("pid") String pid);

    @GET(BASE_URL+"auth/sendmessage")
    Call<Messenger> sendMessage(@Query("token") String token, @Query("id") String to_chat_with_id,@Query("msg") String msg);

    //notifications + appointments
    @GET(BASE_URL+"auth/notifications")
    Call<Appointment> getAppointmentsNotifications(@Query("token") String token);

    @GET(BASE_URL+"auth/confirmapt")
    Call<Appointment> confirmAppointment(@Query("token") String token,@Query("id") String id);

    @GET(BASE_URL+"auth/declineapt")
    Call<Appointment> declineAppointment(@Query("token") String token,@Query("id") String id);

    @GET(BASE_URL+"auth/getcounts")
    Call<Appointment> getCountForChatAndNotificationsTab(@Query("token") String token);


}
