package com.example.angel.topic.Json;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jacky on 2015/9/22.
 */
public class GCMHandle {

    AsyncTask<Void , Void , String> asyncTask ;
    final String GCM_VALUE = "gcm register" , GOOGLE_PROJECT_ID = "579411090555" , APP_VERSION = "app_version" , KEY = "temp";
    Context context ;
    String regId ;
    GoogleCloudMessaging gcm ;
    SharedPreferences sp ;
    AtomicInteger atomicId = new AtomicInteger();


    public GCMHandle(Context context){
        this.context = context;
        gcm = GoogleCloudMessaging.getInstance(context);
    }



    public void registerGCM(){
        int curr = currVersion();
        sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        if(sp.getInt(APP_VERSION , 0)!= curr) {
            registerInBackground();
        }else
            Toast.makeText(context, "reg exist", Toast.LENGTH_LONG).show();
    }

    private int currVersion() {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private void registerInBackground() {
        new AsyncTask<Void , Void ,  String>(){
            @Override
            protected String doInBackground(Void... params) {

                if(gcm == null)
                    gcm = GoogleCloudMessaging.getInstance(context);

                try {
                    regId = gcm.register(GOOGLE_PROJECT_ID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sp = context.getSharedPreferences(KEY , Context.MODE_PRIVATE);
                sp.edit().putInt(APP_VERSION, currVersion()).apply();
                sp.edit().putString(GCM_VALUE, regId).apply();

                return regId ;
            }

            @Override
            protected void onPostExecute(String registerId) {
                Toast.makeText(context, "gcm Id  :  " + registerId, Toast.LENGTH_LONG).show();
            }
        }.execute(null , null , null);
    }

    public String getRegId(){
        sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getString(GCM_VALUE, "");
    }

    public void sendMessage(final String key , final String account , final String password , final String targetAccount){

        asyncTask = new AsyncTask<Void , Void ,  String>(){
            @Override
            protected String doInBackground(Void... params) {

                Bundle bundle = new Bundle();
                bundle.putString("Action" , key);
                bundle.putString("Account" , account);
                if(!password.isEmpty())
                    bundle.putString("Password" , password);
                if(!targetAccount.isEmpty())
                    bundle.putString("Target Account" , targetAccount);


                String id = Integer.toString(atomicId.incrementAndGet());

                try {
                    gcm.send( GOOGLE_PROJECT_ID + "@gcm.googleapis.com" , id , bundle);
                }catch (IOException ex){
                    ex.printStackTrace();
                }


                return "Send Message" ;
            }

            @Override
            protected void onPostExecute(String msg) {
                asyncTask = null ;
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        };
        asyncTask.execute(null, null , null);

    }

}
