package com.irfanullah.ecommerce.service;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Service;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.service.Logic.ServiceLogic;
import com.irfanullah.ecommerce.service.Logic.ServicePresenter;

import java.util.ArrayList;

public class ServicesActivity extends AppCompatActivity implements ServiceLogic.View {
    private ServiceAdapter serviceAdapter;
    private RecyclerView recyclerView;
    private ServicePresenter presenter;
    private FloatingActionButton addServiceBtn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        initObjects();
    }

    private void initObjects() {
        context = this;
        presenter = new ServicePresenter(this,context);
        recyclerView = findViewById(R.id.serviceRv);
        addServiceBtn = findViewById(R.id.fab_btn);
        serviceAdapter = presenter.setUpRecyclerView(recyclerView);
        presenter.fetchServices();

        addServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addServiceAct = new Intent(context,AddServiceActivity.class);
                startActivity(addServiceAct);
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        SC.toastHere(context,msg);
    }

    @Override
    public void onServicesLoad(ArrayList<Service> services) {
        serviceAdapter.notifyAdapter(services);
    }

    @Override
    public void showDialog(Service service) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = new ServiceActionDialog();
        Bundle bundle = new Bundle();
        bundle.putString("service_id",service.getSERVICE_ID());
        bundle.putString("service_name",service.getSERVICENAME());
        bundle.putString("service_cost",service.getSERVICE_COST());
        dialogFragment.setArguments(bundle);
        dialogFragment.show(ft, "dialog");
    }

}
