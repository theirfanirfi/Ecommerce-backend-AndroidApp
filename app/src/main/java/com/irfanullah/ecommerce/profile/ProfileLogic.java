package com.irfanullah.ecommerce.profile;

import com.irfanullah.ecommerce.Models.User;

public interface ProfileLogic {
    interface View {
        void onError(String message);
        void onProfileLoaded(User user);
        void showProgress();
        void hideProgress();
        void onUpdated();
    }

    interface Presenter {
        void loadProfile();
        void validaeFields(String name,String email,String cpass, String npass,String openingTime,String closingTime,
                           String service_time);
    }
}
