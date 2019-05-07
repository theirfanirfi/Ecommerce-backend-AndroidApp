package com.irfanullah.ecommerce.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.main.MainActivity;


public class LoginActivity extends AppCompatActivity implements LoginLogic.View {

    private Context context;
    private LoginPresenter loginPresenter;
    private EditText emailField, passwordField;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initObjects();
    }

    @Override
    public void showEmptyErrorMessage() {
       // SC.snackHere(getCurrentFocus(),"None of the fields can be empty.");
       SC.toastHere(context,"None of the fields can be empty.");
    }

    @Override
    public void invalidCredentials() {
        SC.toastHere(context,"Invalid Credentials");
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void onFailure(String message) {
        SC.toastHere(context,message);
    }

    @Override
    public void onException(String message) {
        SC.toastHere(context,message);
    }

    @Override
    public void gotoMainActivity() {
        Intent mainActivity = new Intent(context, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    @Override
    public void onUserSaveError() {
        SC.toastHere(context,"Error occurred in saving the user. Please Login again.");
    }

    private void initObjects(){
        context = this;
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.loginBtn);
        loginPresenter = new LoginPresenter(this,context);

        loginPresenter.checkWhetherLoggedInOrNot();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.validateFieldsAndMakeLoginRequest(emailField.getText().toString(),passwordField.getText().toString());
            }
        });
    }
}
