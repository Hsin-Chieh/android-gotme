package com.example.angel.topic.DataBase;

/**
 * Created by Angel on 2016/1/5.
 */


        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.Locale;

/**
 * Created by USER-NB on 2015/12/27.
 */
public class Item {
    long id;
    private String name;
    private String phone;
    private String email;
    private String note;
    private long datatime;
    private boolean selected;
    private SQLiteDatabase db;

    public Item(){
        name ="";
        phone="";
        email="";
        note="";
    }

    public Item(long id, String name, String phone, String email, String note, long datatime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.datatime = datatime;

    }
    //-----------------------------------------------------

    public Long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getEmail(){
        return email;
    }
    public void setEmai(String email){
        this.email = email;
    }
    public String getNote(){
        return note;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getDatatime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        return sdf.format(datatime);
    }
    public void setDatatime(long datatime){
        this.datatime = datatime;
    }
}
