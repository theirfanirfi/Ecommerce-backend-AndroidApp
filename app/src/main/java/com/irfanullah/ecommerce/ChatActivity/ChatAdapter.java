package com.irfanullah.ecommerce.ChatActivity;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.Glib;
import com.irfanullah.ecommerce.Libraries.TimeDiff;
import com.irfanullah.ecommerce.Models.Messenger;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private Context context;
    private ArrayList<Messenger> messengerArrayList;
    private User loggedUser;

    public ChatAdapter(Context context,ArrayList<Messenger> messengerArrayList) {
        this.context = context;
        this.messengerArrayList = messengerArrayList;
        loggedUser = Pref.getUser(context);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_row,viewGroup,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {
        Messenger messenger = messengerArrayList.get(i);
        if(Integer.parseInt(loggedUser.getUSER_ID()) == messenger.getSENDER_ID()){

            chatViewHolder.sender.setText(messenger.getMESSAGE());
            chatViewHolder.sender.setVisibility(View.VISIBLE);
            chatViewHolder.sender_time.setText(TimeDiff.getTimeDifference(messenger.getCREATED_AT()));
            chatViewHolder.sender_time.setVisibility(View.VISIBLE);
            chatViewHolder.reciever.setVisibility(View.GONE);
            chatViewHolder.reciever_time.setVisibility(View.GONE);
            chatViewHolder.profile_image.setVisibility(View.GONE);

        }else {

            chatViewHolder.reciever.setText(messenger.getMESSAGE());
//            Glib.loadImage(context,messenger.getP)
            chatViewHolder.reciever.setVisibility(View.VISIBLE);
            chatViewHolder.reciever_time.setText(TimeDiff.getTimeDifference(messenger.getCREATED_AT()));
            chatViewHolder.reciever_time.setVisibility(View.VISIBLE);
            chatViewHolder.profile_image.setVisibility(View.VISIBLE);
            chatViewHolder.sender.setVisibility(View.GONE);
            chatViewHolder.sender_time.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messengerArrayList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView sender,reciever,sender_time, reciever_time;
        private ImageView profile_image;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.sender);
            profile_image = itemView.findViewById(R.id.profile_image);
            reciever = itemView.findViewById(R.id.reciever);
            sender_time = itemView.findViewById(R.id.senderTime);
            reciever_time = itemView.findViewById(R.id.recieverTime);
        }
    }

    public void notifyAdapter(ArrayList<Messenger> messengerArrayList){
        this.messengerArrayList = messengerArrayList;
        notifyDataSetChanged();
    }
}

