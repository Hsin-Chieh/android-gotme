package com.example.angel.topic;

/**
 * Created by Angel on 2016/1/1.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.angel.topic.Adapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends Activity {

    private ViewPager mViewPager;
    private List<View> viewList;
    private RadioGroup mRadio;
    private RadioButton radioButton1, radioButton2, radioButton3;
    private boolean Scrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 消去標題列
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全螢幕
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mRadio = (RadioGroup)findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton)findViewById(R.id.radioButton);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton1);
        radioButton3 = (RadioButton)findViewById(R.id.radioButton2);
        radioButton1.setEnabled(false);
        radioButton2.setEnabled(false);
        radioButton3.setEnabled(false);

        final LayoutInflater mInflater = getLayoutInflater().from(this);
        View v1 = mInflater.inflate(R.layout.intro_tab1, null);
        View v2 = mInflater.inflate(R.layout.intro_tab2, null);
        View v3 = mInflater.inflate(R.layout.intro_tab3, null);

        viewList = new ArrayList<View>();
        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);

        mViewPager.setAdapter(new MyViewPagerAdapter(viewList));
        /*mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, MainActivity.class));
                finish();
            }
        });*/
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRadio.check(R.id.radioButton);
                        break;
                    case 1:
                        mRadio.check(R.id.radioButton1);
                        radioButton1.setChecked(false);
                        break;
                    case 2:
                        mRadio.check(R.id.radioButton2);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING://正在滑動
                        Scrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING://滑動完自動拖移至要去的頁面
                        Scrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE://什麼事都沒做
                        if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && Scrolled) {
                            startActivity(new Intent(AboutActivity.this, MainActivity.class));
                            finish();

                        }
                        Scrolled = false;
                        break;
                }
            }
        });

    }
}
