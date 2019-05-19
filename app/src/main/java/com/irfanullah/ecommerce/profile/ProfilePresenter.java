package com.irfanullah.ecommerce.profile;

import android.content.Context;

import com.irfanullah.ecommerce.Libraries.RetroLib;
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

            }
        });
    }

    @Override
    public void validaeFields() {

    }
}
