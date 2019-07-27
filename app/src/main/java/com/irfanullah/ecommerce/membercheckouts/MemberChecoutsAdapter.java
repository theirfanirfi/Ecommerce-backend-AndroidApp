package com.irfanullah.ecommerce.membercheckouts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.R;

import java.util.ArrayList;

public class MemberChecoutsAdapter extends RecyclerView.Adapter<MemberChecoutsAdapter.OrderViewHolder> {

    public static Context context;
    public static ArrayList<Order> orders;
    public static OrderClickListener orderClickListener;
    public static ShipOrderClickListener shipOrderClickListener;

    public MemberChecoutsAdapter(Context ctx, ArrayList<Order> odrs) {
        context = ctx;
        orders = odrs;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_custom_row,viewGroup,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i) {

        Order order = orders.get(i);
        orderViewHolder.name.setText(order.getFIRSTNAME());
        orderViewHolder.address.setText("Address: "+order.getADDRESS());
        orderViewHolder.townCity.setText("Town/City: "+order.getTOWNCITY());
        orderViewHolder.price.setText("Total Price: $"+order.getTOTALPRICE());

        orderViewHolder.pOrdered.setText("Ordered Products: "+order.getQUANTITY());
        if(order.getIS_PROCESSED() == 1){
            orderViewHolder.processOrderBtn.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,townCity,pOrdered,price;
        Button processOrderBtn;
        ConstraintLayout constraintLayout;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ordererName);
            address = itemView.findViewById(R.id.address);
            townCity = itemView.findViewById(R.id.townCity);
            pOrdered = itemView.findViewById(R.id.pOrdered);
            price = itemView.findViewById(R.id.productPrice);

            processOrderBtn = itemView.findViewById(R.id.proOrderBtn);

            constraintLayout = itemView.findViewById(R.id.constraintLayout);

            clickListeners();

        }

        private void clickListeners(){
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Order order = orders.get(position);
                    orderClickListener.onOrderClick(position,order);
                }
            });

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Order order = orders.get(position);
                    orderClickListener.onOrderClick(position,order);
                }
            });

            address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Order order = orders.get(position);
                    orderClickListener.onOrderClick(position,order);
                }
            });

            pOrdered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Order order = orders.get(position);
                    orderClickListener.onOrderClick(position,order);
                }
            });

            processOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Order order = orders.get(position);
                    shipOrderClickListener.onOrderShipClicked(position,order);
                }
            });
        }
    }

    public interface OrderClickListener {
        void onOrderClick(int position, Order order);
    }
    public interface ShipOrderClickListener {
        void onOrderShipClicked(int position, Order order);
    }

    public void setOnOrderClickListener(OrderClickListener onOrderClickListener){
        orderClickListener = onOrderClickListener;
    }
    public void setOnShipOrderClickListener(ShipOrderClickListener onShipOrderClickListener){
        shipOrderClickListener = onShipOrderClickListener;
    }

    public void notifyAdapter(ArrayList<Order> or){
        orders = or;
        notifyDataSetChanged();
    }


}
