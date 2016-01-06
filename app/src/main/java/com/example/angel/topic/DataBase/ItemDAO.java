package com.example.angel.topic.DataBase;

/**
 * Created by Angel on 2016/1/5.
 */

        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
/**
 * Created by USER-NB on 2015/12/31.
 */
public class ItemDAO {
    public static final String TABLE_NAME = "Card";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";
    // 其它表格欄位名稱
    public static final String DATETIME_COLUMN = "datetime";
    public static final String NAME_COLUMN = "name";
    public static final String PHONE_COLUMN = "phone";
    public static final String EMAIL_COLUMN = "email";
    public static final String NOTE_COLUMN = "note";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COLUMN + " TEXT NOT NULL, " +
                    PHONE_COLUMN + " TEXT NOT NULL, " +
                    EMAIL_COLUMN + " TEXT NOT NULL, " +
                    NOTE_COLUMN + " TEXT ," +
                    DATETIME_COLUMN + " INTEGER NOT NULL )";

    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public ItemDAO(Context context) {
        db = myDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    public Item insert(Item item) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        //cv.put(KEY_ID, item.getId());
        cv.put(NAME_COLUMN, item.getName());
        cv.put(PHONE_COLUMN, item.getPhone());
        cv.put(EMAIL_COLUMN, item.getEmail());
        cv.put(NOTE_COLUMN, item.getNote());
        cv.put(DATETIME_COLUMN, item.getDatatime());

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        item.setId(id);
        // 回傳結果
        return item;
    }

    // 修改參數指定的物件
    public boolean update(Item item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(NAME_COLUMN, item.getName());
        cv.put(PHONE_COLUMN, item.getPhone());
        cv.put(EMAIL_COLUMN, item.getEmail());
        cv.put(NOTE_COLUMN, item.getNote());
        cv.put(DATETIME_COLUMN, item.getDatatime());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + item.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    public List<Item> getAll() {
        List<Item> result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public void deleteAll(){
        db.delete(TABLE_NAME, null, null);
    }

    public Item get(long id) {
        // 準備回傳結果用的物件
        Item item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }

    // 把Cursor目前的資料包裝為物件
    public Item getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Item result = new Item();

        result.setId(cursor.getLong(0));
        result.setName(cursor.getString(1));
        result.setPhone(cursor.getString(2));
        result.setEmai(cursor.getString(3));
        result.setNote(cursor.getString(4));
        result.setDatatime(cursor.getLong(5));

        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount() {
        int c = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            c = cursor.getInt(0);
        }
        return c;
    }
/**Get the id of the lastest data
 public long getLastId(List<Item> i){
 Item item = null;

 Cursor c = db.query(
 TABLE_NAME, null, null, null, null, null, null);
 c.moveToLast();
 item = getRecord(c);

 c.close();
 return item.getId();
 }*/

    // 建立範例資料
     public void sample() {
     Item item = new Item(0,"Jin-yo", "0979330696", "utaipei10216019@gmail.com", "handsome", new Date().getTime());
     Item item2 = new Item(0,"Angel", "0911951391", "u10216260@gmail.com", "ugly", new Date().getTime());
     Item item3 = new Item(0,"Ass", "0938902568", "fuckyou@gmail.com", "yo man", new Date().getTime());
     //Item item4 = new Item();

     insert(item);
     insert(item2);
     insert(item3);
     //insert(item4);
     }



}

