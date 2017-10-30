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

public class OrderActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "OrderActivity";
    private CustomGridView gv_one, gv_two;
    private LinearLayout ll_tab;
    private TextView tv_show_1, tv_show_2;
    private ImageView iv_show_1, iv_show_2;

    private List<RoomBean> rooms = new ArrayList<>();
    private List<CardBean> cards = new ArrayList<>();
    private CommonAdapter<RoomBean> adapterRoom;
    private CommonAdapter<CardBean> adapterCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitleLayout(ONE_LAYOUT);
        setTitleText("订单详情");

        init();
        initAdapter();
        initData();
    }

    private void initData() {
        RoomBean room;
        for (int i = 0; i <20; i++){
            room = new RoomBean();
            room.setText("包"+(i+1));
            room.setState(((i+1)%3)+1);
            OrderBean orderBean = new OrderBean();
            orderBean.setSellerName("锐欧时尚美食"+i);
            orderBean.setText("订单"+(i+1));
            orderBean.setState(((i+1)/3)+1);
            room.setOrderBean(orderBean);
            rooms.add(room);
        }
        CardBean card;
        for (int i = 0; i <20; i++){
            card = new CardBean();
            card.setText("卡"+(i+1));
            card.setState(((i+1)%3)+1);
            OrderBean orderBean = new OrderBean();
            orderBean.setSellerName("锐欧时尚美食"+i);
            orderBean.setText("订单"+(i+1));
            orderBean.setState(((i+1)/3)+1);
            card.setOrderBean(orderBean);
            cards.add(card);
        }
        adapterRoom.notifyDataSetChanged();
        adapterCard.notifyDataSetChanged();

    }

    private void initAdapter() {
        adapterRoom = new CommonAdapter<RoomBean>(this, rooms, R.layout.item_rectangle) {
            @Override
            public void convert(ViewHolder helper, RoomBean item) {
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
        gv_one.setAdapter(adapterRoom);
        gv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OrderActivity.this, DetailActivity.class);
                Logger.d(TAG, "onItemClick i = "+i);
                intent.putExtra(DetailActivity.ORDER, rooms.get(i).getOrderBean());
                startCallbackActivity(intent);
            }
        });
        adapterCard = new CommonAdapter<CardBean>(this, cards, R.layout.item_rectangle) {
            @Override
            public void convert(ViewHolder helper, CardBean item) {
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
        gv_two.setAdapter(adapterCard);
        gv_two.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OrderActivity.this, DetailActivity.class);
                Logger.d(TAG, "onItemClick i = "+i);
                intent.putExtra(DetailActivity.ORDER, cards.get(i).getOrderBean());
                startCallbackActivity(intent);
            }
        });

    }

    private void init() {
        ll_tab = (LinearLayout) findViewById(R.id.ll_tab);
        gv_one = (CustomGridView) findViewById(R.id.gv_one);
        gv_two = (CustomGridView) findViewById(R.id.gv_two);
        ll_tab.setOnClickListener(this);

        tv_show_1 = (TextView) findViewById(R.id.tv_show_1);
        tv_show_2 = (TextView) findViewById(R.id.tv_show_2);
        iv_show_1 = (ImageView) findViewById(R.id.iv_show_1);
        iv_show_2 = (ImageView) findViewById(R.id.iv_show_2);
        tv_show_1.setOnClickListener(this);
        tv_show_2.setOnClickListener(this);
        iv_show_1.setOnClickListener(this);
        iv_show_2.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_tab:
                startResponseActivityFromAssignedActivity(new Intent(OrderActivity.this, OrderTwoActivity.class), OrderActivity.class);
                break;
            case R.id.tv_show_1:
            case R.id.iv_show_1:
                if (gv_one.getVisibility() == View.VISIBLE){
                    gv_one.setVisibility(View.GONE);
                    tv_show_1.setText("展开");
                    iv_show_1.setImageDrawable(getDrawable(R.mipmap.ico_down));
                }else {
                    tv_show_1.setText("收起");
                    gv_one.setVisibility(View.VISIBLE);
                    iv_show_1.setImageDrawable(getDrawable(R.mipmap.ico_up));
                }

                break;
            case R.id.tv_show_2:
            case R.id.iv_show_2:
                if (gv_two.getVisibility() == View.VISIBLE){
                    gv_two.setVisibility(View.GONE);
                    tv_show_2.setText("展开");
                    iv_show_2.setImageDrawable(getDrawable(R.mipmap.ico_down));
                }else {
                    gv_two.setVisibility(View.VISIBLE);
                    tv_show_2.setText("收起");
                    iv_show_2.setImageDrawable(getDrawable(R.mipmap.ico_up));
                }

                break;
        }
    }
}
