package com.irfanullah.ecommerce.main;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;

import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.service.ServicesActivity;

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
            case R.id.services:
                gotoServiceAcitivity();
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
        tabLayout.getTabAt(0).setText("Gallery");
        tabLayout.getTabAt(1).setText("Appointments");
        tabLayout.getTabAt(2).setText("Chat");
        tabLayout.getTabAt(3).setText("Members");
    }

    private void gotoServiceAcitivity(){
        Intent servicesAct = new Intent(context, ServicesActivity.class);
        context.startActivity(servicesAct);
    }

}
