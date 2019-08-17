package com.irfanullah.ecommerce.main.Fragments.AppointmentsFrag;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.Storage.Pref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AptFragPresenter implements AptFragLogic.Presenter, DayAppointmentsAdapter.AppointmentClickListenr {
    private AptFragLogic.View view;
    private Context context;
    private DayAppointmentsAdapter dayAppointmentsAdapter;
    private ArrayList<Appointment> appointments;

    public AptFragPresenter(AptFragLogic.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void makeMonthAppointmentsRequest(String year,String month) {

        RetroLib.getAPIServices().getMonthApts(Pref.getUser(context).getTOKEN(),month,year).enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.isSuccessful()){
                    Appointment appointment = response.body();
                    if(appointment.isAuthenticated()){
                        if(appointment.isError()){
                            view.showError(appointment.getMESSAGE());
                        }else if(appointment.isFound()){
                           // SC.toastHere(context,"found");
                            view.appointmentsLoaded(appointment.getAppointments());
                        }
                    }else {
                        view.showError(appointment.getMESSAGE());
                    }
                }else {
                    SC.toastHere(context,"response error");
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                view.showError(t.getMessage());
            }
        });
    }

    @Override
    public DayAppointmentsAdapter setUpRvAndReturnAdapter(RecyclerView rv) {
        appointments = new ArrayList<>();
        dayAppointmentsAdapter = new DayAppointmentsAdapter(context,appointments);
        dayAppointmentsAdapter.setOnAppointmentClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(dayAppointmentsAdapter);
        return dayAppointmentsAdapter;
    }

    @Override
    public void loadAppointmentsForTheClickedDay(String year, String month, String day) {
        RetroLib.getAPIServices().getDayApts(Pref.getUser(context).getTOKEN(),month,year,day).enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.isSuccessful()){
                    Appointment appointment = response.body();
                    if(appointment.isAuthenticated()){
                        if(appointment.isError()){
                            view.showError(appointment.getMESSAGE());
                        }else if(appointment.isFound()){
                            SC.toastHere(context,"found");
                            view.dayAppointmentsLoaded(appointment.getAppointments());
                        }else {
                            appointments = new ArrayList<>();
                            view.dayAppointmentsLoaded(appointments);

                        }
                    }else {
                        view.showError(appointment.getMESSAGE());
                    }
                }else {
                    SC.toastHere(context,"response error");
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                view.showError(t.getMessage());

            }
        });
    }

    @Override
    public void onAptClickListener(int position, Appointment apt) {
        view.showError(apt.getUSERNAME());

    }
}
