package com.irfanullah.ecommerce.main.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irfanullah.ecommerce.ChatActivity.ChatActivity;
import com.irfanullah.ecommerce.Libraries.Glib;
import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Messenger;
import com.irfanullah.ecommerce.Models.Participants;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.UserAppointments.UserAppointmentsActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantsViewHolder> {

    private Context context;
    private ArrayList<Participants> participantsArrayList;
    public ParticipantsAdapter(Context context,ArrayList<Participants> participantsArrayList) {
        this.context = context;
        this.participantsArrayList = participantsArrayList;
    }

    @NonNull
    @Override
    public ParticipantsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.participants_row,viewGroup,false);

        return new ParticipantsViewHolder(view,context,this.participantsArrayList);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantsViewHolder participantsViewHolder, int i) {
        Participants pt = participantsArrayList.get(i);
        participantsViewHolder.chatWithUsername.setText(pt.getUSERNAME());
        if(!pt.getUSER_PROFILE_IMAGE().isEmpty()){
          Glib.loadImage(context,pt.getUSER_PROFILE_IMAGE()).into(participantsViewHolder.profile_image);
        }

        participantsViewHolder.last_message.setText(pt.getLAST_MESSAGE());

        if(pt.getUNREAD_MSG_COUNT() > 0){
            participantsViewHolder.unread_messages_count.setText(Integer.toString(pt.getUNREAD_MSG_COUNT()));
            participantsViewHolder.unread_messages_count.setVisibility(View.VISIBLE);
        }else {
            participantsViewHolder.unread_messages_count.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return participantsArrayList.size();
    }

    public static class ParticipantsViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView chatWithUsername, last_message, last_message_time;
        private ImageView profile_image;
        private ConstraintLayout pLayout;
        private TextView unread_messages_count;


        public ParticipantsViewHolder(@NonNull View itemView,final Context context,final ArrayList<Participants> participantsArrayList) {
            super(itemView);
            this.context = context;
            chatWithUsername = itemView.findViewById(R.id.chatWithUsername);
            profile_image = itemView.findViewById(R.id.profile_image);
            pLayout = itemView.findViewById(R.id.participantLayout);
            last_message = itemView.findViewById(R.id.last_message_textView);
            last_message_time = itemView.findViewById(R.id.last_msg_time);
            unread_messages_count = itemView.findViewById(R.id.unread_msgs_count);
            unread_messages_count.setVisibility(View.GONE);

            gotoChat(participantsArrayList);

            profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Participants participants = participantsArrayList.get(position);
                    Intent intent = new Intent(context, UserAppointmentsActivity.class);
                    intent.putExtra("member_id",Integer.toString(participants.getUSER_ID()));
                    context.startActivity(intent);
                }
            });
        }



        private void gotoChat(final ArrayList<Participants> participantsArrayList){
            pLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    User user = Pref.getUser(context);
                    Participants participants = participantsArrayList.get(position);
                    int CHAT_ID = participants.getCHAT_ID();
                    Intent chatAct = new Intent(context, ChatActivity.class);
                        int CHAT_WITH_USER = participants.getUSER_ID();
                        chatAct.putExtra("user_id",CHAT_WITH_USER);
//                    SC.iLogHere(CHAT_WITH_USER);
//                    SC.iLogHere(participants.getUSER_ID());
                        chatAct.putExtra("chat_id",CHAT_ID);
                        context.startActivity(chatAct);

                }
            });
        }
    }

    public void notifyAdapter(ArrayList<Participants> participantsArrayList){
        this.participantsArrayList = participantsArrayList;
        notifyDataSetChanged();
    }



}

