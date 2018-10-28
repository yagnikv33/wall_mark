package com.wallmark;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch(i){
            case 0: return new AllPhotoFragment();
            case 1: return new CategoryFragment();
            case 2: return new PopularPhotoFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "All";
            case 1: return "Category";
            case 2: return "Popular";
        }
        return super.getPageTitle(position);
    }
}
