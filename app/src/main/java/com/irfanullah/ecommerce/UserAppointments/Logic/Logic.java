package com.irfanullah.ecommerce.UserAppointments.Logic;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.UserAppointments.UserAppointmentsAdapter;

import java.util.ArrayList;

public interface Logic {
    interface View {
        void onAppointmentsLoaded(ArrayList<Appointment> appointments);
        void onMessage(String msg);
        void onUserLoaded(String username);
        void appointmentConfirmed(int position);
        void appointmentDeclined(int position);
    }

    interface Presenter {
        UserAppointmentsAdapter configRecyclerView(RecyclerView rv);
        void declineAppointment(String id, int position);
        void confirmAppointment(String id, int position);
        void fetchUserAppointments(String user_id);
    }
}
