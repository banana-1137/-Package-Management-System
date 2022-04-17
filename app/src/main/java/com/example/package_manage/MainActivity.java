package com.example.package_manage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText user_name;
    private EditText user_password;
    Data db;
    SQLiteDatabase sDatabase = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        user_name = (EditText) findViewById(R.id.user_name);
        user_password = (EditText) findViewById(R.id.user_password);
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
    }
    
    public void login(View v)
    {
        String userName = "";
        String userPw = "";

        String i = user_name.getText().toString();
        String select_sql = "select username,password from user_info where username = '"
                + i + "'";
        Cursor cursor = sDatabase.rawQuery(select_sql, null);
        cursor.moveToFirst();
        try
        {
            userName = cursor.getString(0);
            userPw = cursor.getString(1);
        } catch (Exception e)
        {
            // TODO: handle exception
            userName = "";
            userPw = "";
        }

        if (user_name.getText().toString().equals(""))
        {
            Dialog.builder(MainActivity.this, "错误信息",
                    "用户名不能为空！");
        }
        else if (user_password.getText().toString().equals(""))
        {
            Dialog.builder(MainActivity.this, "错误信息",
                    "密码不能为空！");
        }
        else if (!(user_name.getText().toString().equals(userName) && user_password
                .getText().toString().equals(userPw)))
        {
            Dialog.builder(MainActivity.this, "错误信息",
                    "用户名或密码错误，请重新输入");
        }
        else if(user_name.getText().toString().equals("qyh"))
        {
            cursor.close();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("username",userName);
            intent.putExtras(bundle);
            intent.setClass(getApplicationContext(), Menu.class);
            startActivity(intent);

        }
        else {
            cursor.close();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("username",userName);
            intent.putExtras(bundle);
            intent.setClass(getApplicationContext(), User_ui.class);
            startActivity(intent);
        }
    }

    public void register(View v) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), Register.class);
        startActivity(intent);

    }
}
