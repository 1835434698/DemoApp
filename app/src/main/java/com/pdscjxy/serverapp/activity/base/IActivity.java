package com.pdscjxy.serverapp.activity.base;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface IActivity {
    public void setCustomedLayout(boolean customed);
    public Context getContext();
//    public void onActivityResult(int requestCode, int resultCode, Intent data);
    public void onActivityFirstLayout();
}
