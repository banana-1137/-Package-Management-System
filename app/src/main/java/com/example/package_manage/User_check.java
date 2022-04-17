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

public class User_check extends Activity {
    private ListView listview;

    String id[];
    String companies[];
    String consignee[];
    String tel[];
    String state[];
    String date[];
    String yes_or_no = "已取件";
    String names;

    Data db;
    int i = 0;
    SQLiteDatabase sDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out);
        setTitle("包裹查看");
        //System.out.println(yes_or_no);
        Intent inte = getIntent();
        names = inte.getStringExtra("username");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        listview = (ListView) findViewById(R.id.kucunlist);
        List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
        String selectStr = "select _id,com_name,name,tel,state,date  from stock where name = '"+names+"'";
        Cursor cursor = sDatabase.rawQuery(selectStr, null);

        cursor.moveToFirst();

        int count = cursor.getCount();
        id = new String[count];
        companies = new String[count];
        consignee= new String[count];
        tel= new String[count];
        state = new String[count];
        date = new String[count];

        do {
            try {
                id[i] = cursor.getString(0);
                companies[i] = cursor.getString(1);
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
            map.put("companies", companies[i]);
            map.put("consignee", consignee[i]);
            map.put("tel", tel[i]);
            map.put("state",state[i]);
            map.put("web", date[i]);
            slist.add(map);
        }
        SimpleAdapter simple = new SimpleAdapter(this, slist,
                R.layout.outadapter, new String[] { "id", "companies", "consignee" ,"tel","state","web"}, new int[] { R.id.t1, R.id.t2, R.id.t3,
                R.id.t4, R.id.t5,R.id.t6,});
        listview.setAdapter(simple);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                for(int i=0;i<companies.length;i++){
                    if(arg2==i){
                        builder.setTitle("确认消息");
                        builder.setMessage("确定要取这个货吗？");
                        final int j=i;
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                String selectStr = "select name from RealName where username = '" + names + "'";
                                Cursor select_cursor = sDatabase.rawQuery(selectStr, null);
                                select_cursor.moveToFirst();
                                String string = null;
                                do {
                                    try {
                                        string = select_cursor.getString(0);

                                    } catch (Exception e) {
                                        // TODO: handle exception
                                        string = "";
                                    }
                                    if (string == "") {
                                        System.out.println(string);
                                        Dialog.builder(User_check.this, "错误信息",
                                                "请先进行实名认证！");
                                        select_cursor.close();
                                        break;

                                    }
                                } while (select_cursor.moveToNext());
                                if(string != "") {
                                    sDatabase.execSQL("update stock set state='" + yes_or_no + "'where _id='" + id[j] + "'");
                                    Intent intent = new Intent();
                                    intent.setClass(User_check.this, User_ui.class);
                                    startActivity(intent);
                                }
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
