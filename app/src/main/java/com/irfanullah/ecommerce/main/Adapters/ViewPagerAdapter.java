package com.irfanullah.ecommerce.main.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.irfanullah.ecommerce.main.Fragments.AppointmentsFrag.AppointmentsFrag;
import com.irfanullah.ecommerce.main.Fragments.Categories.CategoriesFrag;
import com.irfanullah.ecommerce.main.Fragments.Gallery.GalleryFrag;
import com.irfanullah.ecommerce.main.Fragments.MembersFrag.MembersFrag;
import com.irfanullah.ecommerce.main.Fragments.NewOrdersFrag.NOrders;
import com.irfanullah.ecommerce.main.Fragments.OlderOrdersFrag.OOrderFrag;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                GalleryFrag galleryFrag = new GalleryFrag();
                return galleryFrag;
            case 1:
                AppointmentsFrag nOrders = new AppointmentsFrag();
                return nOrders;
            case 2:
                OOrderFrag oOrderFrag = new OOrderFrag();
                return oOrderFrag;
            case 3:
                MembersFrag membersFrag = new MembersFrag();
                return membersFrag;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
