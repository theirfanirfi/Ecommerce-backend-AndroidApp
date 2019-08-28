package com.irfanullah.ecommerce.main.Fragments.Chat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Participants;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.main.Adapters.ParticipantsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class ChatFragment extends Fragment implements SearchView.OnQueryTextListener {

    private ProgressBar progressBar;
    private RecyclerView rv;
    private ParticipantsAdapter participantsAdapter;
    private Context context;
    private ArrayList<Participants> participantsArrayList;
    SearchView searchView;
    User user;
    public ChatFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        searchView = rootView.findViewById(R.id.chatSearchView);
        context = getContext();
        searchView.setQueryHint("Search chat");
        searchView.setEnabled(true);
        searchView.setFocusable(true);
        searchView.setOnQueryTextListener(this);
        user = Pref.getUser(context);
        initializeObjects(rootView);
        getParticipants();
        return rootView;
    }

    public void initializeObjects(View rootView){
        participantsArrayList = new ArrayList<>();
        rv = rootView.findViewById(R.id.chatRV);
        progressBar = rootView.findViewById(R.id.participantLoadingProgressbar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        participantsAdapter = new ParticipantsAdapter(context,participantsArrayList);
        rv.setAdapter(participantsAdapter);
    }

    public void getParticipants(){
        RetroLib.getAPIServices().getParticipants(Pref.getUser(context).getTOKEN()).enqueue(new Callback<Participants>() {
            @Override
            public void onResponse(Call<Participants> call, Response<Participants> response) {
                if(response.isSuccessful()){
                    Participants participants = response.body();
                   // if(participants.getAuthenticated()){
                        if(participants.getFound()){
                            updateAdapter(participants.getPARTICIPANTS());
                        }else if(participants.getError()) {
                            //RMsg.toastHere(context,participants.getMESSAGE());
                        }else {
                            //  RMsg.toastHere(context,participants.getMESSAGE());
                        }
//                    }else {
//                        SC.toastHere(getContext(),"You are not logged in.");
//                    }

                }else {
                    // RMsg.toastHere(getContext(),RMsg.REQ_ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<Participants> call, Throwable t) {
                SC.toastHere(getContext(),t.toString());
            }
        });

        progressBar.setVisibility(View.GONE);
    }

    public void updateAdapter(ArrayList<Participants> participantsArrayList1){
        this.participantsArrayList= participantsArrayList1;
        participantsAdapter.notifyAdapter(participantsArrayList1);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //  RMsg.toastHere(context,"working");
        filterChat(s);
        return false;
    }

    private void filterChat(String query){
        ArrayList<Participants> filteredArrayList = new ArrayList<>();
        for(int i = 0;i<participantsArrayList.size();i++){
            Participants participants = participantsArrayList.get(i);
            if(participants.getUSERNAME().toLowerCase().contains(query.toLowerCase())){
                filteredArrayList.add(participants);
                participantsAdapter.notifyAdapter(filteredArrayList);
            }else {
                participantsAdapter.notifyAdapter(filteredArrayList);
            }
        }
    }
}
