package com.example.package_manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    String names = null;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
            setContentView(R.layout.menu);
                TabHost tabHost = findViewById(android.R.id.tabhost);
                tabHost.setup();
                LayoutInflater inflater = LayoutInflater.from(this);
                inflater.inflate(R.layout.stock_manage, tabHost.getTabContentView());
                inflater.inflate(R.layout.user_manage_tab, tabHost.getTabContentView());
                inflater.inflate(R.layout.me, tabHost.getTabContentView());
                inflater.inflate(R.layout.app_about, tabHost.getTabContentView());
                tabHost.addTab(tabHost.newTabSpec("stock_manage").setIndicator("库存管理").setContent(R.id.stock_manage));
                tabHost.addTab(tabHost.newTabSpec("user_manage_tab").setIndicator("用户管理").setContent(R.id.user_manage));
                tabHost.addTab(tabHost.newTabSpec("me").setIndicator("我的").setContent(R.id.me));
                tabHost.addTab(tabHost.newTabSpec("app_about").setIndicator("关于").setContent(R.id.app_about));
                Bundle name = getIntent().getExtras();
                names = name.getString("username");
    }
        public void add (View v){

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("username", names);
            intent.putExtras(bundle);
            intent.setClass(getApplicationContext(), Stock_in.class);
            startActivity(intent);

        }
        public void check (View v){


            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("username", names);
            intent.putExtras(bundle);
            intent.setClass(getApplicationContext(), Stock_check.class);
            startActivity(intent);

        }

        public void change_pass (View v){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("username", names);
            intent.putExtras(bundle);
            intent.setClass(getApplicationContext(), Change_password.class);
            startActivity(intent);
        }
        public void manage (View v){

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("username", names);
            intent.putExtras(bundle);
            intent.setClass(getApplicationContext(), User_manage.class);
            startActivity(intent);
        }
        public void out (View v){
            Intent intent = new Intent();
            intent.setClass(Menu.this, Stock_out.class);
            startActivity(intent);
        }
        public void change (View v){
            Intent intent = new Intent();
            intent.setClass(Menu.this, Stock_change.class);
            startActivity(intent);
        }
        public void search (View v){
        Intent intent = new Intent();
        intent.setClass(Menu.this, Stock_search.class);
        startActivity(intent);
        }
        public void user_check(View v){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("username", names);
            intent.putExtras(bundle);
            intent.setClass(getApplicationContext(), User_check.class);
            startActivity(intent);

        }
        public void logout(View v){
            Intent intent = new Intent();
            intent.setClass(Menu.this, MainActivity.class);
            startActivity(intent);
        }
    public void real_name(View v){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("username", names);
        intent.putExtras(bundle);
        intent.setClass(getApplicationContext(), Real_name.class);
        startActivity(intent);
    }
    public void name_check(View v){
        Intent intent = new Intent();
        intent.setClass(Menu.this, Name_check.class);
        startActivity(intent);
    }
    }
