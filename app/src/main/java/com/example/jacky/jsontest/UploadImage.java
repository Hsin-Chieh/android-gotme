package com.example.jacky.jsontest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jacky on 2016/1/3.
 */
public class UploadImage {
    final String SEVER_IP = "http://192.168.1.21:8080/FinalProject/UploadImage";
    String imagePath = "";
    String filename = "";
    String phoneNumber ;

    UploadImage(String imagePath , String phoneNumber){
        this.imagePath = imagePath;
        this.phoneNumber = phoneNumber;
        filename = imagePath.substring(imagePath.lastIndexOf('/') + 1);
    }

    public void execute(){
        new AsyncTask<Void , Void , String>(){
            @Override
            protected void onPostExecute(String s) {
                Log.d("json" , s) ;
            }

            @Override
            protected String doInBackground(Void... params) {
                HttpURLConnection connection = null ;
                URL url = null;

                try {
                    url = new URL(SEVER_IP);
                    File file = new File(imagePath);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setUseCaches(false);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setRequestProperty("phone-number", phoneNumber);
                    connection.setRequestProperty("Filename", filename);
                    connection.connect();
                    Log.d("json" , imagePath);
                    DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                    byte[] buffer = new byte[1024*1024*32];
                    int bufferLength = -1 ;

                    while (  (bufferLength = fileInputStream.read(buffer)) > 0) {
                        dataOutputStream.write(buffer, 0, bufferLength);
                    }

                    dataOutputStream.flush();
                    dataOutputStream.close();
                    fileInputStream.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        return "image upload success" ;
                    }


                }catch (IOException e){
                    e.printStackTrace();
                }


                return "image upload fail";
            }
        }.execute(null , null , null);
    }
}
