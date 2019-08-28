package com.irfanullah.ecommerce.main.Fragments.AppointmentsFrag;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Appointment;

import java.util.ArrayList;

public interface AptFragLogic {
    interface View {

        void appointmentsLoaded(ArrayList<Appointment> appointments);
        void dayAppointmentsLoaded(ArrayList<Appointment> appointments);
        void noAppointmentsForTheDay(String message);
        void showError(String message);
        void appointmentConfirmed(int position);
        void appointmentDeclined(int position);

    }

    interface Presenter {
        void makeMonthAppointmentsRequest(String year,String month);
        DayAppointmentsAdapter setUpRvAndReturnAdapter(RecyclerView rv);
        void loadAppointmentsForTheClickedDay(String year,String month,String day);

        void declineAppointment(String id, int position);
        void confirmAppointment(String id, int position);

    }
}
