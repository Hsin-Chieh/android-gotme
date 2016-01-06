package com.example.angel.topic.Json;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jacky on 2016/1/3.
 */
public class DownloadImage {
    final String SEVER_IP = "http://163.21.245.122:8787/FinalProject/DownloadImage";
    String DEFAULT_PATH = Environment.getExternalStorageDirectory() + File.separator + "GotMe" + File.separator;
    String account = "";
    String filename = "temp.jpg";

    public DownloadImage(String account){
        this.account = account;
    }

    public void execute(){
        new AsyncTask<Void , Void , String>(){
            @Override
            protected void onPostExecute(String s) {
                Log.d("json" , s);
            }

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
                    connection.setRequestProperty("account", account);
                    File file = new File(DEFAULT_PATH  +  filename);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    InputStream inputStream = connection.getInputStream();

                    byte[] bytes = new byte[1024*1024*32];

                    int length = -1;

                    while ((length = inputStream.read(bytes)) > 0) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    inputStream.close();

                }catch (IOException e){
                    e.printStackTrace();
                }

                return "image download finish";
            }
        }.execute(null , null , null);
    }
}
