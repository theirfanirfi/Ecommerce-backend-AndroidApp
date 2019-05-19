package com.irfanullah.ecommerce.profile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.R;

public class ProfileActivity extends AppCompatActivity implements ProfileLogic.View{
    private ProfilePresenter presenter;
    private EditText name,email,currentPass,newPass,shipmentDuration;
    private Button saveBtn;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initObject();
    }

    private void initObject() {
        context = this;
        name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        currentPass = findViewById(R.id.cpass);
        newPass = findViewById(R.id.npass);
        shipmentDuration = findViewById(R.id.orderShipmentDuration);
        presenter = new ProfilePresenter(this,context);
        presenter.loadProfile();
    }

    @Override
    public void onError(String message) {
        SC.toastHere(context,message);
    }

    @Override
    public void onProfileLoaded(User user) {
        name.setText(user.getUSERNAME());
        email.setText(user.getEMAIL());
        shipmentDuration.setText(user.getSHIPMENTDURATION());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onUpdated() {
    }
}
