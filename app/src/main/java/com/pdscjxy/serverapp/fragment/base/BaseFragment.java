package com.pdscjxy.serverapp.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.activity.base.BaseActivity;


/**
 * Created by Administrator on 2017/10/26.
 */

public class BaseFragment  extends Fragment {
    public View root;
//    private LoadingView mLoadingView = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        root = inflater.inflate(R.layout.fragment_base, null);
        return root;
    }

    public void setView(int layoutResID) {
        LinearLayout content_linear = (LinearLayout) root.findViewById(R.id.fragment_content_view);
        content_linear.addView(View.inflate(getActivity(), layoutResID, null), new LinearLayout.LayoutParams(-1, -1));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //    /** 清除contentView里面的加载进度 */
//    public void removeProgress() {
//        if (mLoadingView != null&&root!=null) {
//            LinearLayout contentView = (LinearLayout) root.findViewById(R.id.fragment_content_view);
//            contentView.removeView(mLoadingView.getLoadingView());
//            mLoadingView = null;
//        }
//    }

    public View findViewById(int viewID){
        return root.findViewById(viewID);
    }

//    /** 显示嵌入式进度条 */
//    public void showProgress() {
//        removeProgress();
//        addLoadView();
//    }
//
//    private void addLoadView() {
//        if (mLoadingView == null&&root!=null) {
//            mLoadingView = new LoadingView(getActivity());
//            LinearLayout contentView = (LinearLayout) root.findViewById(R.id.fragment_content_view);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
//            contentView.addView(mLoadingView.getLoadingView(), 0, params);
//        }
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()&&root!=null) {
            delayLoad();
        }
    }

    public void delayLoad(){

    }

    public void showWaitDialog(String msg){
        Activity activity= getActivity();
        if(activity!=null&&!activity.isFinishing()){
            if(activity instanceof BaseActivity){
                ((BaseActivity)activity).showWaitDialog(msg);
            }
        }
    }

    public void hideWaitDialog(){
        Activity activity= getActivity();
        if(activity!=null&&!activity.isFinishing()){
            if(activity instanceof BaseActivity){
                ((BaseActivity)activity).hideWaitDialog();
            }
        }
    }
}
