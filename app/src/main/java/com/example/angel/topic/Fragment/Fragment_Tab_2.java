package com.example.angel.topic.Fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.angel.topic.Adapter.ItemAdapter;
import android.widget.ListView;;
import android.widget.Toast;
import java.util.*;
import com.example.angel.topic.DataBase.ItemDAO;
import com.example.angel.topic.DataBase.Item;
import com.example.angel.topic.R;

/**
 * Created by Angel on 2016/1/4.
 */
public class Fragment_Tab_2 extends ListFragment {
    //String[] arr = new String[]{"aa", "bb", "cc"};

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        List<Item> items;
        items = new ArrayList<Item>();
        ItemDAO itemDAO = new ItemDAO(getActivity().getApplicationContext());
      /*
        Item i;
        i = new Item(0,"Jin-yo", "0979330696", "utaipei10216019@gmail.com", "handsome", new Date().getTime());
        itemDAO.insert(i);*/
        items = itemDAO.getAll();

        //sample
        //items.add(new Item(0,"angel", "0911951391", "u10216026@gmail.com", "ugly", new Date().getTime()));
        //items.add(new Item(0,"Jin-yo", "0979330696", "utaipei10216019@gmail.com", "handsome", new Date().getTime()));

        if(itemDAO.getCount() == 0){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,new String[]{"No data"});
            setListAdapter(adapter);
        }
        else {
            ItemAdapter itemAdapter = new ItemAdapter(getActivity(), R.layout.list_content, items);
            setListAdapter(itemAdapter);
        }


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        menu.add("加入好友");
        //menu.add("");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.tab2, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), "你按下", Toast.LENGTH_SHORT).show();
    }
}
/*public class Fragment_Tab_2 extends ListFragment {
    private String TAG = Fragment_Tab_2.class.getName();// 方便debug
    //儲存這個fragment的Activity元件
    private Activity parent;

    //加入Activity元件
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "----------onAttach");
        //設定使用這個fragment的Activity元件
        parent = activity;
    }

    //建立fragment畫面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //載入與回傳指定的畫面配置資源
        return inflater.inflate(R.layout.tab2, container, false);
    }

    //Activity元件已建立
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "--------onActivityCreated");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        //取得載入選單用的MenuInflater物件
        MenuInflater menuInflater = parent.getMenuInflater();
        //呼叫inflate方法載入指定的選單資源，第二個參數是這個方法的Menu物件
        //menuInflater.inflate(R.menu.menu_fragment03_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intentUpdate = new Intent(parent, UpdateActivity.class);
        return true;
    }
}*/