package com.irfanullah.ecommerce.UserAppointments.Logic;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.UserAppointments.UserAppointmentsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAppointmentsPresenter implements Logic.Presenter {
    private Logic.View view;
    private Context context;
    private UserAppointmentsAdapter userAppointmentsAdapter;
    private ArrayList<Appointment> appointments;

    public UserAppointmentsPresenter(Logic.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public UserAppointmentsAdapter configRecyclerView(RecyclerView rv) {
        appointments = new ArrayList<>();
        userAppointmentsAdapter = new UserAppointmentsAdapter(context,appointments);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(userAppointmentsAdapter);
        return userAppointmentsAdapter;
    }

    @Override
    public void fetchUserAppointments(String user_id) {
        RetroLib.getAPIServices().getUserAppointments(Pref.getUser(context).getTOKEN(),user_id).enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.isSuccessful()){
                    Appointment appointment = response.body();
                    if(appointment.isError()){
                        SC.toastHere(context,appointment.getMESSAGE());
                    }else if(appointment.isFound()){
                        view.onAppointmentsLoaded(appointment.getAppointments());
                        view.onUserLoaded(appointment.getAppointments().get(0).getUSERNAME());
                    }else {
                        SC.toastHere(context,appointment.getMESSAGE());

                    }
                }else {
                    SC.toastHere(context,response.message());
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                SC.toastHere(context,t.getMessage());

            }
        });
    }
}
