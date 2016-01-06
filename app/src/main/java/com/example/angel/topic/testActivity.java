package com.example.angel.topic;

/**
 * Created by Angel on 2016/1/5.
 */



        import android.content.ContentResolver;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.media.Image;
        import android.net.Uri;
        import android.os.Environment;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.example.angel.topic.DataBase.*;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;


public class testActivity extends ActionBarActivity {

    Button save, show, clear, chooseimage;
    EditText E1, E2, E3, E4, E5;
    TextView T1, T2, T3, T4, T5;
    ImageView image;
    private ItemDAO itemDAO;
    private ListView item_list;
    private TextView show_app_name;
    Bitmap bitmap;

// 刪除原來的宣告
//private ArrayList<String> data = new ArrayList<>();
//private ArrayAdapter<String> adapter;

    //ListView使用的自定Adapter物件
    //private ItemAdapter itemAdapter;

    // 儲存所有記事本的List物件
    private List<Item> items;

    // 選單項目物件
    //private MenuItem add_item, search_item, revert_item, delete_item;
    // 已選擇項目數量
    //private int selectedCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        save = (Button) findViewById(R.id.save);
        show = (Button) findViewById(R.id.show);
        clear = (Button) findViewById(R.id.clear);
        chooseimage = (Button) findViewById(R.id.showImage);
        //save
        image = (ImageView) findViewById(R.id.imageView);
        E1 = (EditText) findViewById(R.id.editText1);//name
        E2 = (EditText) findViewById(R.id.editText2);//phone
        E3 = (EditText) findViewById(R.id.editText3);//email
        E4 = (EditText) findViewById(R.id.editText4);//note
        E5 = (EditText) findViewById(R.id.editText5);
        //show
        T1 = (TextView) findViewById(R.id.textView);
        T2 = (TextView) findViewById(R.id.textView2);
        T3 = (TextView) findViewById(R.id.textView3);
        T4 = (TextView) findViewById(R.id.textView4);
        T5 = (TextView) findViewById(R.id.textView5);


        itemDAO = new ItemDAO(getApplicationContext());
//-----------------------------------------------------------------------------------
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item i;

                //if(itemDAO.getCount() == 0){
                i = new Item(0, E1.getText().toString(), E2.getText().toString(), E3.getText().toString(),
                        E4.getText().toString(), new Date().getTime());
                //}
                /**
                 else {
                 i = new Item(itemDAO.getLastId(items), E1.getText().toString(), E2.getText().toString(), E3.getText().toString(),
                 E4.getText().toString(), new Date().getTime());
                 }*/
                itemDAO.insert(i);

                //Save Image to SD-card
                //  return bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.);
                image.buildDrawingCache();
                bitmap = image.getDrawingCache();
                saveBitmap(bitmap, "Image" + i.getId());

            }
        });
//-----------------------------------------------------------------------------------
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items = itemDAO.getAll();
                int searchID = Integer.parseInt(E5.getText().toString());
                /**
                 if(itemDAO.isExist(searchID)){ //還沒打好( 判斷searchID是不是存在於資料表中 ),暫時以這個代替
                 T1.setText("null");
                 T2.setText("null");
                 T3.setText("null");
                 T4.setText("null");
                 T5.setText("null");
                 }*/

                Item r = itemDAO.get(searchID);
                Long i = r.getId();
                T1.setText(r.getName());
                T2.setText(r.getPhone());
                T3.setText(r.getEmail());
                T4.setText(r.getNote());
                T5.setText(r.getDatatime());
                image.setImageBitmap(readBitmap("Image" + i + ".jpg"));

                //}
            }
        });
//-----------------------------------------------------------------------------------
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long I_D = Long.parseLong(E5.getText().toString());
                itemDAO.delete(I_D);

                //itemDAO.deleteAll();
            }
        });

        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //開啟Pictures畫面Type設定為image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT這個Action    //會開啟選取圖檔視窗讓您選取手機內圖檔
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //取得相片後返回本畫面
                startActivityForResult(intent, 1);

            }
        });

    }

/**
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public void onDestroy(){
        itemDAO.close();
        super.onDestroy();
    }

    public void saveBitmap(Bitmap bitmap, String name) {
        FileOutputStream fOut = null;
        Uri outputFileUri;
        try {
            File dir = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "GotMe" + File.separator);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File sdImageMainDirectory = new File(dir, name+".jpg");
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            fOut = new FileOutputStream(sdImageMainDirectory);
        } catch (Exception e) {
            Toast.makeText(this, "Error occured. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
        }
    }

    public Bitmap readBitmap(String imageName){
        Bitmap bm = null;
        String filepath = Environment.getExternalStorageDirectory()
                + File.separator + "GotMe" + File.separator+ imageName;
        File file = new File(filepath);

        if (file.exists()) {
            bm = BitmapFactory.decodeFile(filepath);
        }


        return bm;
    }



    //取得相片後返回的監聽式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //當使用者按下確定後
        if (resultCode == RESULT_OK) {
            //取得圖檔的路徑位置
            Uri uri = data.getData();
            //寫log
            Log.e("uri", uri.toString());
            //抽象資料的接口
            ContentResolver cr = this.getContentResolver();
            try {
                //由抽象資料接口轉換圖檔路徑為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //取得圖片控制項ImageView
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                // 將Bitmap設定到ImageView
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
