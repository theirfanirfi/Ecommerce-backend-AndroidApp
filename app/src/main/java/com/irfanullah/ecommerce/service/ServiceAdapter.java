package com.irfanullah.ecommerce.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.Glib;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.Models.Service;
import com.irfanullah.ecommerce.R;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceView> {

    private Context context;
    private ArrayList<Service> services;
    ServiceClickListener serviceClickListener;

    public ServiceAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
    }

    @NonNull
    @Override
    public ServiceView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_row,viewGroup,false);
        return new ServiceView(view,context,serviceClickListener,this.services);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceView serviceView, int i) {
        Service service = services.get(i);
        serviceView.serviceName.setText(service.getSERVICENAME());
        serviceView.serviceCost.setText(service.getSERVICE_COST()+"$");
    }

    @Override
    public int getItemCount() {
        return this.services.size();
    }

    public static class ServiceView extends RecyclerView.ViewHolder {
        private TextView serviceName,serviceCost;

        public ServiceView(@NonNull View itemView, Context context, final ServiceClickListener serviceClickListener, final ArrayList<Service> services) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceNameTextView);
            serviceCost = itemView.findViewById(R.id.serviceCostTextView);

            serviceName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceClickListener.onServiceClickListener(getAdapterPosition(),services.get(getAdapterPosition()));
                }
            });
        }
    }

    public void notifyAdapter(ArrayList<Service> services){
        this.services = services;
        notifyDataSetChanged();
    }

    public interface ServiceClickListener {
        void onServiceClickListener(int position, Service service);
    }

    public void setOnServiceClickListener(ServiceClickListener clickListener){
        this.serviceClickListener = clickListener;
    }
}
