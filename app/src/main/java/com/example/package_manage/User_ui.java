package com.example.package_manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;

public class User_ui extends AppCompatActivity {
    String names = null;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.user_ui, tabHost.getTabContentView());
        inflater.inflate(R.layout.me, tabHost.getTabContentView());
        inflater.inflate(R.layout.app_about, tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("user_ui").setIndicator("包裹查看").setContent(R.id.user_manage));
        tabHost.addTab(tabHost.newTabSpec("me").setIndicator("我的").setContent(R.id.me));
        tabHost.addTab(tabHost.newTabSpec("app_about").setIndicator("关于").setContent(R.id.app_about));
        Bundle name = getIntent().getExtras();
        names = name.getString("username");
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
        intent.setClass(User_ui.this, MainActivity.class);
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
    public void real_name(View v){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("username", names);
        intent.putExtras(bundle);
        intent.setClass(getApplicationContext(), Real_name.class);
        startActivity(intent);
    }
}
