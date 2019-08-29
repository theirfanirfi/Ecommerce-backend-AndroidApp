package com.irfanullah.ecommerce.main.Fragments.NotificationsFrag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.UserAppointments.UserAppointmentsActivity;
import com.irfanullah.ecommerce.appointmentsnotifications.AppointmentsNotificationAdapter;
import com.irfanullah.ecommerce.appointmentsnotifications.Logic.AppointmentsNotificationPresenter;
import com.irfanullah.ecommerce.appointmentsnotifications.Logic.Logic;
import com.irfanullah.ecommerce.main.Adapters.MembersAdapter;
import com.irfanullah.ecommerce.main.Fragments.MembersFrag.MembersLogic;
import com.irfanullah.ecommerce.main.Fragments.MembersFrag.MembersPresenter;

import java.util.ArrayList;

public class NotificationsFrag extends Fragment implements Logic.View, AppointmentsNotificationAdapter.AppointmentClickListenr  {
    private AppointmentsNotificationAdapter appointmentsAdapter;
    private RecyclerView rv;
    private ArrayList<Appointment> appointments;
    private Context context;
    private AppointmentsNotificationPresenter presenter;
    private TextView username;
    private boolean isVisible;
    private boolean isStarted;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_user_appointments,container,false);
        context = getContext();

        initObjects(view);
        return view;
    }

    private void initObjects(View view){
        presenter = new AppointmentsNotificationPresenter(this,context);
        rv = view.findViewById(R.id.userAppointmentsRecyclerView);
        username = view.findViewById(R.id.username);
        username.setText("Appointments Notifications");
        username.setTextColor(getResources().getColor(R.color.whiteColor));
        appointmentsAdapter = presenter.configRecyclerView(rv);
        appointmentsAdapter.setOnAppointmentClickListener(this);

        presenter.fetchNotifications();

    }

    @Override
    public void onAptClickListener(int position, Appointment apt) {
        Intent intent = new Intent(context, UserAppointmentsActivity.class);
        intent.putExtra("member_id",apt.getUSER_ID());
        startActivity(intent);

    }

    @Override
    public void onConfirmClickListener(int position, Appointment apt) {
        SC.logHere(apt.getAPPOINTMENT_ID());
        presenter.confirmAppointment(apt.getAPPT_ID_(),position);
    }

    @Override
    public void onDeclineClickListener(int position, Appointment apt) {
        presenter.declineAppointment(apt.getAPPT_ID_(),position);

    }

    @Override
    public void appointmentConfirmed(int position) {
        appointments.remove(position);
        appointmentsAdapter.notifyAdapter(appointments);
    }

    @Override
    public void appointmentDeclined(int position) {
        appointments.remove(position);
        appointmentsAdapter.notifyAdapter(appointments);
    }

    @Override
    public void onAppointmentsLoaded(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
        appointmentsAdapter.notifyAdapter(appointments);
    }


    @Override
    public void onMessage(String msg) {
        SC.toastHere(context,msg);
    }

    @Override
    public void noNewNotificationsFound() {
        username.setText("No new notification.");
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible)
            presenter.fetchNotifications(); //your request method
    }

    @Override
    public void onStop() {
        super.onStop();
        isStarted = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted)
            presenter.fetchNotifications(); //your request method
    }
}
