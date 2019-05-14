package com.irfanullah.ecommerce.main;

import android.content.Context;
import android.support.design.widget.TabLayout;

import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;

public class MainActPresenter implements MainActivityLogic.Presenter {

    private MainActivityLogic.View view;
    private Context context;

    public MainActPresenter(MainActivityLogic.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onMenuItemSelected(int id) {
        switch (id){
            case R.id.profile:
                view.gotoProfileActivity();
                break;
            case R.id.logout:
                logout();
                break;
        }
    }

    @Override
    public void logout() {
        if(Pref.getSharedPreferenceEditor(context).remove(Pref.PREF_USER_DETAILS).commit()){
            view.gotoLoginActivit();
        }else {
            view.showToast("Unable to Logout. Try again.");
        }
    }

    @Override
    public void setUpTabsTitle(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setText("Categories");
        tabLayout.getTabAt(1).setText("New Orders");
        tabLayout.getTabAt(2).setText("Processed Orders");
    }

}
