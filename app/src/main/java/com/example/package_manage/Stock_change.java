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

public class Stock_change extends Activity {
    private ListView listview;

    String id[];
    String companies[];
    String consignee[];
    String tel[];
    String state[];
    String date[];

    Data db;
    int i = 0;
    SQLiteDatabase sDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out);
        setTitle("修改库存信息");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        listview = (ListView) findViewById(R.id.kucunlist);
        List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
        String selectStr = "select _id,com_name,name,tel,state,date  from stock";
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
            map.put("date", date[i]);
            slist.add(map);
        }
        SimpleAdapter simple = new SimpleAdapter(this, slist,
                R.layout.outadapter, new String[] { "id", "companies",
                "consignee" ,"tel","state","date"}, new int[] { R.id.t1, R.id.t2, R.id.t3,
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
                        builder.setMessage("确定要修改该库存信息吗？");
                        final int j=i;
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub


                                Intent intent=new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString("id",id[j]);
                                intent.putExtras(bundle);
                                intent.setClass(Stock_change.this, Stock_change_page.class);
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
