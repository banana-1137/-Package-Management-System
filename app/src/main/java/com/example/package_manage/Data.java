package com.example.package_manage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Data extends SQLiteOpenHelper {
    String createUser = "create table user_info(_id int,username char(20),"
            + "password char(20),primary key('_id'));";
    String createstock = "create table stock(_id int,"
            + "com_name char(40),name char(40),tel char(40),state char(40),"
            + "date char(40),primary key('_id'));";
    String createRealName = "create table RealName(_id int,name char(20),"
            +"sex char(10),idnum char(40),username char(20),primary key('_id'));";
    public Data(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUser);
        db.execSQL(createstock);
        db.execSQL(createRealName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
