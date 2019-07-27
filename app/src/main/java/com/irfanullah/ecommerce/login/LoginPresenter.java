package com.irfanullah.ecommerce.login;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.Storage.Pref;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginLogic.Presenter {
    private LoginLogic.View view;
    private Context context;

    public LoginPresenter(LoginLogic.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void validateFieldsAndMakeLoginRequest(String email, String password) {
        if(email.isEmpty() || password.isEmpty()){
            view.showEmptyErrorMessage();
        }else {
            makeLoginRequest(email,password);
        }
    }

    @Override
    public void makeLoginRequest(String email, String password) {
        RetroLib.getAPIServices().loginUser(email,password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    if(user.isInvalidCreds()){
                        view.invalidCredentials();
                    }else if(user.isEmpty()){
                        view.showEmptyErrorMessage();
                    }else if(user.isLoggedIn()){

                        saveUser(user.getUser());
                    }
                }else {
                    view.onFailure(response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.onException(t.getMessage());
            }
        });
    }

    @Override
    public void saveUser(User user) {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(user);
       if( Pref.getSharedPreferenceEditor(context).putString(Pref.PREF_USER_DETAILS,jsonObject.toString()).commit()){
           view.gotoMainActivity();
       }else {
           view.onUserSaveError();
       }
    }

    @Override
    public void checkWhetherLoggedInOrNot() {
        if(Pref.getSharedPreference(context).contains(Pref.PREF_USER_DETAILS)) {
            view.gotoMainActivity();
        }
    }
}
