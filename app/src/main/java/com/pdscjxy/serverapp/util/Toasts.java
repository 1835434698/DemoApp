package com.pdscjxy.serverapp.util;

import android.widget.Toast;

import com.pdscjxy.serverapp.manager.Constant;


/**
 * Created by tangzy on 2016/8/10.
 */
public class Toasts {
    public static void showToastLong(String msg){
        showToast(msg, Toast.LENGTH_LONG);
    }
    public static void showToastShort(String msg){
        showToast(msg, Toast.LENGTH_SHORT);
    }
    private static void showToast(String msg, int time){
        Toast.makeText(Constant.context, msg, time).show();
    }
}
