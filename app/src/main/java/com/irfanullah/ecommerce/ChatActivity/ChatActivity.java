package com.irfanullah.ecommerce.ChatActivity;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Messenger;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ChatAdapter chatAdapter;
    private Context context;
    private int CHAT_WITH_USER = 0;
    private int CHAT_ID = 0, START =0;
    private User user;
    private ArrayList<Messenger> messengerArrayList;
    private EditText messageField;
    private ImageView sendBtn;
    private Runnable runnable;
    private Handler handler;
    private static final String TAG = "ChatActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initializeObjects();
        getExtras();
        loadMessages();
        sendMessage();
        refereshMessages();
    }

    private void refereshMessages() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMessages();
                handler.postDelayed(this,5000);
            }
        },5000);
    }

    private void sendMessage() {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = messageField.getText().toString();
                SC.logHere(message+" : "+Integer.toString(CHAT_WITH_USER));
                RetroLib.getAPIServices().sendMessage(user.getTOKEN(),Integer.toString(CHAT_WITH_USER),message).enqueue(new Callback<Messenger>() {
                    @Override
                    public void onResponse(Call<Messenger> call, Response<Messenger> response) {
                        if(response.isSuccessful()){
                            Messenger messenger = response.body();
                            if(messenger.getIS_ERROR()){
                                SC.toastHere(context,messenger.getRESPONSE_MESSAGE());
                            }else {
                                if(messenger.getIS_AUTHENTICATED()){
                                    if(messenger.getIS_SENT()){
                                        //SC.toastHere(context,messenger.getRESPONSE_MESSAGE());
                                        messageField.setText("");
                                        START = 0;
                                        loadMessages();
                                    }else {
                                        SC.toastHere(context,messenger.getRESPONSE_MESSAGE());
                                    }
                                }else {
                                    SC.toastHere(context,"You are not logged in.");
                                }
                            }
                        }else {
                            SC.toastHere(context,"Request error");
                            SC.logHere(response.raw().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Messenger> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            CHAT_WITH_USER = bundle.getInt("user_id");
            CHAT_ID = bundle.getInt("chat_id");
        }else {
            finish();
        }

    }

    private void initializeObjects() {
        context = this;
        user = Pref.getUser(context);
        messageField = findViewById(R.id.messageField);
        sendBtn = findViewById(R.id.sendBtn);
        messengerArrayList = new ArrayList<>();
        rv = findViewById(R.id.chatRV);
        chatAdapter = new ChatAdapter(context, messengerArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.smoothScrollToPosition(messengerArrayList.size() > 0 ? messengerArrayList.size() - 1 : messengerArrayList.size());
        rv.setAdapter(chatAdapter);
    }

    private void recyclerViewSetup(ArrayList<Messenger> messengerArrayList) {
        chatAdapter = new ChatAdapter(context, messengerArrayList);
        //rv.smoothScrollToPosition(messengerArrayList.size() - 1);
        rv.setAdapter(chatAdapter);
    }

    private void notifyAdapter(ArrayList<Messenger> messengerArrayList){
        chatAdapter.notifyAdapter(messengerArrayList);
    }
    private void loadMessages(){
        SC.logHere("CHAT ID: "+Integer.toString(CHAT_ID));
      //  SC.iLogHere(CHAT_ID);
        RetroLib.getAPIServices().getMessages(user.getTOKEN(),Integer.toString(CHAT_ID)).enqueue(new Callback<Messenger>() {
            @Override
            public void onResponse(Call<Messenger> call, Response<Messenger> response) {
                if(response.isSuccessful()){
                    Messenger messenger = response.body();
                    if(messenger.getIS_ERROR()){
                      //  RMsg.logHere(messenger.getRESPONSE_MESSAGE());
                    }else {
                        if(messenger.getIS_AUTHENTICATED()){
                            if(messenger.getIS_FOUND()){
                                //messengerArrayList = messenger.getMESSENGER();
                                //messengerArrayList.addAll(messenger.getMESSENGER());
                                //recyclerViewSetup(messenger.getMESSENGER());
                                notifyAdapter(messenger.getMESSENGER());
                                SC.iLogHere(messenger.getMESSENGER().size());
                                //if(START == 0) {
                                //START++;
                                rv.smoothScrollToPosition(messenger.getMESSENGER().size() > 0 ? messenger.getMESSENGER().size() - 1 : messenger.getMESSENGER().size());
                                // }
                            }else {
                               // RMsg.logHere(messenger.getRESPONSE_MESSAGE());

                            }
                        }else {
                           /// RMsg.logHere(RMsg.AUTH_ERROR_MESSAGE);
                        }
                    }
                }else {
                  //  RMsg.logHere(RMsg.REQ_ERROR_MESSAGE);
                  //  RMsg.logHere(response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<Messenger> call, Throwable t) {
                SC.logHere(t.toString());
            }
        });
    }
}

