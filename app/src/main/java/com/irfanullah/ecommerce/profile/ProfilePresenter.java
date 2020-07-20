package com.irfanullah.ecommerce.profile;

import android.content.Context;
import android.util.Log;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.Storage.Pref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileLogic.Presenter {
    private ProfileLogic.View view;
    private Context context;

    public ProfilePresenter(ProfileLogic.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void loadProfile() {
        RetroLib.getAPIServices().getProfile(Pref.getUser(context).getTOKEN()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();

                    if(user.isError()){
                        view.onError(user.getMESSAGE());
                    }else if(!user.isAuthenticated()){
                        view.onError("You are not logged in to perform this action.");
                    }else if(user.isFound()){
                        view.onProfileLoaded(user.getUser());
                    }else {
                        view.onError(user.getMESSAGE());
                    }
                }else {
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.onError(t.getMessage());
            }
        });
    }

    @Override
    public void validaeFields(String name,String email,String cpass, String npass,String openingTime, String closingTime,
                              String service_time) {

        int isPassword = 0;

        if(!npass.isEmpty() || !cpass.isEmpty()){
            isPassword = 1;
        }

        if(name.isEmpty() || email.isEmpty() || openingTime.isEmpty()|| closingTime.isEmpty()
                || service_time.isEmpty()){
            view.onError("Name, email, service time and time fields are mandatory, these fields cannot be empty.");
        }else if(isPassword == 1 && (npass.isEmpty() || cpass.isEmpty())){
            view.onError("If you want to update password, then the password fields cannot be empty.");
        }else if(isPassword == 1 && npass.length() < 6){
            view.onError("Password length must be at least six characters long.");
        }else {
            updateProfileRequest( name, email, cpass,  npass, openingTime, closingTime,
                     service_time,isPassword);
        }
    }

    private void updateProfileRequest(String name,String email,String cpass, String npass,String openingTime,String closingTime,
                                      String service_time,int isPassword){


        RetroLib.getAPIServices().updateProfile(Pref.getUser(context).getTOKEN(),name,email,isPassword,cpass,npass,openingTime, closingTime,
                service_time).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    if(user.isError()){
                        view.onError(user.getMESSAGE());
                    }else if(!user.isAuthenticated()){
                        view.onError("You are not logged in to perform this action.");
                    }else if(user.isUpdated()){
                        view.onProfileLoaded(user.getUser());
                        view.onUpdated();
                    }else {
                        view.onError(user.getMESSAGE());
                    }
                }else {
                    view.onError(response.message().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.onError(t.toString());
            }
        });
    }
}
