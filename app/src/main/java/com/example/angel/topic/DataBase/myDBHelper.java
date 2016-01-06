package com.example.angel.topic.DataBase;

/**
 * Created by Angel on 2016/1/5.
 */


        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
public class myDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CardData.db";
    public static final int DATABASE_VERSION = 1;//資料庫改變時改變數字, 加一
    public static SQLiteDatabase database;

    //建構子, 一般的應用不用修改
    public myDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int DATABASE_VERSION) {
        super(context, name, factory, DATABASE_VERSION);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new myDBHelper(context, DATABASE_NAME,
                    null, DATABASE_VERSION).getWritableDatabase();
        }

        return database;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemDAO.TABLE_NAME);
        onCreate(db);
    }
}
