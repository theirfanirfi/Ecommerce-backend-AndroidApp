package com.irfanullah.ecommerce.appointmentsnotifications.Logic;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.UserAppointments.UserAppointmentsAdapter;
import com.irfanullah.ecommerce.appointmentsnotifications.AppointmentsNotificationAdapter;

import java.util.ArrayList;

public interface Logic {
    interface View {
        void onAppointmentsLoaded(ArrayList<Appointment> appointments);
        void onMessage(String msg);
        void appointmentConfirmed(int position);
        void appointmentDeclined(int position);
        void noNewNotificationsFound();
    }

    interface Presenter {
        AppointmentsNotificationAdapter configRecyclerView(RecyclerView rv);
        void declineAppointment(String id, int position);
        void confirmAppointment(String id, int position);
        void fetchNotifications();
    }
}
