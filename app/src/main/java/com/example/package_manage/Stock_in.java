package com.example.package_manage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Stock_in extends Activity {
    Data db;
    SQLiteDatabase sDatabase = null;
    Spinner companies;
    EditText consignee;
    EditText tel;
    RadioGroup package_state;
    RadioButton state;
    String yes_or_no;
    String[] gs;
    String comname;
    String da;
    DatePicker date;
    int year;
    int mon;
    int day;
    Calendar c;
    String names;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        Intent inte = getIntent();
        Bundle name = inte.getExtras();
        names = inte.getStringExtra("username");
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        companies = (Spinner) findViewById(R.id.companies);
        consignee = (EditText) findViewById(R.id.consignee);
        tel = (EditText) findViewById(R.id.tel);
        package_state = (RadioGroup)findViewById(R.id.package_state);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        mon = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        da=year+"年"+(mon+1)+"月"+day+"日";
        date = (DatePicker) findViewById(R.id.date);
        date.init(year , mon ,day
                , new DatePicker.OnDateChangedListener()
                {

                    @Override
                    public void onDateChanged(DatePicker arg0, int year
                            , int month, int day)
                    {
                       Stock_in.this.year = year;
                        Stock_in.this.mon = month;
                        Stock_in.this.day = day;
                        da=year+"年"+(month+1)+"月"+day+"日";
                        System.out.println(da);

                    }
                });
        gs = getResources().getStringArray(R.array.companies);
        companies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                comname = gs[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        package_state.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                state = (RadioButton)findViewById(checkedId);
                yes_or_no = state.getText().toString();
            }
        });
    }

    public void save(View v) {
        if(consignee.getText().toString().equals("")||tel.getText().toString().equals("")){
            Dialog.builder(Stock_in.this, "提示", "请填写完整信息");

        }
        else{
            String name=consignee.getText().toString();
            String telphone=tel.getText().toString();
            int id = 0;
            String select = "select max(_id) from stock";
            Cursor seCursor = sDatabase.rawQuery(select, null);
            try {
                seCursor.moveToFirst();
                id = Integer.parseInt(seCursor.getString(0));
                id += 1;
            } catch (Exception e) {
                // TODO: handle exception
                id = 0;
            }
            sDatabase.execSQL("insert into stock values('" + id + "','"
                    + comname+ "','" + name + "','" +telphone+ "','"+yes_or_no+"','"+da+ "')");
            Toast.makeText(Stock_in.this, "添加成功", Toast.LENGTH_LONG).show();

            seCursor.close();


        }

    }

    public void back(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("username",names);
        intent.putExtras(bundle);
        intent.setClass(Stock_in.this, Menu.class);
        startActivity(intent);
    }
}
