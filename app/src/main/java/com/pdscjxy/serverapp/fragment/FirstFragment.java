package com.pdscjxy.serverapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.adapter.CommonAdapter;
import com.pdscjxy.serverapp.adapter.ViewHolder;
import com.pdscjxy.serverapp.fragment.base.BaseFragment;
import com.pdscjxy.serverapp.util.Logger;
import com.pdscjxy.serverapp.view.ZdyListView;
import com.pdscjxy.serverapp.view.ZdyScrollView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/10/19.
 */

public class FirstFragment extends BaseFragment {

    @BindView(R.id.lv_home)
    ZdyListView lv_home;

    @BindView(R.id.scrollView)
    ZdyScrollView scrollView;

    private CommonAdapter<JSONObject> homeAdapter;
    private List<JSONObject> lists = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView(R.layout.fragment_first);
        init();
        JSONObject home;
        for (int i = 0; i<5; i++){
            home = new JSONObject();
            lists.add(home);
        }
        homeAdapter.notifyDataSetChanged();
    }

    private void init() {
        homeAdapter = new CommonAdapter<JSONObject>(getActivity(), lists, R.layout.item_home) {
            @Override
            public void convert(ViewHolder helper, JSONObject item) {
//                Logger.d(TAG, "lists = "+lists.size());

            }
        };
        lv_home.setAdapter(homeAdapter);
        scrollView.setOnZdyScrollViewListener(new ZdyScrollView.OnZdyScrollViewListener() {
            @Override
            public void ZdyScrollViewListener() {
                lv_home.onLoading();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject home;
                                for (int i = 0; i<5; i++){
                                    home = new JSONObject();
                                    lists.add(home);
                                }
                                homeAdapter.notifyDataSetChanged();
                                lv_home.LoadingComplete();
                                scrollView.loadingComponent();

                            }
                        });

                    }
                }).start();

            }
        });
    }

}
