package com.example.angel.topic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angel.topic.DataBase.*;

import java.io.File;


/**
 * Created by Angel on 2016/1/6.
 */
public class DetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView name, phone, email, note;
    private ImageView image;
    Item item;
    ItemDAO itemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        long p = 0;
        Bundle bundle =this.getIntent().getExtras();
        p = bundle.getLong("p");

        itemDAO = new ItemDAO(getApplicationContext());
        item = itemDAO.get(p);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(p == 1)
            toolbar.setTitle("我的名片");
        else
            toolbar.setTitle(item.getName() + " 的名片");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.logo);
        image = (ImageView)findViewById(R.id.photoA);
        name = (TextView) findViewById(R.id.detail_name);
        phone = (TextView) findViewById(R.id.detail_phone);
        email = (TextView) findViewById(R.id.detail_email);
        note = (TextView) findViewById(R.id.detail_note);

        image.setImageBitmap(readBitmap("Image" + item.getId() +".jpg"));
        name.setText(item.getName());
        phone.setText(item.getPhone());
        email.setText(item.getEmail());
        note.setText(item.getNote());

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
}