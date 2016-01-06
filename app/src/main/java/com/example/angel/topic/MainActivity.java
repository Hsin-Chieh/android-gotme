package com.example.angel.topic;

/*import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.angel.topic.Adapter.MyViewPagerAdapter;
import com.example.angel.topic.Widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //private View view1, view2;//需要滑动的页卡
    private ViewPager mViewPager;
    private List<View> viewList;//把需要滑动的页卡添加到这个list中

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);//在frg_tab中

        final LayoutInflater mInflater = getLayoutInflater().from(this);
        View v1 = mInflater.inflate(R.layout.tab1, null);
        View v2 = mInflater.inflate(R.layout.tab2, null);
        View v3 = mInflater.inflate(R.layout.tab3, null);
        View v4 = mInflater.inflate(R.layout.tab4, null);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);
        viewList.add(v4);

        mViewPager.setAdapter(new MyViewPagerAdapter(viewList));
        mViewPager.setCurrentItem(0);//從第幾個tab開始,預設為0
        
        //slideTab.setViewPager(mViewPager) 的動作一定要在 mViewPager.setAdapter(adapter) 後面！
        // 因為 SlidingTabLayout 在做 setViewPager 的時候需要從他那邊去拿 Adapter
        SlidingTabLayout slideTab = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slideTab.setViewPager(mViewPager);

    }
}*/

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.example.angel.topic.Adapter.TabFragmentPagerAdapter;
import com.example.angel.topic.Fragment.Fragment_Tab_1;
import com.example.angel.topic.Fragment.Fragment_Tab_2;
import com.example.angel.topic.Fragment.Fragment_Tab_3;
import com.example.angel.topic.Fragment.Fragment_Tab_4;
import com.example.angel.topic.Json.GCMHandle;
import com.example.angel.topic.Json.SendJSON;
import com.example.angel.topic.Json.UploadImage;
import com.example.angel.topic.Widget.SlidingTabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    private ArrayList<Fragment> fragments;
    private TabFragmentPagerAdapter viewPager_Adapter;
    private Fragment_Tab_2 place_fragment;
    private GCMHandle gcmHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 实例化控件
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("GotMe");
        setSupportActionBar(toolbar);
        // Navigation Icon 要設定在 setSupoortActionBar 才有作用
        // 否則會出現 back button
        toolbar.setNavigationIcon(R.mipmap.logo);
        findView();
        gcmHandle = new GCMHandle(getApplication());
        gcmHandle.registerGCM();
        gcmHandle.sendMessage("Register", "account_test", "aqqqqq", "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("name" , "jacky");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new SendJSON(jsonObject , "account_test").execute();
        new UploadImage("" , "account_test").execute();




        /*slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPager_Adapter);
        // 设置SlidingTab
        slidingTabLayout.setViewPager(viewPager);

        //在Activity元件類別中取得ListFragment物件
        FragmentManager manger = getSupportFragmentManager();
        place_fragment = (Fragment_Tab_2)manger.findFragmentById(R.id.place_fragment);

        viewPager.setAdapter(viewPager_Adapter);*/
    }
    public boolean onKeyDown(int keyCode,KeyEvent event){

        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){   //確定按下退出鍵and防止重複按下退出鍵
            dialog();
        }

        return false;
    }

    private void dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //創建訊息方塊

        builder.setMessage("結束應用程式?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss(); //dismiss為關閉dialog,Activity還會保留dialog的狀態

                MainActivity.this.finish();//關閉activity

            }

        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        builder.create().show();
    }

    /*//在Activity元件中把處理回應的工作交由Fragment執行
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //如果執行確定
        if(resultCode == Activity.RESULT_OK){
            //呼叫Fragment物件的 onActivityResult方法
            place_fragment.onActivityResult(requestCode, resultCode, data);
        }
    }*/


    // 初始化控件
    private void findView() {
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        // 設置ViewPager
        fragments = new ArrayList<Fragment>();
        fragments.add(new Fragment_Tab_1());
        fragments.add(new Fragment_Tab_2());
        fragments.add(new Fragment_Tab_3());
        fragments.add(new Fragment_Tab_4());
        viewPager_Adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(viewPager_Adapter);
        // 设置SlidingTab
        slidingTabLayout.setViewPager(viewPager,caculateScreenX());
    }

    //獲取螢幕寬度
    private int caculateScreenX() {
        return getResources().getDisplayMetrics().widthPixels;
    }
}