package com.irfanullah.ecommerce.service.Logic;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Service;
import com.irfanullah.ecommerce.service.ServiceAdapter;

import java.util.ArrayList;

public interface AddServiceLogic {
    interface View {
        void showMessage(String msg);
        void onServiceAdd();
    }

    interface Presenter {
        void addService(String sName, String sCost);
    }
}
