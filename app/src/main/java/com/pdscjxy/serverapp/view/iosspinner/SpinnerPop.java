package com.pdscjxy.serverapp.view.iosspinner;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.pdscjxy.serverapp.R;


/**
 * Created by dell on 2017/8/28.
 */

public class SpinnerPop {
    private Context mContext;

    public void showPopupWindow(){
        if(!popupWindow.isShowing()){
            popupWindow.showAtLocation(((Activity) mContext).getWindow().getDecorView(),
                    Gravity.BOTTOM, 0, 0);
        }
    }

    private PopupWindow popupWindow;
    public SpinnerPop(Context context, BaseSpinnerAdapter adapter, AdapterView.OnItemClickListener listener) {
        // TODO Auto-generated method stub
        // 获取自定义布局文件pop.xml的视图
        this.mContext = context;
        View popupWindow_view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.pop_spinner, null,
                false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        ListView listView = (ListView) popupWindow_view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
        listView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                return false;
            }});

        popupWindow = new PopupWindow(popupWindow_view,
                WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT, true);
        ColorDrawable dw = new ColorDrawable(-00000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOutsideTouchable(true);
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
                return false;
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
    }

    public void hidePopupWindow() {
        // TODO Auto-generated method stub
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    }
}
