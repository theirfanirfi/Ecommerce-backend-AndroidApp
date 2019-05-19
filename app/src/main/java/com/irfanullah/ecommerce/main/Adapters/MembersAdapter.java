package com.irfanullah.ecommerce.main.Adapters;

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
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.R;

import java.util.ArrayList;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MembersViewHolder> {

    public static Context context;
    public static ArrayList<User> users;
    public static MemberClickListener memberClickListener;

    public MembersAdapter(Context ctx, ArrayList<User> usr) {
        context = ctx;
        users = usr;
    }

    @NonNull
    @Override
    public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_custom_row,viewGroup,false);
        return new MembersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MembersViewHolder membersViewHolder, int i) {
        User user = users.get(i);
        membersViewHolder.name.setText(user.getUSERNAME());
       // membersViewHolder.spent.setText(user.getUSERNAME());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MembersViewHolder extends RecyclerView.ViewHolder {
        TextView name,spent;
        ConstraintLayout constraintLayout;

        public MembersViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
           // spent = itemView.findViewById(R.id.spentForProducts);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

            clickListeners();

        }

        private void clickListeners(){
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    User user = users.get(position);
                    memberClickListener.onMemberClick(position,user);
                }
            });

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    User user = users.get(position);
                    memberClickListener.onMemberClick(position,user);
                }
            });

        }
    }

    public interface MemberClickListener {
        void onMemberClick(int position, User user);
    }


    public void setOnMemberClickListener(MemberClickListener memberClickListenerr){
        memberClickListener = memberClickListenerr;
    }

    public void notifyAdapter(ArrayList<User> userr){
        users = userr;
        notifyDataSetChanged();
    }


}
