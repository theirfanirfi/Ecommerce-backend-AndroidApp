package com.irfanullah.ecommerce.main;

import android.support.design.widget.TabLayout;

public interface MainActivityLogic {

    interface View {
        void gotoProfileActivity();
        void gotoLoginActivit();
        void showToast(String message);
    }

    interface Presenter {
        void onMenuItemSelected(int id);
        void logout();
        void setUpTabsTitle(TabLayout tabLayout);

    }
}
