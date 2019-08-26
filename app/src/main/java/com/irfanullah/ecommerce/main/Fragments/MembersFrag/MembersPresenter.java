package com.irfanullah.ecommerce.main.Fragments.MembersFrag;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.UserAppointments.UserAppointmentsActivity;
import com.irfanullah.ecommerce.main.Adapters.CategoriesAdapter;
import com.irfanullah.ecommerce.main.Adapters.MembersAdapter;
import com.irfanullah.ecommerce.membercheckouts.MemberCheckoutsActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MembersPresenter implements MembersLogic.Presenter, MembersAdapter.MemberClickListener {
    private Context context;
    private ArrayList<User> users;
    private MembersLogic.View view;
    private MembersAdapter membersAdapter;

    public MembersPresenter(Context context,MembersLogic.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void makeGetMembersRequest() {
        RetroLib.getAPIServices().getMembers(Pref.getUser(context).getTOKEN()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    if(user.isError()){
                        view.hideProgressBar();
                        view.onError(user.getMESSAGE());
                    }else if(user.isAuthenticated()){
                        if(user.isFound()){
                            view.onMembersLoaded(user.getMEMBERS());
                            view.hideProgressBar();
                        }else {
                            view.hideProgressBar();
                            view.onError(user.getMESSAGE());
                        }
                    }else {
                        view.hideProgressBar();
                        view.onError(user.getMESSAGE());

                    }
                }else {
                    view.hideProgressBar();
                    view.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.hideProgressBar();
                view.onError(t.getMessage());
            }
        });
    }

    @Override
    public MembersAdapter setUpRecyclerView(RecyclerView rv) {
        users = new ArrayList<>();
        membersAdapter = new MembersAdapter(context,users);
        membersAdapter.setOnMemberClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(membersAdapter);
        return membersAdapter;
    }

    @Override
    public void onMemberClick(int position, User user) {
        //view.onError(user.getUSERNAME());
        Intent userAppointments = new Intent(context, UserAppointmentsActivity.class);
        userAppointments.putExtra("member_id",user.getUSER_ID());
        context.startActivity(userAppointments);
    }
}
