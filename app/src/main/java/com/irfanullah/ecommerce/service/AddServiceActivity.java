package com.irfanullah.ecommerce.service;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.service.Logic.AddServiceLogic;
import com.irfanullah.ecommerce.service.Logic.AddServicePresenter;

public class AddServiceActivity extends AppCompatActivity implements AddServiceLogic.View {

    private Context context;
    private AddServicePresenter presenter;
    private EditText serviceName,serviceCost;
    private Button addServiceBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        initObjects();
    }

    private void initObjects() {
        context = this;
        presenter = new AddServicePresenter(context,this);
        serviceName = findViewById(R.id.service_name);
        serviceCost = findViewById(R.id.service_cost);
        addServiceBtn = findViewById(R.id.addServiceBtn);

        addServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addService(serviceName.getText().toString(),serviceCost.getText().toString());
            }
        });

    }

    @Override
    public void showMessage(String msg) {
        SC.toastHere(context,msg);
    }

    @Override
    public void onServiceAdd() {
        serviceName.setText("");
        serviceCost.setText("");
    }
}
