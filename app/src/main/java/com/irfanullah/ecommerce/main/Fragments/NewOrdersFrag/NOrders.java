package com.irfanullah.ecommerce.main.Fragments.NewOrdersFrag;

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
import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.main.Adapters.OrdersAdapter;
import com.irfanullah.ecommerce.main.MainActivityLogic;

import java.util.ArrayList;

public class NOrders extends Fragment implements MainActivityLogic.NewOrdersView {
    NewOrdersPresenter presenter;
    private Context context;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private OrdersAdapter ordersAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.new_order_frag,container,false);
        context = getContext();
        presenter = new NewOrdersPresenter(this,context);
        initObjects(view);
        return view;
    }

    @Override
    public void onError(String msg) {
        SC.toastHere(context,msg);
    }

    @Override
    public void onOrdersLoaded(ArrayList<Order> orders) {
        ordersAdapter.notifyAdapter(orders);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        SC.toastHere(context,message);
    }

    private void initObjects(View view){
        rv = view.findViewById(R.id.newOrdersRv);
        progressBar = view.findViewById(R.id.progressBar);
        //ordersAdapter = presenter.setUpRV(rv);
        //presenter.makeOrdersRequest();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            ordersAdapter = presenter.setUpRV(rv);
            presenter.makeOrdersRequest();
        }
    }
}
