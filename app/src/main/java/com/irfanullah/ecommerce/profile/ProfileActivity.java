package com.irfanullah.ecommerce.profile;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.R;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements ProfileLogic.View {
    private ProfilePresenter presenter;
    private EditText name,email,currentPass,newPass,service_time;
    private Button saveBtn;
    Button openingHour, closingHour;
    private Context context;
    String openingHourTime, openingHourMinutes,closingHourTime, closingHourMinutes;
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
        saveBtn = findViewById(R.id.saveChangesBtn);
        openingHour = findViewById(R.id.openingHour);
        closingHour = findViewById(R.id.closingHour);
        service_time = findViewById(R.id.time_diff);
        presenter = new ProfilePresenter(this,context);
        presenter.loadProfile();


        openingHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String stringHour = selectedHour < 10 ? "0"+Integer.toString(selectedHour): Integer.toString(selectedHour);
                        String stringMinutes = selectedMinute < 10 ? "0"+Integer.toString(selectedMinute): Integer.toString(selectedMinute);
                        String modulation = selectedHour < 12 ? "am" : "pm";
                        openingHourTime = stringHour;
                        openingHourMinutes = stringMinutes;
                        openingHour.setText( stringHour + ":" + stringMinutes+":00 "+modulation);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        closingHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String stringHour = selectedHour < 10 ? "0"+Integer.toString(selectedHour): Integer.toString(selectedHour);
                        String stringMinutes = selectedMinute < 10 ? "0"+Integer.toString(selectedMinute): Integer.toString(selectedMinute);
                        String modulation = selectedHour < 12 ? "am" : "pm";
                        closingHourTime = stringHour;
                        closingHourMinutes = stringMinutes;
                        closingHour.setText( stringHour + ":" + stringMinutes+":00 "+modulation);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validaeFields(name.getText().toString(),email.getText().toString(),currentPass.getText().toString(),newPass.getText().toString(),
                        openingHour.getText().toString(),closingHour.getText().toString(),service_time.getText().toString());
            }
        });
    }

    @Override
    public void onError(String message) {
        Log.i("internat",message);
        SC.toastHere(context,message);
    }

    @Override
    public void onProfileLoaded(User user) {
        SC.logHere(user.toString());
        name.setText(user.getUSERNAME());
        email.setText(user.getEMAIL());
        openingHour.setText(user.getOPENINGTIME());
        closingHour.setText(user.getCLOSINGTIME());
//        service_time.setText(user.getTIMEDIFF());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onUpdated() {
        SC.toastHere(context,"Profile Updated.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
