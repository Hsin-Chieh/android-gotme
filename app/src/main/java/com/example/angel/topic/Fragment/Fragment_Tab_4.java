package com.example.angel.topic.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angel.topic.R;

/**
 * Created by Angel on 2016/1/5.
 */
public class Fragment_Tab_4 extends Fragment {
    TextView t;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab4, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        t = (TextView) getView().findViewById(R.id.intro);
        t.setText("2015 Android project gotme\n" +
                "打造地表最實用名片盒app\n" +
                "變成一款值得留在手機裡的app\n" +
                "Author : \n" +
                "       巫謹佑 U10216019\n" +
                "       陳安琪 U10216026\n" +
                "       吳新捷 U10116004");
    }
}
