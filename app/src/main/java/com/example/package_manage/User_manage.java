package com.example.package_manage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User_manage extends Activity {
    private ListView listview;
    String id[];
    String name[];
    String pass[];
    Data db;
    int i = 0;
    SQLiteDatabase sDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_manage);
        setTitle("用户管理");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        listview = (ListView) findViewById(R.id.yonghulist);
        List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
        String selectStr = "select _id,username,password  from user_info";
        Cursor cursor = sDatabase.rawQuery(selectStr, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        pass = new String[count];
        id = new String[count];
        name = new String[count];
        do {
            try {
                id[i] = cursor.getString(0);
                name[i] = cursor.getString(1);
                pass[i] = cursor.getString(2);
                i++;

            } catch (Exception e) {
                // TODO: handle exception

            }

        } while (cursor.moveToNext());

        for (int i = 0; i < id.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id[i]);
            map.put("name", name[i]);
            map.put("pass", pass[i]);
            slist.add(map);
        }
        SimpleAdapter simple = new SimpleAdapter(this, slist,
                R.layout.useradapter, new String[] { "id", "name", "pass" }, new int[] { R.id.t1, R.id.t2, R.id.t3,});
        listview.setAdapter(simple);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                for(int i=0;i<name.length;i++){
                    if(arg2==i){

                        builder.setTitle("确认消息");
                        builder.setMessage("确定要删除该用户吗？");
                        final int j=i;
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                                sDatabase.execSQL("delete from user_info where username='"+name[j]+"'");
                                Intent intent=new Intent();
                                intent.setClass(User_manage.this, User_manage.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub


                            }
                        });
                        builder.create().show();

                    }

                }

            }
        });
    }
}
