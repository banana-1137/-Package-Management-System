package com.example.package_manage;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Name_check extends Activity {
    private ListView listview;

    String id[];
    String name[];
    String sex[];
    String idnum[];
    String names[];

    Data db;
    int i = 0;
    SQLiteDatabase sDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_check);
        setTitle("查看实名信息");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        listview = (ListView) findViewById(R.id.name_list);
        List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
        String selectStr = "select _id,name,sex,idnum,username  from RealName";
        Cursor cursor = sDatabase.rawQuery(selectStr, null);

        cursor.moveToFirst();

        int count = cursor.getCount();
        id = new String[count];
        name = new String[count];
        sex= new String[count];
        idnum= new String[count];
        names = new String[count];

        do {
            try {
                id[i] = cursor.getString(0);
                name[i] = cursor.getString(1);
                sex[i] = cursor.getString(2);
                idnum[i] = cursor.getString(3);
                names[i]=cursor.getString(4);
                i++;
            } catch (Exception e) {
                // TODO: handle exception

            }

        } while (cursor.moveToNext());

        for (int i = 0; i < id.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id[i]);
            map.put("name", name[i]);
            map.put("sex", sex[i]);
            map.put("idnum", idnum[i]);
            map.put("names",names[i]);
            slist.add(map);
        }
        SimpleAdapter simple = new SimpleAdapter(this, slist,
                R.layout.name_check_adapter, new String[] { "id", "name", "sex" ,"idnum","names"}, new int[] { R.id.t1, R.id.t2, R.id.t3,
                R.id.t4, R.id.t5,});
        listview.setAdapter(simple);
    }

}
