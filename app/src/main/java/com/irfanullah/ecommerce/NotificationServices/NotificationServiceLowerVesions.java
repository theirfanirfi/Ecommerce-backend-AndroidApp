package com.irfanullah.ecommerce.NotificationServices;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
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

public class NotificationServiceLowerVesions extends Service {
    private Boolean IS_RUNNING = false;
    private final int DELAY = 300000;
    private Handler handler;
    private Runnable runnable;
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
        SC.toastHere(this, "lower vestion service started.");
        if(!IS_RUNNING){
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    checkNotification();
                    handler.postDelayed(this,120000);
                }
            };

            handler.postDelayed(runnable,10000);
            IS_RUNNING = true;
        }


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

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Barber")
                .setContentText(NotificationContent)
                .setSmallIcon(R.drawable.blue_circle_check)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_LOW)
                .setOnlyAlertOnce(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        SC.logHere("id: "+Integer.toString(id));
//        SC.logHere("working");
        notificationManager.notify(2, notification);
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
                           notification((appointment.getAPPOINTMENTS_COUNT()+appointment.getCHAT_COUNT()));
                        }

                        else if(appointment.getAPPOINTMENTS_COUNT() > 0 && appointment.getCHAT_COUNT() == 0){
                            NotificationContent = "You have new Appointments.";
                            notification((appointment.getAPPOINTMENTS_COUNT()+appointment.getCHAT_COUNT()));
                        } else if(appointment.getAPPOINTMENTS_COUNT() == 0 && appointment.getCHAT_COUNT() > 0){
                            NotificationContent = "You have new Chats.";
                            notification((appointment.getAPPOINTMENTS_COUNT()+appointment.getCHAT_COUNT()));
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
}

