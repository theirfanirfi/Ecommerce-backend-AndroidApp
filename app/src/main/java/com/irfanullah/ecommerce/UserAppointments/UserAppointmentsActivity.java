package com.irfanullah.ecommerce.UserAppointments;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.UserAppointments.Logic.Logic;
import com.irfanullah.ecommerce.UserAppointments.Logic.UserAppointmentsPresenter;

import java.util.ArrayList;

public class UserAppointmentsActivity extends AppCompatActivity implements Logic.View {

    private UserAppointmentsAdapter userAppointmentsAdapter;
    private RecyclerView rv;
    private String USER_ID = "0";
    private ArrayList<Appointment> appointments;
    private Context context;
    private UserAppointmentsPresenter presenter;
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
        userAppointmentsAdapter.notifyAdapter(appointments);
    }

    @Override
    public void onUserLoaded(String user) {
        username.setText(user+"'s Appointments");
    }

    @Override
    public void onMessage(String msg) {
        SC.toastHere(context,msg);
    }


    private void initObjects(){
        context = this;
        this.USER_ID = getIntent().getExtras().getString("member_id");
        presenter = new UserAppointmentsPresenter(this,context);
        rv = findViewById(R.id.userAppointmentsRecyclerView);
        username = findViewById(R.id.username);
        userAppointmentsAdapter = presenter.configRecyclerView(rv);

        presenter.fetchUserAppointments(USER_ID);
    }
}
