package com.irfanullah.ecommerce.service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Service;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceActionDialog extends DialogFragment {

    private EditText name,cost;
    private Button update,delete;
    private String SERVICE_ID = "";
    private Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         super.onCreateView(inflater, container, savedInstanceState);
         context = getContext();
         Bundle bundle = getArguments();

         View view = LayoutInflater.from(getContext()).inflate(R.layout.update_service,container,false);
         SERVICE_ID = bundle.getString("service_id");

        name = view.findViewById(R.id.service_name);
        cost = view.findViewById(R.id.service_cost);

        update = view.findViewById(R.id.addServiceBtn);
        delete = view.findViewById(R.id.deleteServiceBtn);

        name.setText(bundle.getString("service_name"));
        cost.setText(bundle.getString("service_cost"));



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().isEmpty() || cost.getText().toString().isEmpty()){
                    SC.toastHere(getContext(),"None of the fields can be empty.");
                }else {

                    RetroLib.getAPIServices().updateService(Pref.getUser(getContext()).getTOKEN(), name.getText().toString(), cost.getText().toString(), SERVICE_ID).enqueue(new Callback<Service>() {
                        @Override
                        public void onResponse(Call<Service> call, Response<Service> response) {
                            if(response.isSuccessful()){
                                Service service = response.body();
                                if(service.isSaved()){
                                    getDialog().dismiss();
                                    getDialog().hide();
                                    SC.toastHere(getContext(),service.getMESSAGE());

                                }else if(service.isError()){
                                    SC.toastHere(getContext(),service.getMESSAGE());
                                }
                            }else {
                                SC.toastHere(getContext(),"Response error");
                            }
                        }

                        @Override
                        public void onFailure(Call<Service> call, Throwable t) {
                            SC.toastHere(getContext(),t.getMessage());

                        }
                    });
                }
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetroLib.getAPIServices().deleteService(Pref.getUser(context).getTOKEN(),SERVICE_ID).enqueue(new Callback<Service>() {
                    @Override
                    public void onResponse(Call<Service> call, Response<Service> response) {
                        if(response.isSuccessful()){
                            Service service = response.body();
                            if(service.isSaved()){
                                getDialog().dismiss();
                                getDialog().hide();
                                SC.toastHere(getContext(),service.getMESSAGE());

                            }else if(service.isError()){
                                SC.toastHere(getContext(),service.getMESSAGE());
                            }
                        }else {
                            SC.toastHere(getContext(),"Response error");
                        }
                    }

                    @Override
                    public void onFailure(Call<Service> call, Throwable t) {
                        SC.toastHere(context,t.getMessage());

                    }
                });
            }
        });
         return view;
    }
}
