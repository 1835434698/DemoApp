package com.pdscjxy.serverapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.pdscjxy.serverapp.MainActivity;
import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.activity.base.BaseActivity;
import com.pdscjxy.serverapp.fragment.BannerItemFragment;
import com.pdscjxy.serverapp.util.PrefUtils;


/**
 * Created by Administrator on 2017/10/26.
 */

public class BannerActivity extends BaseActivity{
//    @Bind(R.id.mViewPager)
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle bundle) {
        // TODO Auto-generated method stub
        super.onCreate(bundle);
        if(!PrefUtils.getPrefBoolean(BannerActivity.this, "isFirst", true)) {
            Intent intent = new Intent(this, LoginActivity.class);
//            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_banner);
        initView();
        hideTitle();
        initAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initAdapter() {
        // TODO Auto-generated method stub
        mViewPager.setAdapter(new ViewPagerAdapter(this.getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void initView() {
        // TODO Auto-generated method stub
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int index) {
            // TODO Auto-generated method stub
            Fragment f= new BannerItemFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index",index);
            f.setArguments(bundle);
            return f;
        }



        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 3;
        }
    }
}
