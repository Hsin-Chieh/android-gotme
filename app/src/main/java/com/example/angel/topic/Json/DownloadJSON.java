package com.example.angel.topic.Json;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jacky on 2016/1/3.
 */
public class DownloadJSON {
    final String SEVER_IP = "http://163.21.245.122:8787/FinalProject/DownloadJSON";
    String account ;
    String json ;
    SharedPreferences sp ;

    public DownloadJSON(String account , Context context){
        this.account = account;
        sp = context.getSharedPreferences("YES" , Context.MODE_PRIVATE);
    }

    public void  execute(){
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
                    connection.setRequestProperty("account", account);
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

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



                }catch (IOException e){
                    e.printStackTrace();
                }

                return json;
            }

            @Override
            protected void onPostExecute(String s) {
                split(s);
                Log.d("json" , s );
            }
        }.execute(null , null , null);
    }

    private void split(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            String name = jsonObject.get("name").toString();
            //String phone = jsonObject.get("phone").toString();
            //String email = jsonObject.get("email").toString();
            //String note = jsonObject.get("note").toString();
            sp.edit().putString("json" , s).apply();
            sp.edit().putString("name" , name).apply();
            Log.d("angel" ,name + " " );

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
