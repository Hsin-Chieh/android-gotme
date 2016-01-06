package com.example.angel.topic.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.example.angel.topic.Adapter.ItemAdapter;

import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import com.example.angel.topic.DataBase.*;

import com.example.angel.topic.DetailActivity;
import com.example.angel.topic.R;

/**
 * Created by Angel on 2016/1/4.
 */
public class Fragment_Tab_2 extends ListFragment {
    ImageView image;
    Bitmap bitmap;
    ItemDAO itemDAO;
    ItemAdapter itemAdapter;
    List<Item> items;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //想让Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法
        setHasOptionsMenu(true);
        items = new ArrayList<Item>();
        itemDAO = new ItemDAO(getActivity().getApplicationContext());

        //If no data then give a sample
        if(itemDAO.getCount() == 0){
            Item i = new Item(0,"","","","",new Date().getTime());
            itemDAO.insert(i);

        }
        items = itemDAO.getAll();

        itemAdapter = new ItemAdapter(getActivity(), R.layout.list_content, items);
        setListAdapter(itemAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.tab2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        image = (ImageView)getView().findViewById(R.id.photoA);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "你按下" + itemAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                long p =itemAdapter.getItem(position).getId();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                //new一個Bundle物件，並將要傳遞的資料傳入
                Bundle bundle = new Bundle();
                bundle.putLong("p",p );
                //將Bundle物件assign給intent
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete")
                        .setMessage("確定刪除 " + itemAdapter.getItem(position).getName() + " 的名片?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(itemAdapter.getItem(position).getId() == 1){
                                    itemDAO.update(new Item(1,"","","","",new Date().getTime()));
                                }
                                else
                                    itemDAO.delete(itemAdapter.getItem(position).getId());   //資料要記得刪除!!!!!!!!!!!!!!!!!!
                                getActivity().recreate();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        menu.add(0, 1, 0, "RESET");
        //menu.add("加入好友");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.NONE:
                return false;

            case 1:
                // Reset Activity
                getActivity().recreate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
            Toast.makeText(getActivity(), "Error occured. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
        }
    }



}