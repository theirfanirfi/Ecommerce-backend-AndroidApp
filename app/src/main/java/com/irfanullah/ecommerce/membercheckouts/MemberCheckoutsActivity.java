package com.irfanullah.ecommerce.membercheckouts;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.irfanullah.ecommerce.Libraries.SC;
import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.R;

import java.util.ArrayList;

public class MemberCheckoutsActivity extends AppCompatActivity implements MemberCheckoutsLogic.View {
    MemberCheckoutsPresenter presenter;
    private Context context;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private MemberChecoutsAdapter memberChecoutsAdapter;
    private String MEMBER_ID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_checkouts);
        initObjects();
    }

    @Override
    public void onError(String msg) {
        SC.toastHere(context,msg);
    }

    @Override
    public void onOrdersLoaded(ArrayList<Order> orders) {
        memberChecoutsAdapter.notifyAdapter(orders);
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

    @Override
    public void onOrderShipped() {
        memberChecoutsAdapter = presenter.setUpRV(rv);
        presenter.makeOrdersRequest();
        SC.toastHere(context,"Order is shipped");
    }
    private void initObjects(){
        context = this;
        MEMBER_ID = getIntent().getExtras().getString("member_id");
        SC.logHere(MEMBER_ID+ " mem");
        presenter = new MemberCheckoutsPresenter(this,context,MEMBER_ID);
        rv = findViewById(R.id.checkoutsRV);
        progressBar = findViewById(R.id.progressBar);
        memberChecoutsAdapter = presenter.setUpRV(rv);
        presenter.makeOrdersRequest();
    }
}
