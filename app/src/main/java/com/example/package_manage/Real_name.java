package com.example.package_manage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCapture;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureConfig;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureFactory;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureResult;

public class Real_name  extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "IDCardRecognition";

    private boolean lastType = false; // false: front， true：back.
    private static final int REQUEST_CODE = 10;
    private static final String[] PERMISSIONS = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET};

    private ImageView frontImg;
    private ImageView backImg;
    private ImageView frontSimpleImg;
    private ImageView backSimpleImg;
    private ImageView frontDeleteImg;
    private ImageView backDeleteImg;
    private LinearLayout frontAddView;
    private LinearLayout backAddView;
    private TextView showResult;
    private String lastFrontResult = "";
    private String lastBackResult = "";
    private boolean isRemote = false;
    Data db;
    SQLiteDatabase sDatabase = null;
    public String name;
    public String sex;
    public String idnum;
    public String names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.idcard);
        this.initComponent();
        db = new Data(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        Intent inte = getIntent();
        names = inte.getStringExtra("username");
    }

    private void initComponent() {
        this.frontImg = this.findViewById(R.id.avatar_img);
        this.backImg = this.findViewById(R.id.emblem_img);
        this.frontSimpleImg = this.findViewById(R.id.avatar_sample_img);
        this.backSimpleImg = this.findViewById(R.id.emblem_sample_img);
        this.frontDeleteImg = this.findViewById(R.id.avatar_delete);
        this.backDeleteImg = this.findViewById(R.id.emblem_delete);
        this.frontAddView = this.findViewById(R.id.avatar_add);
        this.backAddView = this.findViewById(R.id.emblem_add);
        this.showResult = this.findViewById(R.id.show_result);
        this.frontAddView.setOnClickListener(this);
        this.backAddView.setOnClickListener(this);
        this.frontDeleteImg.setOnClickListener(this);
        this.backDeleteImg.setOnClickListener(this);
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.upload).setOnClickListener(this);
    }
    private String formatIdCardResult(MLCnIcrCaptureResult result, boolean isFront) {
        Log.i(Real_name.TAG, "formatIdCardResult");
        StringBuilder resultBuilder = new StringBuilder();
        if (isFront) {
            resultBuilder.append("Name：");
            resultBuilder.append(result.name);
            name = result.name;
//             System.out.println(name);
            resultBuilder.append("\r\n");

            resultBuilder.append("Sex：");
            resultBuilder.append(result.sex);
            sex = result.sex;
            resultBuilder.append("\r\n");



            resultBuilder.append("IDNum: ");
            resultBuilder.append(result.idNum);
            idnum = result.idNum;
            resultBuilder.append("\r\n");
            Log.i(Real_name.TAG, "front result: " + resultBuilder.toString());
        } else {

            resultBuilder.append("ValidDate: ");
            resultBuilder.append(result.validDate);
            resultBuilder.append("\r\n");
            Log.i(Real_name.TAG, "back result: " + resultBuilder.toString());
        }
        return resultBuilder.toString();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.avatar_add:
                Log.i(Real_name.TAG, "onClick avatar_img");
                this.lastType = true;
                if (!this.isGranted(Manifest.permission.CAMERA)) {
                    this.requestPermission(Real_name.PERMISSIONS,Real_name.REQUEST_CODE);
                    return;
                } else {
                    this.startCaptureActivity(this.idCallBack, this.lastType, this.isRemote);
                }
                break;
            case R.id.emblem_add:
                Log.i(Real_name.TAG, "onClick emblem_img");
                this.lastType = false;
                if (!this.isGranted(Manifest.permission.CAMERA)) {
                    this.requestPermission(Real_name.PERMISSIONS, Real_name.REQUEST_CODE);
                    return;
                } else {
                    this.startCaptureActivity(this.idCallBack, this.lastType, this.isRemote);
                }
                break;
            case R.id.avatar_delete:
                Log.i(Real_name.TAG, "onClick avatar_delete");
                this.showFrontDeleteImage();
                this.lastFrontResult = "";
                break;
            case R.id.emblem_delete:
                Log.i(Real_name.TAG, "onClick emblem_delete");
                this.showBackDeleteImage();
                this.lastBackResult = "";
                break;
            case R.id.back:
                this.finish();
                break;
            case R.id.upload:
                //System.out.println("yes");
                db = new Data(getApplicationContext(), "store.db", null, 1);
                sDatabase = db.getWritableDatabase();
               // System.out.println(name);
                if (name==null) {
                Dialog.builder(Real_name.this,"错误信息","请进行实名认证");
                System.out.println("失败");
                }
                else {
                    String selectStr = "select name from RealName where username = '" + names + "'";
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
                        if (string != "") {
                            System.out.println(string);
                            Dialog.builder(Real_name.this, "错误信息",
                                    "该用户已实名认证，请勿重复认证");
                            select_cursor.close();
                            break;

                        }
                    } while (select_cursor.moveToNext());
                    if (string == "") {
                        int idb = 0;
                        String select = "select max(_id) from RealName";
                        Cursor seCursor = sDatabase.rawQuery(select, null);
                        try {
                            seCursor.moveToFirst();
                            idb = Integer.parseInt(seCursor.getString(0));
                            idb += 1;
                        } catch (Exception e) {
                            // TODO: handle exception
                            idb = 0;
                        }
                        sDatabase.execSQL("insert into RealName values('" + idb + "','"
                                + name + "','" + sex + "','" + idnum + "','" + names + "')");
                        Toast.makeText(Real_name.this, "添加成功", Toast.LENGTH_LONG).show();

                        seCursor.close();
                    }
                }
                        break;
            default:
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != Real_name.REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(Real_name.TAG, "Camera permission granted - initialize the lensEngine");
            this.startCaptureActivity(this.idCallBack, this.lastType, this.isRemote);
            return;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(Real_name.TAG, "onConfigurationChanged");
    }

    private void startCaptureActivity(MLCnIcrCapture.CallBack callBack, boolean isFront, boolean isRemote) {
        Log.i(Real_name.TAG, "startCaptureActivity");
        MLCnIcrCaptureConfig config =
                new MLCnIcrCaptureConfig.Factory().setFront(isFront).setRemote(isRemote).create();
        MLCnIcrCapture icrCapture = MLCnIcrCaptureFactory.getInstance().getIcrCapture(config);

        icrCapture.capture(callBack, this);
    }



    private boolean isGranted(String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            int checkSelfPermission = this.checkSelfPermission(permission);
            return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
        }
    }

    private boolean requestPermission(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (!this.isGranted(permissions[0])) {
            this.requestPermissions(permissions, requestCode);
        }
        return true;
    }

    private MLCnIcrCapture.CallBack idCallBack = new MLCnIcrCapture.CallBack() {
        @Override
        public void onSuccess(MLCnIcrCaptureResult idCardResult) {
            Log.i(Real_name.TAG, "IdCallBack onRecSuccess");
            if (idCardResult == null) {
                Log.i(Real_name.TAG, "IdCallBack onRecSuccess idCardResult is null");
                return;
            }
            Bitmap bitmap = idCardResult.cardBitmap;
            if (Real_name.this.lastType) {
                Log.i(Real_name.TAG, "Front");
                Real_name.this.showFrontImage(bitmap);
                Real_name.this.lastFrontResult = Real_name.this.formatIdCardResult(idCardResult, true);
            } else {
                Log.i(Real_name.TAG, "back");
                Real_name.this.showBackImage(bitmap);
                Real_name.this.lastBackResult = Real_name.this.formatIdCardResult(idCardResult, false);
            }
            Real_name.this.showResult.setText(Real_name.this.lastFrontResult);
            Real_name.this.showResult.append(Real_name.this.lastBackResult);
        }

        @Override
        public void onCanceled() {
            Log.i(Real_name.TAG, "IdCallBack onRecCanceled");
        }

        @Override
        public void onFailure(int recCode, Bitmap bitmap) {
            Toast.makeText(Real_name.this.getApplicationContext(), "Failed to get data.", Toast.LENGTH_SHORT).show();
            Log.i(Real_name.TAG, "IdCallBack onRecFailed: " + recCode);
        }

        @Override
        public void onDenied() {
            Log.i(Real_name.TAG, "IdCallBack onCameraDenied");
        }
    };

    private void showFrontImage(Bitmap bitmap) {
        Log.i(Real_name.TAG, "showFrontImage");
        this.frontImg.setVisibility(View.VISIBLE);
        this.frontImg.setImageBitmap(bitmap);
        this.frontSimpleImg.setVisibility(View.GONE);
        this.frontAddView.setVisibility(View.GONE);
        this.frontDeleteImg.setVisibility(View.VISIBLE);
    }

    private void showBackImage(Bitmap bitmap) {
        this.backImg.setVisibility(View.VISIBLE);
        this.backImg.setImageBitmap(bitmap);
        this.backAddView.setVisibility(View.GONE);
        this.backSimpleImg.setVisibility(View.GONE);
        this.backDeleteImg.setVisibility(View.VISIBLE);
    }

    private void showFrontDeleteImage() {
        this.frontImg.setVisibility(View.GONE);
        this.frontSimpleImg.setVisibility(View.VISIBLE);
        this.frontAddView.setVisibility(View.VISIBLE);
        this.frontDeleteImg.setVisibility(View.GONE);
    }

    private void showBackDeleteImage() {
        this.backImg.setVisibility(View.GONE);
        this.backAddView.setVisibility(View.VISIBLE);
        this.backSimpleImg.setVisibility(View.VISIBLE);
        this.backDeleteImg.setVisibility(View.GONE);
    }
}
