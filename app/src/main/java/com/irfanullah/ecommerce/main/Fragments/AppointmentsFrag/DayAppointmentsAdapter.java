package com.irfanullah.ecommerce.main.Fragments.AppointmentsFrag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.Glib;
import com.irfanullah.ecommerce.Models.Appointment;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.R;

import java.util.ArrayList;

public class DayAppointmentsAdapter extends RecyclerView.Adapter<DayAppointmentsAdapter.AptView> {

    private Context context;
    private ArrayList<Appointment> appointments;
    AppointmentClickListenr apptClickListener;

    public DayAppointmentsAdapter(Context context, ArrayList<Appointment> appointments) {
        this.context = context;
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public AptView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_appointment_row,viewGroup,false);
        return new AptView(view,context,apptClickListener,this.appointments);
    }

    @Override
    public void onBindViewHolder(@NonNull AptView aptView, int i) {
        Appointment appointment = appointments.get(i);
        aptView.username.setText(appointment.getUSERNAME());
        aptView.time_textview.setText(appointment.getTIMERANGE());

//        if(!appointment.getPROFILE_IMAGE().isEmpty()){
            Glib.loadImage(context,appointment.getPROFILE_IMAGE()).into(aptView.profile_image);
 //       }

        if(Integer.parseInt(appointment.getIS_CONFIRMED()) == 1){
            aptView.confirmBtn.setBackgroundResource(R.drawable.green_circle_check);
            aptView.declineBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.appointments.size();
    }

    public static class AptView extends RecyclerView.ViewHolder {
        private ImageView profile_image;
        private TextView username;
        private TextView time_textview;
        private Button confirmBtn, declineBtn;


        public AptView(@NonNull View itemView, Context context, final AppointmentClickListenr apptClickListener, final ArrayList<Appointment> appointments) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.usernameTextView);
            time_textview = itemView.findViewById(R.id.timeTextView);
            confirmBtn = itemView.findViewById(R.id.confirmBtn);
            declineBtn = itemView.findViewById(R.id.declineBtn);

            profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apptClickListener.onAptClickListener(getAdapterPosition(),appointments.get(getAdapterPosition()));
                }
            });

            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apptClickListener.onAptClickListener(getAdapterPosition(),appointments.get(getAdapterPosition()));
                }
            });

            time_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apptClickListener.onAptClickListener(getAdapterPosition(),appointments.get(getAdapterPosition()));
                }
            });


            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View vv = v.getRootView();
                    Button confirm = vv.findViewById(R.id.confirmBtn);
                    Button decline = vv.findViewById(R.id.declineBtn);
                    decline.setVisibility(View.GONE);
                    confirm.setBackgroundResource(R.drawable.green_circle_check);
                    apptClickListener.onConfirmClickListener(getAdapterPosition(),appointments.get(getAdapterPosition()));
                }
            });

            declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apptClickListener.onDeclineClickListener(getAdapterPosition(),appointments.get(getAdapterPosition()));
                }
            });
        }
    }

    public void notifyAdapter(ArrayList<Appointment> appointments){
        this.appointments = appointments;
        notifyDataSetChanged();
    }


    public interface AppointmentClickListenr {
        void onAptClickListener(int position, Appointment apt);
        void onConfirmClickListener(int position, Appointment apt);
        void onDeclineClickListener(int position, Appointment apt);
    }

    public void setOnAppointmentClickListener(AppointmentClickListenr clickListener){
        this.apptClickListener = clickListener;
    }
}
