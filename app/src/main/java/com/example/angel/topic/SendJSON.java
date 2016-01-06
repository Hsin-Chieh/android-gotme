package com.example.angel.topic;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jacky on 2016/1/3.
 */
public class SendJSON {
    private final String SEVER_IP = "http://163.21.245.122:8787/FinalProject/Synchronize";
    String targetJson = "";
    String account ;

    SendJSON(JSONObject jsonObject , String account){
        targetJson = jsonObject.toString();
        this.account = account;
        Log.d("json", targetJson);
    }

    public void execute(){
        new AsyncTask<Void , Void , String>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.d("json" , msg);
            }

            @Override
            protected String doInBackground(Void... params) {
                URL url = null ;
                HttpURLConnection connection = null;
                try {
                    url = new URL(SEVER_IP);
                    byte[] data = targetJson.getBytes();
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setUseCaches(false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setRequestProperty("account", account);
                    connection.setRequestProperty("Content-Length", String.valueOf(data.length));
                    connection.setRequestMethod("POST");

                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(data, 0, targetJson.length());
                    outputStream.flush();
                    outputStream.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        return "upload json success" ;
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
                return "upload json fail";
            }
        }.execute(null , null , null);
    }
}
