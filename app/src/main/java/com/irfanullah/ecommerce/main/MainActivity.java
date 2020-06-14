package com.irfanullah.ecommerce.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.NotificationServices.NotificationService;
import com.irfanullah.ecommerce.NotificationServices.NotificationServiceLowerVesions;
import com.irfanullah.ecommerce.R;
import com.irfanullah.ecommerce.Storage.Pref;
import com.irfanullah.ecommerce.login.LoginActivity;
import com.irfanullah.ecommerce.main.Adapters.ViewPagerAdapter;
import com.irfanullah.ecommerce.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity implements MainActivityLogic.View {
    private MainActPresenter presenter;
    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private Handler handler;
    private Runnable runnable;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            i = new Intent(this, NotificationService.class);
        }else {
            i = new Intent(this, NotificationServiceLowerVesions.class);
        }

       // startService(i);

        initObjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainact_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        presenter.onMenuItemSelected(id);
        return super.onOptionsItemSelected(item);
    }

    private void initObjects(){

        context = this;
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPagerForTabLayout);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        handler = new Handler();

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        presenter = new MainActPresenter(this,context);
        presenter.setUpTabsTitle(tabLayout);
        runnable = new Runnable() {
            @Override
            public void run() {
                presenter.getCountForChatAndNotificationTabs(tabLayout);
                handler.postDelayed(this,5000);
            }
        };

        handler.postDelayed(runnable,5000);

    }

    @Override
    public void gotoProfileActivity() {
        //not added yet
        Intent profileAct = new Intent(this, ProfileActivity.class);
        startActivity(profileAct);
    }

    @Override
    public void gotoLoginActivit() {
        Intent loginAct = new Intent(context, LoginActivity.class);
        startActivity(loginAct);
        finish();
    }

    @Override
    public void showToast(String message) {
        SC.toastHere(context,message);
    }
}
