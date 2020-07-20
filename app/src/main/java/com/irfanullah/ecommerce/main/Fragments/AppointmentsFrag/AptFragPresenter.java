package com.irfanullah.ecommerce.main.Fragments.AppointmentsFrag;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.UserAppointments.UserAppointmentsActivity;

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
                    //SC.toastHere(context,"response error");
                    Log.i("sometag",response.toString());
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
               // view.showError(t.getMessage());
                Log.i("sometag","Exception: "+t.toString());

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
                            //view.showError(appointment.getMESSAGE());
                        }else if(appointment.isFound()){
//                            SC.toastHere(context,"found");
                            view.dayAppointmentsLoaded(appointment.getAppointments());
                        }else {
                            appointments = new ArrayList<>();
                            view.dayAppointmentsLoaded(appointments);

                        }
                    }else {
                        //view.showError(appointment.getMESSAGE());
                    }
                }else {
                    //SC.toastHere(context,"response error");
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                //view.showError(t.getMessage());

            }
        });
    }

    @Override
    public void onAptClickListener(int position, Appointment apt) {
        //view.showError(apt.getUSERNAME());
        Intent intent = new Intent(context, UserAppointmentsActivity.class);
        intent.putExtra("member_id",apt.getUSER_ID());
        context.startActivity(intent);

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
                        SC.toastHere(context,appointment.getMESSAGE());
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
                       // view.appointmentConfirmed(position);
                        view.showError(appointment.getMESSAGE());
                    }else {
                        SC.toastHere(context,appointment.getMESSAGE());

                    }
                }else {
                    SC.toastHere(context,response.message());
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                //SC.toastHere(context,t.getMessage());
                Log.i("sometag","Exception: "+t.toString());


            }
        });
    }

    @Override
    public void onConfirmClickListener(int position, Appointment apt) {
        confirmAppointment(apt.getAPPT_ID_(),position);
    }

    @Override
    public void onDeclineClickListener(int position, Appointment apt) {

        declineAppointment(apt.getAPPT_ID_(),position);
    }


}
