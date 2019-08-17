package com.irfanullah.ecommerce.service.Logic;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Models.Service;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.service.ServiceActionDialog;
import com.irfanullah.ecommerce.service.ServiceAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicePresenter implements ServiceLogic.Presenter, ServiceAdapter.ServiceClickListener{

    private ServiceLogic.View view;
    private Context context;
    private ServiceAdapter serviceAdapter;
    private ArrayList<Service> services;


    public ServicePresenter(ServiceLogic.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public ServiceAdapter setUpRecyclerView(RecyclerView rv) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        services = new ArrayList<>();
        serviceAdapter = new ServiceAdapter(context,services);
        serviceAdapter.setOnServiceClickListener(this);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(serviceAdapter);
        return serviceAdapter;
    }

    @Override
    public void fetchServices() {
        RetroLib.getAPIServices().getservices(Pref.getUser(context).getTOKEN()).enqueue(new Callback<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {
                if(response.isSuccessful()){
                    Service service = response.body();
                    if(service.isFound()){
                        view.onServicesLoad(service.getServices());
                    }else {
                        view.showMessage(service.getMESSAGE());
                    }
                }else {
                    view.showMessage("response error");
                }
            }

            @Override
            public void onFailure(Call<Service> call, Throwable t) {
                view.showMessage(t.getMessage());
            }
        });

    }

    @Override
    public void onServiceClickListener(int position, Service service) {
        view.showDialog(service);
    }
}
