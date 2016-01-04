package com.example.jacky.jsontest;

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
    final String SEVER_IP = "http://192.168.1.21:8080/FinalProject/DownloadImage";
    final String DEFAULT_PATH = "";
    String targetFilePath = "";
    String filename = "";

    DownloadImage(String targetFilePath){
        this.targetFilePath = targetFilePath;
        filename = targetFilePath.substring(targetFilePath.lastIndexOf('/') + 1);
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
                    connection.setRequestProperty("file-path", targetFilePath);
                    File file = new File(Environment.getExternalStorageDirectory() +"/" +  filename);
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
