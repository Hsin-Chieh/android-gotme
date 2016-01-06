package com.example.angel.topic.Adapter;

/**
 * Created by Angel on 2016/1/5.
 */
import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.angel.topic.DataBase.*;
import com.example.angel.topic.R;

public class ItemAdapter extends ArrayAdapter<Item> {

    // 畫面資源編號
    private int resource;
    // 包裝的記事資料
    private List<Item> items;

    public ItemAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        // 讀取目前位置的記事物件
        final Item item = getItem(position);

        if (convertView == null) {
            // 建立項目畫面元件
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)
                    getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }
        else {
            itemView = (LinearLayout) convertView;
        }

        // 讀取記事顏色、已選擇、標題與日期時間元件
        ImageView PhotoView = (ImageView) itemView.findViewById(R.id.photo);
        TextView titleView = (TextView) itemView.findViewById(R.id.name);
        //TextView dateView = (TextView) itemView.findViewById(R.id.date_text);

        // 設定記事顏色
        //GradientDrawable background = (GradientDrawable)typeColor.getBackground();
        //background.setColor(item.getColor().parseColor());

        // 設定標題與日期時間
        if(item.getName().equals("")&&item.getPhone().equals("")&&item.getEmail().equals("")&&item.getNote().equals("")){
            PhotoView.setImageResource(R.mipmap.ic_launcher1);
            titleView.setText("您還沒有設定哦!");
            //dateView.setText("");
        }else {
            PhotoView.setImageBitmap(readBitmap("Image" + item.getId() + ".jpg"));
            titleView.setText(item.getName());
            //dateView.setText(item.getDatatime());
        }


        // 設定是否已選擇
        //selectedItem.setVisibility(item.isSelected() ? View.VISIBLE : View.INVISIBLE);

        return itemView;
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

    // 設定指定編號的記事資料
    public void set(int index, Item item) {
        if (index >= 0 && index < items.size()) {
            items.set(index, item);
            notifyDataSetChanged();
        }
    }

    // 讀取指定編號的記事資料
    public Item get(int index) {
        return items.get(index);
    }

}
