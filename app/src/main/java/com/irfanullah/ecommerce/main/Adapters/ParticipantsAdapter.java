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

      //  loadLastMessageAndUnReadMessagesCount(participants, participantsViewHolder);

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
                    int USER_ID = 0;
                    User user = Pref.getUser(context);

//                    Intent profileAct = new Intent(context, NLUserProfile.class);
//                    profileAct.putExtra("user_id",USER_ID);
//                    context.startActivity(profileAct);
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

//    private void loadLastMessageAndUnReadMessagesCount(final Participants participants, final ParticipantsViewHolder participantsViewHolder){
//        final User user = Pref.getUser(context);
//        RetroLib.getAPIServices().getUnReadAndLast(user.getTOKEN(),participants.getCHAT_ID()).enqueue(new Callback<Messenger>() {
//            @Override
//            public void onResponse(Call<Messenger> call, Response<Messenger> response) {
//                if(response.isSuccessful()){
//                    Messenger msg = response.body();
//                    if(msg.getIS_AUTHENTICATED()){
//                        if(msg.getIS_ERROR()){
//                            //RMsg.logHere(msg.getMESSAGE());
//                        }else {
//                            Messenger last_msg = msg.getLAST_MESSAGE();
//                            int count = msg.getCOUNT_READ_MESSAGES();
//
//                            if(msg.getLAST_MESSAGE_COUNT() > 0) {
//                                String who_sent = "";
//                                if(msg.getSENDER_ID() == user.getUSER_ID()){
//                                    who_sent = "Me: ";
//                                }else {
//                                    if(user.getUSER_ID() == participants.getUSER_ONE()) {
//                                        who_sent = participants.getUSER_TWO_NAME() + ": ";
//                                    }else {
//                                        who_sent = participants.getUSER_ONE_NAME() + ": ";
//                                    }
//                                }
//
//                                participantsViewHolder.last_message.setText(who_sent+last_msg.getMESSAGE());
//                                participantsViewHolder.last_message.setVisibility(View.VISIBLE);
//                            }
//
//
//                            if(msg.getCOUNT_READ_MESSAGES() > 0) {
//                                participantsViewHolder.unread_messages_count.setText(Integer.toString(count));
//                                participantsViewHolder.unread_messages_count.setVisibility(View.VISIBLE);
//                            }
//                        }
//                    }else {
//                        SC.logHere(msg.getMESSAGE());
//                    }
//                }else {
//                    SC.logHere(response.raw().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Messenger> call, Throwable t) {
//
//            }
//        });
//    }

}

