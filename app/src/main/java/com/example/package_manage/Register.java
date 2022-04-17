package com.example.package_manage;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends Activity {
    private EditText user_name;
    private EditText user_password;
    private EditText user_passwordsure;
    Data db;
    SQLiteDatabase sDatabase = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        user_name = (EditText) findViewById(R.id.user_name);
        user_password = (EditText) findViewById(R.id.user_password);
        user_passwordsure = (EditText) findViewById(R.id.user_passwordsure);
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
    }
    public void submit(View v) {
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        if (user_name.getText().toString().equals("")
                || user_password.getText().toString().equals("")
                || user_passwordsure.getText().toString().equals("")) {

            Dialog.builder(Register.this, "错误信息", "请填写完整信息！");

        } else if (!user_password.getText().toString()
                .equals(user_passwordsure.getText().toString())) {
            Dialog.builder(Register.this, "错误信息", "两次密码输入不一致！");
        } else {
            String name = user_name.getText().toString();
            String password = user_password.getText().toString();
            String selectStr = "select username from user_info";
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
                if (string.equals(name)) {
                    Dialog.builder(Register.this, "错误信息",
                            "用户名已存在，请另设用户名");
                    select_cursor.close();
                    break;

                }
            } while (select_cursor.moveToNext());
            if (!string.equals(name)) {
                int id = 0;
                String select = "select max(_id) from user_info";
                Cursor seCursor = sDatabase.rawQuery(select, null);
                try {
                    seCursor.moveToFirst();
                    id = Integer.parseInt(seCursor.getString(0));
                    id += 1;
                } catch (Exception e) {
                    // TODO: handle exception
                    id = 0;
                }
                sDatabase.execSQL("insert into user_info values('" + id + "','"
                        + name + "','" + password + "')");
                Dialog.builder(Register.this, "提示", "注册成功，请返回登录界面登录");

                seCursor.close();


            }
        }

    }
    public void back(View v) {
        this.finish();
    }
}
