package com.pdscjxy.serverapp;

import android.app.Application;

import com.pdscjxy.serverapp.manager.Constant;

/**
 * Created by Administrator on 2017/10/26.
 */

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Constant.app = this;
    }
}
