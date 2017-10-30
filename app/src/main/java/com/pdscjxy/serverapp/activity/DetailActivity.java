package com.pdscjxy.serverapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.activity.base.BaseActivity;
import com.pdscjxy.serverapp.adapter.CommonAdapter;
import com.pdscjxy.serverapp.adapter.ViewHolder;
import com.pdscjxy.serverapp.bean.DishBean;
import com.pdscjxy.serverapp.bean.OrderBean;
import com.pdscjxy.serverapp.util.Logger;
import com.pdscjxy.serverapp.view.CustomListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangzy on 17/10/28.
 */

public class DetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "DetailActivity";
    public static String ORDER = "order";
    private TextView tv_sure;
    private OrderBean orderBean;
    private CustomListView lv_hotdish, lv_cooldish, lv_noodle;
    private CommonAdapter<DishBean> hotAdapter, coolAdapter, noodleAdapter;
    private List<DishBean> hots= new ArrayList<>();
    private List<DishBean> cools = new ArrayList<>();
    private List<DishBean> noodles = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitleLayout(ONE_LAYOUT);
        orderBean = (OrderBean) getIntent().getSerializableExtra(ORDER);
        if (orderBean == null) finish();
        setTitleText(orderBean.getSellerName());

        init();
        initAdapter();

        initData();

    }

    private void initData() {
        DishBean bean;
        for (int i = 0; i < 15; i++){
            bean = new DishBean();
            bean.setNo((i+1)+"");
            bean.setName("东北菜"+i);
            bean.setNumber(1);
            bean.setPrice(23);
            switch (i){
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    bean.setType(1);
                    hots.add(bean);
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    bean.setType(2);
                    cools.add(bean);
                    break;
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    bean.setType(3);
                    noodles.add(bean);
                    break;
            }
        }
        hotAdapter.notifyDataSetChanged();
        coolAdapter.notifyDataSetChanged();
        noodleAdapter.notifyDataSetChanged();
    }

    private void init() {
        lv_hotdish = (CustomListView) findViewById(R.id.lv_hotdish);
        lv_cooldish = (CustomListView) findViewById(R.id.lv_cooldish);
        lv_noodle = (CustomListView) findViewById(R.id.lv_noodle);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);

    }

    private void initAdapter() {
        hotAdapter = new CommonAdapter<DishBean>(this, hots, R.layout.item_detail) {
            @Override
            public void convert(ViewHolder helper, DishBean item) {
                helper.setText(R.id.tv_no, item.getNo());
                helper.setText(R.id.tv_name, item.getName());
                helper.setText(R.id.tv_number, item.getNumber()+"份");
                helper.setText(R.id.tv_price, "$"+item.getPrice());
                if (item.getState() == 1){
                    helper.setBackground(R.id.ll_bg, null);
                }else {
                    helper.setBackground(R.id.ll_bg, getDrawable(R.drawable.rectangle_three));
                }
            }
        };
        lv_hotdish.setAdapter(hotAdapter);
        lv_hotdish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (hots.get(i).getState() == 1){
                    hots.get(i).setState(2);
                }else {
                    hots.get(i).setState(1);
                }
                hotAdapter.notifyDataSetChanged();
                setButtonState();
            }
        });
        coolAdapter = new CommonAdapter<DishBean>(this, cools, R.layout.item_detail) {
            @Override
            public void convert(ViewHolder helper, DishBean item) {
                helper.setText(R.id.tv_no, item.getNo());
                helper.setText(R.id.tv_name, item.getName());
                helper.setText(R.id.tv_number, item.getNumber()+"份");
                helper.setText(R.id.tv_price, "$"+item.getPrice());
                if (item.getState() == 1){
                    helper.setBackground(R.id.ll_bg, null);
                }else {
                    helper.setBackground(R.id.ll_bg, getDrawable(R.drawable.rectangle_three));
                }
            }
        };
        lv_cooldish.setAdapter(coolAdapter);
        lv_cooldish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (cools.get(i).getState() == 1){
                    cools.get(i).setState(2);
                }else {
                    cools.get(i).setState(1);
                }
                coolAdapter.notifyDataSetChanged();
                setButtonState();
            }
        });
        noodleAdapter = new CommonAdapter<DishBean>(this, noodles, R.layout.item_detail) {
            @Override
            public void convert(ViewHolder helper, DishBean item) {
                helper.setText(R.id.tv_no, item.getNo());
                helper.setText(R.id.tv_name, item.getName());
                helper.setText(R.id.tv_number, item.getNumber()+"份");
                helper.setText(R.id.tv_price, "$"+item.getPrice());
                if (item.getState() == 1){
                    helper.setBackground(R.id.ll_bg, null);
                }else {
                    helper.setBackground(R.id.ll_bg, getDrawable(R.drawable.rectangle_three));
                }
            }
        };
        lv_noodle.setAdapter(noodleAdapter);
        lv_noodle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (noodles.get(i).getState() == 1){
                    noodles.get(i).setState(2);
                }else {
                    noodles.get(i).setState(1);
                }
                noodleAdapter.notifyDataSetChanged();
                setButtonState();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_sure:
                Intent intent = new Intent(DetailActivity.this, OrderSureActivity.class);
                startCallbackActivity(intent);
                break;
        }
    }

    private void setButtonState(){
        for (DishBean item : hots) {
            if (item.getState() == 2){
                tv_sure.setText("建议换菜");
                Logger.d(TAG, "hots 建议换菜");
                return;
            }
            Logger.d(TAG, "hots 1");
        }
        Logger.d(TAG, "hots 2");
        for (DishBean item : cools) {
            if (item.getState() == 2){
                tv_sure.setText("建议换菜");
                Logger.d(TAG, "cools 建议换菜");
                return;
            }
            Logger.d(TAG, "cools 1");
        }
        Logger.d(TAG, "cools 2");
        for (DishBean item : noodles) {
            if (item.getState() == 2){
                tv_sure.setText("建议换菜");
                Logger.d(TAG, "noodles 建议换菜");
                return;
            }
            Logger.d(TAG, "noodles 1");
        }
        tv_sure.setText("可订");
        Logger.d(TAG, "noodles 2");
    }

}
