package com.example.package_manage;

import android.app.AlertDialog;
import android.content.Context;

public class Dialog {
    public static void builder(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("È·¶¨", null);
        builder.create();
        builder.show();
    }
}
