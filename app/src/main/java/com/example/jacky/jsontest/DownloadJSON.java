package com.example.jacky.jsontest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jacky on 2016/1/3.
 */
public class DownloadJSON {
    final String SEVER_IP = "http://192.168.1.21:8080/FinalProject/DownloadJSON";
    String phoneNumber ;
    String json ;

    DownloadJSON(String phoneNumber){
        this.phoneNumber = phoneNumber;
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
                    connection.setRequestProperty("phone-number", phoneNumber);
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
                Log.d("json" , s );
            }
        }.execute(null , null , null);
    }
}
