package com.wallmark;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
