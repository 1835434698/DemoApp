package com.pdscjxy.serverapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.fragment.base.BaseFragment;


/**
 * Created by Administrator on 2017/10/19.
 */

public class FirstFragment extends BaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView(R.layout.fragment_first);
    }

}
