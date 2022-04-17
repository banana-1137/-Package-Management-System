package com.example.package_manage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Stock_search extends Activity {
    private EditText name;
    String names;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        name = (EditText) findViewById(R.id.search_name);
    }
    public void search_stock (View v){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        names = name.getText().toString();
        bundle.putString("name", names);
        //System.out.println(names);
        intent.putExtras(bundle);
        intent.setClass(getApplicationContext(), Stock_search_page.class);
        startActivity(intent);
    }
    public void back(View v){
        this.finish();
    }
}
