package com.example.package_manage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Change_password extends Activity {
    private EditText old;
    private EditText new_;
    private EditText new_sure;
    String names;
    Data db;
    SQLiteDatabase sDatabase = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass);
        setTitle("修改密码");
        Intent inte = getIntent();
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        names = inte.getStringExtra("username");
        new_ = (EditText) findViewById(R.id.new_);
        old = (EditText) findViewById(R.id.old);
        new_sure=(EditText) findViewById(R.id.new_sure);


    }

    public void save(View v) {
        String selectStr = "select password from user_info where username='"+names+"'";
        Cursor cursor = sDatabase.rawQuery(selectStr, null);
        cursor.moveToFirst();
        String pass=null;
        String oldpass=old.getText().toString();
        String newpass=new_.getText().toString();
        String newpasssure=new_sure.getText().toString();
        if(oldpass.equals("")||newpass.equals("")||newpasssure.equals("")){
            Dialog.builder(Change_password.this , "提示", "请填写完整信息");
        }
        else if (!new_.getText().toString()
                .equals(new_sure.getText().toString())) {
            Dialog.builder(Change_password.this, "错误信息", "两次密码输入不一致！");}
        else{
            do {
                try {
                    pass= cursor.getString(0);
                } catch (Exception e) {
                    // TODO: handle exception
                    pass="";
                }
                if (!oldpass.equals(pass)) {
                    Dialog.builder(Change_password.this, "错误信息","原始密码错误");
                    cursor.close();
                    break;

                }

            } while (cursor.moveToNext());
            if (oldpass.equals(pass)) {
                sDatabase.execSQL("update user_info set password='"+newpass+"' where username='"+names+"'");
                Toast.makeText(Change_password.this, "修改成功", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void back(View v) {
        this.finish();

    }
}
