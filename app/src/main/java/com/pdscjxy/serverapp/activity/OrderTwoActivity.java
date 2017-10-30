package com.pdscjxy.serverapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class OrderTwoActivity extends BaseActivity implements View.OnClickListener {
    private CustomGridView gv_one;
    private LinearLayout ll_tab;
    private TextView tv_show;
    private ImageView iv_show;

    private List<OrderBean> orders = new ArrayList<>();
    private CommonAdapter<OrderBean> adapterOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);
        setTitleLayout(ONE_LAYOUT);
        setTitleText("订单详情");

        init();
        initAdapter();
        initData();
    }

    private void initData() {
        OrderBean order;
        for (int i = 0; i <20; i++){
            order = new OrderBean();
            order.setSellerName("锐欧时尚美食"+i);
            order.setText("订单"+(i+1));
            order.setState(((i+1)/3)+1);
            orders.add(order);
        }
        adapterOrder.notifyDataSetChanged();

    }

    private void initAdapter() {
        adapterOrder = new CommonAdapter<OrderBean>(this, orders, R.layout.item_rectangle) {
            @Override
            public void convert(ViewHolder helper, OrderBean item) {
                helper.setText(R.id.tv_text, item.getText());
                switch (item.getState()){
                    case 1:
                        helper.setBackground(R.id.tv_text, getDrawable(R.drawable.rectangle_one));
                        break;
                    case 2:
                        helper.setBackground(R.id.tv_text, getDrawable(R.drawable.rectangle_two));
                        break;
                    case 3:
                        helper.setBackground(R.id.tv_text, getDrawable(R.drawable.rectangle_three));
                        break;
                }
            }
        };
        gv_one.setAdapter(adapterOrder);
        gv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OrderTwoActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.ORDER, orders.get(i));
                startCallbackActivity(intent);

            }
        });

    }

    private void init() {
        ll_tab = (LinearLayout) findViewById(R.id.ll_tab);
        gv_one = (CustomGridView) findViewById(R.id.gv_one);
        ll_tab.setOnClickListener(this);
        tv_show = (TextView) findViewById(R.id.tv_show);
        iv_show = (ImageView) findViewById(R.id.iv_show);
        tv_show.setOnClickListener(this);
        iv_show.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_tab:
                startResponseActivityFromAssignedActivity(new Intent(OrderTwoActivity.this, OrderActivity.class), OrderTwoActivity.class);
                break;
            case R.id.tv_show:
            case R.id.iv_show:
                if (gv_one.getVisibility() == View.VISIBLE){
                    gv_one.setVisibility(View.GONE);
                    tv_show.setText("展开");
                    iv_show.setImageDrawable(getDrawable(R.mipmap.ico_down));
                }else {
                    gv_one.setVisibility(View.VISIBLE);
                    tv_show.setText("收起");
                    iv_show.setImageDrawable(getDrawable(R.mipmap.ico_up));
                }

                break;
        }
    }
}
