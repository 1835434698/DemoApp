package com.pdscjxy.serverapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.activity.base.BaseActivity;
import com.pdscjxy.serverapp.adapter.CommonAdapter;
import com.pdscjxy.serverapp.adapter.ViewHolder;
import com.pdscjxy.serverapp.bean.CardBean;
import com.pdscjxy.serverapp.bean.OrderBean;
import com.pdscjxy.serverapp.bean.RoomBean;
import com.pdscjxy.serverapp.util.Logger;
import com.pdscjxy.serverapp.view.CustomGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangzy on 17/10/28.
 */

public class OrderSureActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "OrderActivity";
    private EditText et_message;
    private TextView tv_minus, tv_voucher, tv_add, tv_sure, tv_cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersure);
        setTitleLayout(ONE_LAYOUT);
        setTitleText("订单1详情");

        init();
        initData();
    }

    private void initData() {

    }


    private void init() {
        et_message = (EditText) findViewById(R.id.et_message);
        tv_minus = (TextView) findViewById(R.id.tv_minus);
        tv_voucher = (TextView) findViewById(R.id.tv_voucher);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_add.setOnClickListener(this);
        tv_minus.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add:
                countVoucher(true);
//                startResponseActivityFromAssignedActivity(new Intent(OrderSureActivity.this, OrderTwoActivity.class), OrderSureActivity.class);
                break;
            case R.id.tv_minus:
                countVoucher(false);
                break;
            case R.id.tv_sure:

                break;
            case R.id.tv_cancel:

                break;
        }
    }

    private void countVoucher(final boolean b) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int count =Integer.parseInt(tv_voucher.getText().toString());
                if (b){
                    if (count< 5 ){
                        try {
                            tv_voucher.setText(String.valueOf(count+1));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }else {
                    if (count> 0 ){
                        tv_voucher.setText(String.valueOf(count-1));
                    }

                }
            }
        });

    }
}
