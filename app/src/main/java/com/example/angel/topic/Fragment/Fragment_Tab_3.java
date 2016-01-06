package com.example.angel.topic.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.example.angel.topic.DataBase.*;
import com.example.angel.topic.Json.DownloadImage;
import com.example.angel.topic.Json.DownloadJSON;
import com.example.angel.topic.Json.SearchUser;
import com.example.angel.topic.MainActivity;
import com.example.angel.topic.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Angel on 2016/1/5.
 */

public class Fragment_Tab_3 extends Fragment  implements View.OnClickListener {
    EditText search;
    TextView searchName;
    Button searchB, addB;
    ImageView imageView;
    Bitmap bitmap;
    String Name= "", Phone="", Email="", Note="";
    Item i;
    ItemDAO itemDAO;
    ProgressBar progressbar;
    SharedPreferences sp ;
    Handler handler ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //想让Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法
        setHasOptionsMenu(true);
        itemDAO = new ItemDAO(getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tab3, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        search = (EditText) getView().findViewById(R.id.searchText);
        searchName = (TextView) getView().findViewById(R.id.search_name);
        imageView = (ImageView) getView().findViewById(R.id.iv_search);
        searchB = (Button) getView().findViewById(R.id.btn_search);
        addB = (Button) getView().findViewById(R.id.addToList);
        searchB.setOnClickListener(this);
        addB.setOnClickListener(this);
        //progressbar = (ProgressBar)getView().findViewById(R.id.progressBar);
        sp = getActivity().getSharedPreferences("YES" , Context.MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_search:
                //progressbar.setVisibility(View.VISIBLE);
                //if(Integer.parseInt(search.toString()) == 0){
                Phone = search.getText().toString();
                new DownloadJSON(Phone                                                                                                                                      , getActivity() ).execute();
                new DownloadImage(Phone ).execute();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            while (true){
                                if(sp.getString("name" , "").isEmpty())
                                    Thread.sleep(100);

                                else{
                                    handler.sendMessage(new Message());
                                    Log.d("angel" , "send");
                                    break;
                                }

                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

                handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        //i = sample(Phone);
                        i = new Item(0,sp.getString("name" , "").toString(),sp.getString("phone" , "").toString(), sp.getString("email" , "").toString(), sp.getString("note" , "").toString(), new Date().getTime());
                        //searchName.setText(i.getName());
                        searchName.setText(i.getName());
                        if(i != null) {
                            imageView.setImageBitmap(readBitmap("temp.jpg"));
                            //progressbar.setVisibility(View.INVISIBLE);
                            addB.setVisibility(View.VISIBLE);
                        }

                        else{
                            addB.setVisibility(View.INVISIBLE);
                        }
                        super.handleMessage(msg);
                    }
                };
                break;

            case R.id.addToList:
                Name = search.getText().toString();
                i = sample(Phone);
                itemDAO.insert(i);
                imageView.buildDrawingCache();
                bitmap = imageView.getDrawingCache();
                saveBitmap(bitmap, "Image" + i.getId());
                i = null;
                search.setText("");
                getActivity().recreate();

                break;
        }
    }

    public Item sample(String n){
        Item item = null;

        if(n.equals("0979330696")){
            item = new Item(0,"Jin-yo","0979330696","U10216019@gmail.com","no",new Date().getTime());
        }
        else if(n.equals("0911951391")){
            item = new Item(0,"Angel", "0911951391", "U10216026@gmail.com", "yes",new Date().getTime());
        }
        else if(n.equals("account_test")){
            item = new Item(0,"jacky", "0900000000", "jacky@go.utaipei.edu.tw", "Test",new Date().getTime());
        }
        else
            item = new Item(0,"沒有此名片唷!", "", "", "",new Date().getTime());

        return item;
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


    public void saveBitmap(Bitmap bitmap, String name) {
        FileOutputStream fOut = null;
        Uri outputFileUri;
        try {
            File dir = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "GotMe" + File.separator);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File sdImageMainDirectory = new File(dir, name+".jpg");
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            fOut = new FileOutputStream(sdImageMainDirectory);
        } catch (Exception e) {

        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
        }
    }

}
