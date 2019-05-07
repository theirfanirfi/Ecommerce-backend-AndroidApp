package com.irfanullah.ecommerce.main.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.irfanullah.ecommerce.main.Fragments.Categories.CategoriesFrag;
import com.irfanullah.ecommerce.main.Fragments.Products.ProductsFrag;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                CategoriesFrag categoriesFrag = new CategoriesFrag();
                return categoriesFrag;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
