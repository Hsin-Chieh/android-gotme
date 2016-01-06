package com.example.angel.topic.Json;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by USER-NB on 2016/1/6.
 */
public class SearchUser {
    final String SEVER_IP = "http://163.21.245.122:8787/FinalProject/SearchUser";
    String json ;
    String target;
    SharedPreferences sp ;

    public SearchUser(String target , Context context){
        this.target = target;
        sp = context.getSharedPreferences("YES" , Context.MODE_PRIVATE);
    }

    public void execute(){
        new AsyncTask<Void, Void , String>(){
            @Override
            protected String doInBackground(Void... params) {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(SEVER_IP);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setUseCaches(false);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setRequestProperty("account", target);

                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024*1024*32];

                    int length = -1;

                    while ((length = inputStream.read(bytes)) > 0) {
                        output.write(bytes , 0 , length);
                    }
                    json = new String( output.toByteArray() ,  "UTF-8");
                    inputStream.close();
                    output.flush();
                    output.close();
                    sp.edit().putString("account" , json).apply();
                    //new JSONObject(json)
                }catch (IOException e){
                    e.printStackTrace();
                }

                return json;
            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("json", s);
            }
        }.execute(null , null , null);

    }
}