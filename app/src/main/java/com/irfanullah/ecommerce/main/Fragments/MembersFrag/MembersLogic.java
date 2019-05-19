package com.irfanullah.ecommerce.main.Fragments.MembersFrag;

import android.support.v7.widget.RecyclerView;
import com.irfanullah.ecommerce.Models.User;
import com.irfanullah.ecommerce.main.Adapters.MembersAdapter;

import java.util.ArrayList;

public interface MembersLogic {
    interface View {
        void onMembersLoaded(ArrayList<User> users);
        void hideProgressBar();
        void showProgressBar();
        void onError(String message);
    }

    interface Presenter {
        void makeGetMembersRequest();
        MembersAdapter setUpRecyclerView(RecyclerView rv);
    }
}
