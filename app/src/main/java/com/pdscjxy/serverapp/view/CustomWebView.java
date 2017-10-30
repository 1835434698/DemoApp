package com.pdscjxy.serverapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pdscjxy.serverapp.MainApp;
import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.manager.Constant;
import com.pdscjxy.serverapp.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 * 自定义的WebView和后台交互
 */
public class CustomWebView extends FrameLayout {

    private ProgressWebView mWebView;
    private RelativeLayout mErrorLayout;
    private TextView mTvError;
    private TextView mTitleTextView;
    private List<String> titles = new ArrayList<String>();
    private String app2WebData;

    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_custom_webview, this, true);
        mWebView = (ProgressWebView) view.findViewById(R.id.mWebView);
        mErrorLayout = (RelativeLayout) view.findViewById(R.id.mErrorLayout);
        mTvError = (TextView) view.findViewById(R.id.mTvError);
        initWebview();
    }

    private void initWebview() {
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.requestFocusFromTouch();
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        WebSettings settings = mWebView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSaveFormData(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setUserAgentString(settings.getUserAgentString() + "/androidH5App");
    }

    public class MyWebViewClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!TextUtils.isEmpty(url) && url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                getContext().startActivity(intent);
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String host = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                host = request.getUrl().getHost();
                String url = request.getUrl().toString();
                if (!TextUtils.isEmpty(url) && url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    getContext().startActivity(intent);
                    return true;
                }
//                String[] hosts = BaseHttpUtils.IP.split("//");
//                if (!TextUtils.isEmpty(hosts[1]) && hosts[1].startsWith(host)) {
//                    Log.e("explore", host);
//                } else {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
//                    getContext().startActivity(intent);
//                }
                return false;
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mErrorLayout.setVisibility(View.GONE);
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (request.getUrl().toString().equals(view.getUrl())) {
                mTvError.setText("加载失败");
                mErrorLayout.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                view.loadData("找不到网页", "text/html", "UTF-8");
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                return;
            }
            view.loadData("找不到网页", "text/html; charset=UTF-8", null);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        }

    }

    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            ProgressBar progressbar = mWebView.getProgressbar();
            progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5, 0, 0));
            progressbar.setIndeterminate(false);
            if (newProgress == 100) {
                progressbar.setVisibility(View.GONE);
            } else {
                if (progressbar.getVisibility() == View.GONE)
                    progressbar.setVisibility(View.VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title) && !title.contains("blank") && !title.contains("172.") && !title.contains("app.")) {
                titles.add(title);
                if (mTitleTextView != null) {
                    mTitleTextView.setText(title);
                }
            }
        }
    }

    /**
     * Sync Cookie
     */
    public void syncCookie(Context context, String url) {
        try {
            Logger.d("Nat: webView.syncCookie.url", url);
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            String oldCookie = cookieManager.getCookie(url);
            if (oldCookie != null) {
                Logger.d("Nat: webView.syncCookieOutter.oldCookie", oldCookie);
            }
            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format("JSESSIONID=%s", "INPUT YOUR JSESSIONID STRING"));
            sbCookie.append(String.format(";domain=%s", "INPUT YOUR DOMAIN STRING"));
            sbCookie.append(String.format(";path=%s", "INPUT YOUR PATH STRING"));
            String cookieValue = sbCookie.toString();
            cookieManager.setCookie(url, cookieValue);
            CookieSyncManager.getInstance().sync();
            String newCookie = cookieManager.getCookie(url);
            if (newCookie != null) {
                Logger.d("Nat: webView.syncCookie.newCookie", newCookie);
            }
        } catch (Exception e) {
            Logger.e("Nat: webView.syncCookie failed", e.toString());
        }
    }

    public void synCookies(String url, String cookie) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        CookieSyncManager.createInstance(Constant.app);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);
        CookieSyncManager.getInstance().sync();
    }

    public void removeCookie() {
        CookieSyncManager.createInstance(Constant.app);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }


//    public void setJsDefault() {
//        mWebView.addJavascriptInterface(new JSNotify(), "xiangshang");
//    }

    public ProgressWebView getmWebView() {
        return mWebView;
    }

    public List<String> getTitles() {
        return titles;
    }

    public RelativeLayout getmErrorLayout() {
        return mErrorLayout;
    }

    public TextView getmTvError() {
        return mTvError;
    }

    public void setmTitleTextView(TextView mTitleTextView) {
        this.mTitleTextView = mTitleTextView;
    }

    public String getApp2WebData() {
        return app2WebData;
    }

    public void setApp2WebData(String app2WebData) {
        this.app2WebData = app2WebData;
    }
}
