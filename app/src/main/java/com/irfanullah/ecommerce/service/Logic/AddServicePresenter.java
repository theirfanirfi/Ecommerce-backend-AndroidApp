package com.irfanullah.ecommerce.service.Logic;

import android.content.Context;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Service;
import com.irfanullah.ecommerce.Storage.Pref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServicePresenter implements AddServiceLogic.Presenter {
    private Context context;
    private AddServiceLogic.View view;

    public AddServicePresenter(Context context, AddServiceLogic.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void addService(String sName, String sCost) {

        if(sName.isEmpty() || sCost.isEmpty()){
            view.showMessage("None of the fields can be empty.");
        }else if(!Pref.isLoggedIn(context)){
            view.showMessage("You must be logged in to perform this action.");

        }else {
           RetroLib.getAPIServices().addservice(Pref.getUser(context).getTOKEN(),sName,sCost).enqueue(new Callback<Service>() {
               @Override
               public void onResponse(Call<Service> call, Response<Service> response) {
                   if(response.isSuccessful()){
                       Service service = response.body();
                       if(service.isError()){
                           view.showMessage(service.getMESSAGE());
                       }else if(service.isSaved()){
                           view.showMessage(service.getMESSAGE());
                           view.onServiceAdd();
                       }else {
                           view.showMessage(service.getMESSAGE());
                       }
                   }else {
                       view.showMessage("Response error");
                   }
               }

               @Override
               public void onFailure(Call<Service> call, Throwable t) {
                   view.showMessage(t.getMessage());
               }
           });
        }
    }
}
