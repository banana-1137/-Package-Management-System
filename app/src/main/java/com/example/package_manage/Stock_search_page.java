package com.example.package_manage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stock_search_page extends Activity {
    private ListView listview;

    String id[];
    String componies[];
    String consignee[];
    String tel[];
    String state[];
    String date[];
    String names;

    Data db;
    int i = 0;
    SQLiteDatabase sDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check);
        setTitle("查看库存信息");
        Intent inte = getIntent();
        names = inte.getStringExtra("name");
        System.out.println(names);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        listview = (ListView) findViewById(R.id.userlist);
        List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
        String selectStr = "select _id,com_name,name,tel,state,date  from stock where name = '"+names+"'";
        Cursor cursor = sDatabase.rawQuery(selectStr, null);

        cursor.moveToFirst();

        int count = cursor.getCount();
        id = new String[count];
        componies = new String[count];
        consignee= new String[count];
        tel= new String[count];
        state = new String[count];
        date = new String[count];

        do {
            try {
                id[i] = cursor.getString(0);
                componies[i] = cursor.getString(1);
                consignee[i] = cursor.getString(2);
                tel[i] = cursor.getString(3);
                state[i]=cursor.getString(4);
                date[i] = cursor.getString(5);
                i++;
            } catch (Exception e) {
                // TODO: handle exception

            }

        } while (cursor.moveToNext());

        for (int i = 0; i < id.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id[i]);
            map.put("companies", componies[i]);
            map.put("consignee", consignee[i]);
            map.put("tel", tel[i]);
            map.put("state",state[i]);
            map.put("web", date[i]);
            slist.add(map);
        }
        SimpleAdapter simple = new SimpleAdapter(this, slist,
                R.layout.check_adapter, new String[] { "id", "companies", "consignee" ,"tel","state","web"}, new int[] { R.id.t1, R.id.t2, R.id.t3,
                R.id.t4, R.id.t5,R.id.t6,});
        listview.setAdapter(simple);
    }
}
