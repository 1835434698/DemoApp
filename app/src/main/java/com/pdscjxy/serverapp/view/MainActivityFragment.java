package com.pdscjxy.serverapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.activity.base.BaseActivity;
import com.pdscjxy.serverapp.fragment.FirstFragment;
import com.pdscjxy.serverapp.fragment.MineFragment;

/**
 * Created by Administrator on 2017/10/26.
 */

public class MainActivityFragment extends BaseActivity{
    private RelativeLayout tab1, tab2;
    private ImageView tab1_image, tab2_image;
    private TextView tab1_text, tab2_text;
    private int currentIndex = -1;
    private LinearLayout bottom_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
//        initTitleBar();
        hideTitle();
        initView();
        initTab();
        initListener();
        setTab(0);
    }

    private void initView() {
        // TODO Auto-generated method stub
        tab1 = (RelativeLayout) findViewById(R.id.tab1);
        tab2 = (RelativeLayout) findViewById(R.id.tab2);
        tab1_image = (ImageView) findViewById(R.id.tab1_image);
        tab2_image = (ImageView) findViewById(R.id.tab2_image);
        tab1_text = (TextView) findViewById(R.id.tab1_text);
        tab2_text = (TextView) findViewById(R.id.tab2_text);

        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
    }

    private void initTab() {//根据用户权限选择下导航显示的内容
//        switch (UserManager.getInstance().getUserRights()) {//0:客户经理；1：客服；2：团队;3:门店经理/副理；4：分部总；5：区域总
//            case 0:
//            case 2:
//                tab1_text.setText(getResources().getString(R.string.home_page));
//                tab2.setVisibility(View.VISIBLE);
//                tab2_text.setText(getResources().getString(R.string.client_page));
//                tab3_text.setText(getResources().getString(R.string.lent_page));
//                tab4_text.setText(getResources().getString(R.string.my_page));
//                break;
//
//            case 3:
//                tab1_text.setText(getResources().getString(R.string.home_page));
//                tab2.setVisibility(View.GONE);
//                tab3_text.setText(getResources().getString(R.string.lent_page));
//                tab4_text.setText(getResources().getString(R.string.my_page));
//                break;
//        }
    }

    private void initListener() {
        // TODO Auto-generated method stub
        tab1.setOnClickListener(new TabClickListener());
        tab2.setOnClickListener(new TabClickListener());
    }

    private class TabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.tab1:
                    setTab(0);
                    break;

                case R.id.tab2:
                    setTab(1);
                    break;
            }
        }
    }
    public void setTab(int tab) {//选择当前处于显示状态的tab
        // TODO Auto-generated method stub
        if (currentIndex == tab) {
            return;
        }
        switch (tab) {
            case 0:
                setTabView(0);
                break;

            case 1:
                setTabView(1);
                break;
        }
    }
    public void setTabView(int tab) {
        switch (tab) {
            case 0://
                setClientTabView(tab);
                setClientFragment(tab, 0);
                break;

            case 1://
                setClientTabView(tab);
                setClientFragment(tab, 1);
                break;
        }
    }
    public void setClientFragment(int tab, int tag) {
        if (currentIndex == tab) {
            return;
        }
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment tab0 = (FirstFragment) fragmentManager.findFragmentByTag("tab0");
        Fragment tab1 = (MineFragment) fragmentManager.findFragmentByTag("tab1");
        switch (tab) {
            case 0:
                if (tab1 != null) {
                    fragmentTransaction.hide(tab1);
                }
                if (tab0 == null) {
                    //0:客户经理；1：门店；2：总部
                    tab0 = new FirstFragment();
                    fragmentTransaction.add(R.id.fragment_content, tab0, "tab0");
                } else {
                    fragmentTransaction.show(tab0);
                }
                break;
            case 1:
                if (tab0 != null) {
                    fragmentTransaction.hide(tab0);
                }
                if (tab1 == null) {
                    tab1 = new MineFragment();
                    fragmentTransaction.add(R.id.fragment_content, tab1, "tab1");
                } else {
                    fragmentTransaction.show(tab1);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
        currentIndex = tab;
    }

    //公有方法，客户经理首页通过我的业务横向列表调用改变下导航的显示样式和切换Fragment页面
    public void setClientHomeBottomBar(int tag) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment tab0 = fragmentManager.findFragmentByTag("tab0");
        Fragment tab1 = fragmentManager.findFragmentByTag("tab1");
        if (tab0 != null) {
            fragmentTransaction.hide(tab0);
        }
        if (tab1 == null) {
            tab1 = new FirstFragment();

            Bundle bun = new Bundle();
            bun.putInt("tag", tag);
            tab1.setArguments(bun);
            fragmentTransaction.add(R.id.fragment_content, tab1, "tab1");
        } else {

//            tab1.obtainTag(tag);
            fragmentTransaction.show(tab1);
        }

        currentIndex = 1;
        fragmentTransaction.commitAllowingStateLoss();
        setClientTabView(1);
    }

    public void setClientTabView(int tab) {//处理客户经理角色时下导航字体和图标状态
        switch (tab) {
            case 0:
//                tab1_image.setImageResource(R.drawable.iv_a);
//                tab2_image.setImageResource(R.drawable.client_home_client);
//                tab1_text.setTextColor(getResources().getColor(R.color.color_2772FF));
//                tab2_text.setTextColor(getResources().getColor(R.color.color_515151));
                break;
            case 1:
//                tab1_image.setImageResource(R.drawable.client_home_home);
//                tab2_image.setImageResource(R.drawable.iv_b);
//                tab1_text.setTextColor(getResources().getColor(R.color.color_515151));
//                tab2_text.setTextColor(getResources().getColor(R.color.color_2772FF));
                break;
        }
    }

}
