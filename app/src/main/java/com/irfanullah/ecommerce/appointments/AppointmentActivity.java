package com.irfanullah.ecommerce.appointments;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.listeners.OnMonthChangeListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.irfanullah.ecommerce.R;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AppointmentActivity extends AppCompatActivity {
    private static final String TAG = "AppointmentActivity";
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
//
//        calendarView = findViewById(R.id.calendarView);
//
//        //Set days you want to connect
//        Calendar calendar = Calendar.getInstance();
//        final Set<Long> days = new TreeSet<>();
//        Log.i(TAG, "onCreate: "+calendar.getTimeInMillis());
//        days.add(calendar.getTimeInMillis());
        String myDate = "2019/07/15 09:10:00";
        String myDate2 = "2019/07/16 09:10:00";
//creates a formatter that parses the date in the given format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        Date date2 = null;
        try {
            date = sdf.parse(myDate);
             date2 = sdf.parse(myDate2);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        long timeInMillis = date.getTime();
//
//        days.add(timeInMillis);
//        days.add(date2.getTime());
//        //1594788300000l
//      //  days.add(calendar.getTime(calendar.));
//       // days.add();
//
//calendarView.setConnectedDayIconRes(R.drawable.drawable_circle);
//calendarView.setConnectedDaySelectedIconRes(R.drawable.selected_bg);
////Define colors
//        int textColor = Color.parseColor("#ff0000");
//        int selectedTextColor = Color.parseColor("#ff4000");
//        int disabledTextColor = Color.parseColor("#ff8000");
//        ConnectedDays connectedDays = new ConnectedDays(days, Color.RED, selectedTextColor, disabledTextColor);
////        calendarView.setSelectedDayBackgroundColor(Color.RED);
//
////Connect days to calendar
//        calendarView.addConnectedDays(connectedDays);
//        calendarView.update();
//
//        calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
//            @Override
//            public void onMonthChanged(Month month) {
//                Log.i(TAG, "onDaysSelected: "+month.getMonthName());
//
//            }
//        });
//        new CalendarDialog(this, new OnDaysSelectionListener() {
//            @Override
//            public void onDaysSelected(List<Day> selectedDays) {
//                Log.i(TAG, "onDaysSelected more: "+selectedDays);
//             //   days.add(LoongselectedDays.get(0));
//            }
//        }).show();
//
//        calendarView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendarView.setSelected(true);
//            }
//        });
//        CalenderEvent calenderEvent = findViewById(R.id.calender_event);
//        Calendar calendar = new GregorianCalendar();
//        Event event = new Event(timeInMillis, "appointemnt", getResources().getColor(R.color.blackText));
//// to set desire day time milli second in first parameter
////or you set color for each event
////        Event event = new Event(calendar.getTimeInMillis(), "Test",Color.RED);
//        calenderEvent.addEvent(event);
//
//        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
//            @Override
//            public void onGetDay(DayContainerModel dayContainerModel) {
//                Log.d(TAG, dayContainerModel.getDate());
//            }
//        });



        //third


        final CompactCalendarView compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        Event ev1 = new Event(Color.GREEN, timeInMillis, "Some extra data that I want to store.");
        compactCalendarView.addEvent(ev1);

        Event ev2 = new Event(Color.GREEN, date2.getTime(), "Some extra data that I want to store.");
        compactCalendarView.addEvent(ev2);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });

    }
}
