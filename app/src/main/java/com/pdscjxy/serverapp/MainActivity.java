package com.pdscjxy.serverapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pdscjxy.serverapp.activity.OrderActivity;
import com.pdscjxy.serverapp.activity.base.BaseActivity;
import com.pdscjxy.serverapp.manager.Constant;
import com.pdscjxy.serverapp.net.OkHttpManager;
import com.pdscjxy.serverapp.util.Logger;
import com.pdscjxy.serverapp.util.Toasts;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private TextView tv_look;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideTitle();
        init();
    }

    private void init() {
        tv_look = (TextView) findViewById(R.id.tv_look);
        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCallbackActivity(new Intent(MainActivity.this, OrderActivity.class));

            }
        });
    }


    private void update( JSONObject data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        builder.setMessage("data.versionContent");
        builder.setTitle("版本更新");
        builder.setPositiveButton("更新",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
//                        if(!PrefUtils.getPrefString(MainActivity.this, "newAppVersion", "").equals(data.newAppVersion)) {
//                            PrefUtils.setPrefString(MainActivity.this, "newAppVersion", data.newAppVersion);
//                        }
                        // TODO Auto-generated method stub
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("data.appURL");
                        intent.setData(content_url);
                        startActivity(intent);
                        finish();
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                }
                return false;
            }
        });
        builder.show();
    }




    private OkHttpManager.ResponseListener listener = new OkHttpManager.ResponseListener() {
        @Override
        public void onResp(JSONObject respons, String uri) {
            Logger.d(TAG, "onResp = "+respons);
//            Logger.d(TAG, "respons = "+respons.optString("userid"));
//            stopProgressDialog();
//            JSONObject json = null;
//            try {
//                json = new JSONObject(respons);
//                Toasts.showToast(json.optString("retMessage"), Toast.LENGTH_SHORT);
//                if (json.optString("retCode").equals("000000")){
////                    MiddleView.getInstance().startCleanActivity(FirstPageAc.class, null);
//
//                }
//            } catch (JSONException e) {
//                Toasts.showToast("返回数据格式不正确", Toast.LENGTH_SHORT);
//                e.printStackTrace();
//            }
        }

        @Override
        public void onErr(String respons, String uri) {
//            stopProgressDialog();
//            Logger.d(TAG, "onErr = "+respons);
//            Toasts.showToast("网络地址错误", Toast.LENGTH_SHORT);

        }
    };
}
