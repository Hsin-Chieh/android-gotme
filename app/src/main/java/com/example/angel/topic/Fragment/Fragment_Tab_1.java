package com.example.angel.topic.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angel.topic.DataBase.*;
import com.example.angel.topic.MainActivity;
import com.example.angel.topic.ProfileSettingActivity;
import com.example.angel.topic.R;

import java.io.File;
import java.util.Date;

/**
 * Created by Angel on 2016/1/4.
 */
public class Fragment_Tab_1 extends Fragment{
    Item item;
    ItemDAO itemDAO;
    ImageView  photo;
    TextView name, phone, email, note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //想让Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法
        setHasOptionsMenu(true);
        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
        //Toolbar.setOnMenuItemClickListener(onMenuItemClick);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab1, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        photo = (ImageView) getView().findViewById(R.id.photoA);
        name = (TextView) getView().findViewById(R.id.nameA);
        phone = (TextView) getView().findViewById(R.id.phoneA);
        email = (TextView) getView().findViewById(R.id.emailA);
        note = (TextView) getView().findViewById(R.id.noteA);

        itemDAO = new ItemDAO(getActivity().getApplicationContext());
        if(itemDAO.getCount() == 0){
            name.setTextColor(Color.GRAY);
            item = new Item(0, "", "", "", "", new Date().getTime());
            itemDAO.insert(item);

        }else {
            item = itemDAO.get(1);
            if(item.getName().equals("")&&item.getPhone().equals("")&&item.getEmail().equals("")&&item.getNote().equals("")){
                photo.setImageResource(R.mipmap.ic_launcher1);
                name.setTextColor(Color.GRAY);
                name.setText("您還沒有設定哦!");
                phone.setText("");
                email.setText("");
                note.setText("");
            }
            else {
                name.setTextColor(Color.BLACK);
                photo.setImageBitmap(readBitmap("Image" + item.getId() + ".jpg"));
                name.setText(item.getName());
                phone.setText(item.getPhone());
                email.setText(item.getEmail());
                note.setText(item.getNote());
            }
        }


    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0,1,0,"編輯");


        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.NONE:
                return false;

            case 1:
                // Do Fragment menu item stuff here
                changeView();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public Bitmap readBitmap(String imageName){
        Bitmap bm = null;
        String filepath = Environment.getExternalStorageDirectory()
                + File.separator + "GotMe" + File.separator+ imageName;
        File file = new File(filepath);

        if (file.exists()) {
            bm = BitmapFactory.decodeFile(filepath);
        }


        return bm;
    }



    public void  changeView(){
        startActivity(new Intent(this.getActivity(), ProfileSettingActivity.class));
        getActivity().finish();

    }

}
