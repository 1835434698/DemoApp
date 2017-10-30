package com.pdscjxy.serverapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.activity.base.BaseActivity;
import com.pdscjxy.serverapp.util.Logger;

import org.lalic.base.AES;
import org.lalic.base.SA;

/**
 * Created by Administrator on 2017/10/26.
 */

public class WelcomeActivity extends BaseActivity {
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Intent intent = new Intent(WelcomeActivity.this, BannerActivity.class);
            startActivity(intent);
            finish();
        }

    };

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_welcome);
        hideTitle();
        mHandler.sendEmptyMessageDelayed(0, 1500);


        String res= AES.encryptAES("test", SA.AESK); // 加密
        Logger.d("tangzy", "encryptAES = "+res);
//        System.out.println(res);
        res=AES.decryptAES("6OGxI3XZsLf5KmWcHs5tnA==", SA.AESK); //解密
//        System.out.println(res);
        Logger.d("tangzy", "decryptAES = "+res);




    }
}
