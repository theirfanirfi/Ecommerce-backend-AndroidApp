package com.irfanullah.ecommerce.main;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.NotificationServices.NotificationService;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.service.ServicesActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Pref.getSharedPreferenceEditor(context).remove(Pref.PREF_USER_DETAILS).commit();
        view.gotoLoginActivit();

//        if(){
//        }else {
//            view.showToast("Unable to Logout. Try again.");
//        }
    }

    @Override
    public void setUpTabsTitle(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setText("Notifications");
        tabLayout.getTabAt(1).setText("Gallery");
        tabLayout.getTabAt(2).setText("Appointments");
        tabLayout.getTabAt(3).setText("Chat");
        tabLayout.getTabAt(4).setText("Members");

        getCountForChatAndNotificationTabs(tabLayout);
    }

    private void gotoServiceAcitivity(){
        Intent servicesAct = new Intent(context, ServicesActivity.class);
        context.startActivity(servicesAct);
    }

    public void getCountForChatAndNotificationTabs(final TabLayout tabLayout) {
        if (Pref.isLoggedIn(context)) {
            RetroLib.getAPIServices().getCountForChatAndNotificationsTab(Pref.getUser(context).getTOKEN()).enqueue(new Callback<Appointment>() {
                @Override
                public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                    if (response.isSuccessful()) {
                        Appointment appointment = response.body();
                        if (appointment.isError()) {

                        } else {
                            if (appointment.getCHAT_COUNT() > 0) {
                                tabLayout.getTabAt(3).setText("Chat (" + Integer.toString(appointment.getCHAT_COUNT()) + ")");
                            }

                            if (appointment.getAPPOINTMENTS_COUNT() > 0) {
                                tabLayout.getTabAt(0).setText("Notifications (" + Integer.toString(appointment.getAPPOINTMENTS_COUNT()) + ")");
                            }
                        }
                    } else {
                        SC.toastHere(context, "Response error");
                    }
                }

                @Override
                public void onFailure(Call<Appointment> call, Throwable t) {

                }
            });
        }
    }

}
