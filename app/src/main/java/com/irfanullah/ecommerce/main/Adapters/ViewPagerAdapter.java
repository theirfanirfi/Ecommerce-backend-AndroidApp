package com.irfanullah.ecommerce.main.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.irfanullah.ecommerce.main.Fragments.AppointmentsFrag.AppointmentsFrag;
import com.irfanullah.ecommerce.main.Fragments.Categories.CategoriesFrag;
import com.irfanullah.ecommerce.main.Fragments.Chat.ChatFragment;
import com.irfanullah.ecommerce.main.Fragments.Gallery.GalleryFrag;
import com.irfanullah.ecommerce.main.Fragments.MembersFrag.MembersFrag;
import com.irfanullah.ecommerce.main.Fragments.NotificationsFrag.NotificationsFrag;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){

            case 0:
                NotificationsFrag notificationsFrag = new NotificationsFrag();
                return notificationsFrag;
            case 1:
                GalleryFrag galleryFrag = new GalleryFrag();
                return galleryFrag;
            case 2:
                AppointmentsFrag nOrders = new AppointmentsFrag();
                return nOrders;
            case 3:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            case 4:
                MembersFrag membersFrag = new MembersFrag();
                return membersFrag;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
