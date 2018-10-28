package com.wallmark;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ShowImageViewPagerAdapter extends FragmentPagerAdapter {

    private List<UrlDetails> myList;


    public ShowImageViewPagerAdapter(FragmentManager fm,List<UrlDetails> myList) {
        super(fm);
        this.myList = myList;

    }

    @Override
    public Fragment getItem(int i) {
        ShowImageFragment showImageFragment = new ShowImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", String.valueOf(myList.get(i).getUrl()));
        bundle.putString("id", String.valueOf(myList.get(i).getId()));
        bundle.putString("name", String.valueOf(myList.get(i).getName()));
        showImageFragment.setArguments(bundle);
        return showImageFragment;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

}
