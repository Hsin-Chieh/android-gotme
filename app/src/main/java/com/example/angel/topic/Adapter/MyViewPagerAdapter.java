package com.example.angel.topic.Adapter;

/**
 * Created by Angel on 2016/1/2.
 */
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {
    private List<View> mListViews;

    public MyViewPagerAdapter(List<View> mListViews) {
        this.mListViews = mListViews;
    }

    //實現一个PagerAdapter,必須至少覆蓋以下4點方法
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView((View) object);//删除page
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {//這個方法用來實例化page
        View view = mListViews.get(position);
        container.addView(view);//添加page
        return view;
    }

    @Override
    public int getCount() {
        return  mListViews.size();//返回page的數量
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;//官方提示這樣寫
    }
}



