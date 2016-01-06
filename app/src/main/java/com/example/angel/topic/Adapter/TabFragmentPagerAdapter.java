package com.example.angel.topic.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Angel on 2016/1/4.
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = null;

    public TabFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        if (fragments == null) {
            this.fragments = new ArrayList<Fragment>();
        }else{
            this.fragments = fragments;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tabName = "";
        switch (position){
            case 0:
                tabName = "我的名片";
                break;
            case 1:
                tabName = "名片盒";
                break;
            case 2:
                tabName = "搜尋";
                break;
            case 3:
                tabName = "關於我們";
                break;

        }
        return tabName;
    }



}