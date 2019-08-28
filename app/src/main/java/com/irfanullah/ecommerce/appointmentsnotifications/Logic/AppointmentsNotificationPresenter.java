package com.irfanullah.ecommerce.appointmentsnotifications.Logic;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.appointmentsnotifications.AppointmentsNotificationAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsNotificationPresenter implements Logic.Presenter{
    private Logic.View view;
    private Context context;
    private AppointmentsNotificationAdapter userAppointmentsAdapter;
    private ArrayList<Appointment> appointments;

    public AppointmentsNotificationPresenter(Logic.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public AppointmentsNotificationAdapter configRecyclerView(RecyclerView rv) {
        appointments = new ArrayList<>();
        userAppointmentsAdapter = new AppointmentsNotificationAdapter(context,appointments);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(userAppointmentsAdapter);
        return userAppointmentsAdapter;
    }

    @Override
    public void declineAppointment(String id, final int position) {
        RetroLib.getAPIServices().declineAppointment(Pref.getUser(context).getTOKEN(),id).enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.isSuccessful()){
                    Appointment appointment = response.body();
                    if(appointment.isError()){
                        SC.toastHere(context,appointment.getMESSAGE());
                    }else if(appointment.isDeclined()){
                        view.appointmentDeclined(position);
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

    @Override
    public void confirmAppointment(String id, final int position) {
        RetroLib.getAPIServices().confirmAppointment(Pref.getUser(context).getTOKEN(),id).enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.isSuccessful()){
                    Appointment appointment = response.body();
                    if(appointment.isError()){
                        SC.toastHere(context,appointment.getMESSAGE());
                    }else if(appointment.isConfirmed()){
                        view.appointmentConfirmed(position);
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

    @Override
    public void fetchNotifications() {
        RetroLib.getAPIServices().getAppointmentsNotifications(Pref.getUser(context).getTOKEN()).enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.isSuccessful()){
                    Appointment appointment = response.body();
                    if(appointment.isError()){
                        SC.toastHere(context,appointment.getMESSAGE());
                    }else if(appointment.isFound()){
                        view.onAppointmentsLoaded(appointment.getNOTIFICATIONS());
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
