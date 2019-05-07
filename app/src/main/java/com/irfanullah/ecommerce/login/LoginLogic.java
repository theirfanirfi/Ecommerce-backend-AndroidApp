package com.irfanullah.ecommerce.login;

import android.content.Context;

import com.irfanullah.ecommerce.Models.User;

public interface LoginLogic {
    interface View {

        void showEmptyErrorMessage();
        void invalidCredentials();
        void onSuccess();
        void onFailure(String message);
        void onException(String message);
        void gotoMainActivity();
        void onUserSaveError();
    }

    interface Presenter {
        void validateFieldsAndMakeLoginRequest(String email, String password);
        void makeLoginRequest(String email, String password);
        void saveUser(User user);
        void checkWhetherLoggedInOrNot();
    }
}
