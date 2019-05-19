package com.irfanullah.ecommerce.main.Fragments.MembersFrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.main.Adapters.MembersAdapter;

import java.util.ArrayList;

public class MembersFrag extends Fragment implements MembersLogic.View {
    private Context context;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private MembersAdapter membersAdapter;
    private MembersPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.new_order_frag,container,false);
        context = getContext();
        presenter = new MembersPresenter(context,this);
        initObjects(view);
        return view;
    }

    private void initObjects(View view){
        rv = view.findViewById(R.id.checkoutsRV);
        progressBar = view.findViewById(R.id.progressBar);
        membersAdapter = presenter.setUpRecyclerView(rv);
        presenter.makeGetMembersRequest();
    }

    @Override
    public void onMembersLoaded(ArrayList<User> users) {
        membersAdapter.notifyAdapter(users);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        SC.toastHere(context,message);
    }
}
