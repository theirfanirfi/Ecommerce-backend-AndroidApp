package com.irfanullah.ecommerce.service.Logic;

import android.support.v7.widget.RecyclerView;

import com.irfanullah.ecommerce.Models.Service;
import com.irfanullah.ecommerce.service.ServiceAdapter;

import java.util.ArrayList;

public interface ServiceLogic {
    interface View {
        void showMessage(String msg);
        void onServicesLoad(ArrayList<Service> services);
        void showDialog(Service service);
    }

    interface Presenter {
        ServiceAdapter setUpRecyclerView(RecyclerView rv);
        void fetchServices();

    }
}
