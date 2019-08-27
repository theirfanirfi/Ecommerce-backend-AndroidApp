package com.irfanullah.ecommerce.appointmentsnotifications;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.R;

import com.irfanullah.ecommerce.appointmentsnotifications.Logic.AppointmentsNotificationPresenter;
import com.irfanullah.ecommerce.appointmentsnotifications.Logic.Logic;

import java.util.ArrayList;

public class AppointmentsNotificationActivity extends AppCompatActivity implements Logic.View {

    private AppointmentsNotificationAdapter appointmentsAdapter;
    private RecyclerView rv;
    private ArrayList<Appointment> appointments;
    private Context context;
    private AppointmentsNotificationPresenter presenter;
    private TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointments);
        initObjects();
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


    private void initObjects(){
        context = this;
        presenter = new AppointmentsNotificationPresenter(this,context);
        rv = findViewById(R.id.userAppointmentsRecyclerView);
        username = findViewById(R.id.username);
        username.setText("Appointments Notifications");
        appointmentsAdapter = presenter.configRecyclerView(rv);

        presenter.fetchNotifications();
    }
}
