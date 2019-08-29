package com.irfanullah.ecommerce.NotificationServices;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.irfanullah.ecommerce.NotificationServices.App.CHANNEL_ID;

public class NotificationService extends Service {
    private Boolean IS_RUNNING = false;
    private final int DELAY = 300000;
    private Handler handler;
    private Runnable runnable;
    private int NOTIFICATION_ID = 1;
    private String NotificationContent = "You will be notified about new notifications here.";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //SC.toastHere(this,"service started");
        if(!IS_RUNNING){
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    checkNotification();
                    handler.postDelayed(this,10000);
                }
            };

            handler.postDelayed(runnable,10000);
            IS_RUNNING = true;
        }


        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Barber")
                .setContentText("App service is running. You will be notified about new chats and " +
                        "appointments even your app is closed.")
                .setSmallIcon(R.drawable.blue_circle_check)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .build();
        startForeground(1,notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void notification(int id){
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Barber")
                .setContentText(NotificationContent)
                .setSmallIcon(R.drawable.blue_circle_check)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_LOW)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(id, notification);
//        NotificationManager notificationManager = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            notificationManager = getSystemService(NotificationManager.class);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notificationManager.createNotificationChannel(channel);
//        }
       // startForeground(1,notification);
    }

    private void checkNotification(){
        RetroLib.getAPIServices().getCountForChatAndNotificationsTab(Pref.getUser(this).getTOKEN()).enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.isSuccessful()){
                    Appointment appointment = response.body();
                    if(appointment.isError()){

                    }else {
                        if (appointment.getCHAT_COUNT() > 0 && appointment.getAPPOINTMENTS_COUNT() > 0){
                            SC.logHere(Integer.toString(appointment.getCHAT_COUNT()));
                           NotificationContent = "You have New Chats and Appointment";
                           NOTIFICATION_ID = appointment.getAPPOINTMENTS_COUNT()+appointment.getCHAT_COUNT();
                            checkNotificationInStatusBar( NOTIFICATION_ID);

                        }

                        else if(appointment.getAPPOINTMENTS_COUNT() > 0 && appointment.getCHAT_COUNT() == 0){
                            NotificationContent = "You have new Appointments.";
                            NOTIFICATION_ID = appointment.getAPPOINTMENTS_COUNT()+appointment.getCHAT_COUNT();
                            checkNotificationInStatusBar( NOTIFICATION_ID);

                        } else if(appointment.getAPPOINTMENTS_COUNT() == 0 && appointment.getCHAT_COUNT() > 0){
                            NOTIFICATION_ID = appointment.getAPPOINTMENTS_COUNT()+appointment.getCHAT_COUNT();

                            NotificationContent = "You have new Chats.";
                            checkNotificationInStatusBar( NOTIFICATION_ID);


                        }
                    }
                }else {
//                    SC.toastHere(context,"Response error");
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {

            }
        });
    }

    private void checkNotificationInStatusBar(int id){
        boolean isDisplayed = false;
        int isFirstTime = 1;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            StatusBarNotification[] notifications = mNotificationManager.getActiveNotifications();
            notifications = mNotificationManager.getActiveNotifications();
            for (StatusBarNotification notification : notifications) {
                if (notification.getId() == id) {
                    isDisplayed = true;
                    // Do something.
                    break;
                }else {
                   // notification();
                    isDisplayed = false;


                }
            }

            if(isDisplayed){
               // SC.toastHere(this,"Notification is already displayed");
            }else {
                if(isFirstTime == 1){
                    notification(id);
                    isFirstTime++;
                    SC.toastHere(this,"If block.");

                }
            }
        }

    }
}

