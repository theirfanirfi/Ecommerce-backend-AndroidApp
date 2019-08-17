package com.irfanullah.ecommerce.main.Fragments.AppointmentsFrag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentsFrag extends Fragment implements AptFragLogic.View{

    private static final String TAG = "AppointmentsFrag";
    private AptFragPresenter presenter;
    CompactCalendarView compactCalendarView;
    DayAppointmentsAdapter dayAppointmentsAdapter;
    RecyclerView rv;
    Calendar calendar;
    int thisYear = 0;
    int thisMonth = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.activity_appointment,container,false);
        setup(view);
        return view;
    }

    private void setup(View view) {
        presenter = new AptFragPresenter(this,getContext());
        rv = view.findViewById(R.id.viewDayAppointmentsInRV);
        Date c = Calendar.getInstance().getTime();
        int d = c.getDate();
        int m = c.getMonth()+1;
        int y = c.getYear();

        String dates = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        final String years = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        calendar = Calendar.getInstance();

        thisYear = calendar.get(Calendar.YEAR);
        thisMonth = calendar.get(Calendar.MONTH)+1;

        presenter.makeMonthAppointmentsRequest(Integer.toString(thisYear),Integer.toString(thisMonth));
        dayAppointmentsAdapter = presenter.setUpRvAndReturnAdapter(rv);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
//        date = sdf.parse(Integer.toString(y)+"/"+Integer.toString(m)+"/"+Integer.toString(d));
           // Log.i(TAG, "setup: "+date.toString()+" year: "+Integer.toString(y) + dates);
            date = sdf.parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       compactCalendarView = view.findViewById(R.id.compactcalendar_view);

        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setCurrentDate(date);
        compactCalendarView.showContextMenu();

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //List<Event> events = compactCalendarView.getEvents(dateClicked);
                calendar = Calendar.getInstance();

                thisYear = calendar.get(Calendar.YEAR);
                int thisMonth = dateClicked.getMonth()+1;
                String clickedDay = Integer.toString(dateClicked.getDate());
                presenter.loadAppointmentsForTheClickedDay(Integer.toString(thisYear),Integer.toString(thisMonth),clickedDay);
                //Log.d(TAG, "Day was clicked: "+Integer.toString(thisYear)+" : "+Integer.toString(thisMonth)+" : "+clickedDay);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                int nextMonth = firstDayOfNewMonth.getMonth()+1;
                int thisYear = calendar.get(Calendar.YEAR);

                presenter.makeMonthAppointmentsRequest(Integer.toString(thisYear),Integer.toString(nextMonth));

                //Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth.getMonth()+1);
            }
        });
    }

    @Override
    public void appointmentsLoaded(ArrayList<Appointment> appointments) {
        Event[] events = new Event[appointments.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        List<Event> events1 = new ArrayList<>();
        for (int i =0;i<appointments.size();i++) {
            try{
                date = sdf.parse(appointments.get(i).getFORMATED_DATE());
                Log.i(TAG, "appointmentsLoaded: "+date.toString());
                events[i] = new Event(Color.GREEN,date.getTime(), "appointment");
                events1.add(events[i]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            compactCalendarView.addEvents(events1);

        }
    }

    @Override
    public void dayAppointmentsLoaded(ArrayList<Appointment> appointments) {
        dayAppointmentsAdapter.notifyAdapter(appointments);
    }

    @Override
    public void noAppointmentsForTheDay(String message) {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }
}
