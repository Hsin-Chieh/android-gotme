package com.example.angel.topic;

        import android.app.AlertDialog;
        import android.content.ContentResolver;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.example.angel.topic.DataBase.*;
        import com.example.angel.topic.Json.SendJSON;
        import com.example.angel.topic.Json.UploadImage;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.util.Date;
        import java.io.FileNotFoundException;


/**
 * Created by Angel on 2016/1/5.
 */
public class ProfileSettingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button finish, changeImage;
    private EditText name, phone,email, note;
    private ImageView image;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);

        finish = (Button) findViewById(R.id.finish);
        changeImage = (Button) findViewById(R.id.changeImage);
        image = (ImageView) findViewById(R.id.photo);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        note = (EditText) findViewById(R.id.note);

        final ItemDAO itemDAO;
        itemDAO = new ItemDAO(getApplicationContext());
        image.setImageBitmap(readBitmap("Image1.jpg"));
        name.setText(itemDAO.get(1).getName());
        phone.setText(itemDAO.get(1).getPhone());
        email.setText(itemDAO.get(1).getEmail());
        note.setText(itemDAO.get(1).getNote());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("編輯個人名片");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.logo);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item i;
                i = new Item(1, name.getText().toString(), phone.getText().toString(),
                        email.getText().toString(), note.getText().toString(), new Date().getTime());

                itemDAO.update(i);
                image.buildDrawingCache();
                bitmap = image.getDrawingCache();
                saveBitmap(bitmap, "Image" + i.getId()); // "Image1"
                //getParent().findViewById(R.id.photoA);

                //Sent Jason
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("name", (name.getText() == null)? "" : name.getText().toString());
                    jsonObject.accumulate("phone", (phone.getText() == null)? "" : phone.getText().toString());
                    jsonObject.accumulate("email", (email.getText() == null)? "" : email.getText().toString());
                    jsonObject.accumulate("note", (note.getText() == null)? "" : note.getText().toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }

                new SendJSON(jsonObject , "account_test").execute();
                new UploadImage(Environment.getExternalStorageDirectory()+ File.separator + "GotMe" + File.separator+"Image1" , "account_test").execute();
                back();

            }
        });

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                ImageView imageView = (ImageView) findViewById(R.id.photo);
                // 將Bitmap設定到ImageView
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onKeyDown(int keyCode,KeyEvent event){

        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){   //確定按下退出鍵and防止重複按下退出鍵
            back();
        }

        return false;
    }

    private void back() {
        startActivity(new Intent(ProfileSettingActivity.this, MainActivity.class));
        finish();

    }
}