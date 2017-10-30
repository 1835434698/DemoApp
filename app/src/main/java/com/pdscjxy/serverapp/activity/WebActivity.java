package com.pdscjxy.serverapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.activity.base.BaseActivity;
import com.pdscjxy.serverapp.manager.Constant;
import com.pdscjxy.serverapp.util.Logger;
import com.pdscjxy.serverapp.util.PrefUtils;
import com.pdscjxy.serverapp.view.CustomWebView;
import com.pdscjxy.serverapp.view.ProgressWebView;
import com.pdscjxy.serverapp.view.iosspinner.BaseSpinnerAdapter;
import com.pdscjxy.serverapp.view.iosspinner.SpinnerPop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class WebActivity extends BaseActivity {
    private ProgressWebView refreshWebView;
    private TextView mRefreshError;
    private RelativeLayout errorLayout;
    private CustomWebView mRefreshWebView;
    private String webUrl = "";
    private SpinnerPop spinnerPop;//拍照相册

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.h5_web_activity);
        initView(getIntent());
        loadUrl(webUrl);

        String[] menu = getResources().getStringArray(R.array.selectPicture);
        final ArrayList<String> array = new ArrayList<String>();
        array.addAll(Arrays.asList(menu));
        BaseSpinnerAdapter baseSpinnerAdapter = new BaseSpinnerAdapter(this, array) {
            @Override
            public String getTitle(int position) {
                return array.get(position);
            }
        };

        spinnerPop = new SpinnerPop(this, baseSpinnerAdapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPop.hidePopupWindow();
                switch (i) {
                    case 0:
                        //相机-
                        BaseActivity baseFragmentActivity = (BaseActivity) WebActivity.this;//AddProfessionalActivity
                        baseFragmentActivity.checkPermission(new BaseActivity.CheckPermListener() {
                            @Override
                            public void superPermission() {
                                startCamera();
                            }
                        }, R.string.ask_again, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

                        break;
                    case 1:
                        openAlbum();
                        break;
                    case 2:
                        //取消
                        break;
                }
            }
        });
    }
//
//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        res.updateConfiguration(config, res.getDisplayMetrics() );
//        return res;
//    }

    private void setLeftIv(boolean isShow){
        setLeftShow(isShow);
    }

    private void loadUrl(String url) {
        refreshWebView.loadUrl(url);
    }

    private void initView(Intent intent) {
        mRefreshWebView = (CustomWebView) findViewById(R.id.refreshWebView);
        refreshWebView = mRefreshWebView.getmWebView();
        mRefreshError = mRefreshWebView.getmTvError();
        errorLayout = mRefreshWebView.getmErrorLayout();

        if (intent != null) {
            webUrl = intent.getStringExtra(Constant.WEBVIEW_URL);
        }

        mRefreshWebView.setmTitleTextView(getTitleView());
        refreshWebView.addJavascriptInterface(new JSNotify(), "jsToJava");

        if (TextUtils.isEmpty(webUrl)) {
            mRefreshError.setText("Url为空,加载失败");
        }
        getTitleView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (refreshWebView.canGoBack()) {
                    refreshWebView.goBack();
                    if (mRefreshWebView.getTitles().size() > 1) {
                        mRefreshWebView.getTitles().remove(mRefreshWebView.getTitles().size() - 1);
                        getTitleView().setText(mRefreshWebView.getTitles().get(mRefreshWebView.getTitles().size() - 1));
                    }
                } else {
                    finish();
                }
            }
        });
//        // 解决硬件加速问题 // TODO: 2017/10/23 导致缓慢 
//        if (Build.VERSION.SDK_INT >= 19) {
//            refreshWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
    }

    /**
     * js交互
     */
    public class JSNotify {
        /**
         * js回调方法
         * @param json
         */
        @JavascriptInterface
        public void jsCallbackMethod(String json) {
//            String type = GsonUtils.getJsonStr(json, "type");
//            if ("loginCallBack".equals(type)) {
//                String data = GsonUtils.getJsonStr(json, "data");
//                String mobile = GsonUtils.getJsonStr(data, "mobile");
//                boolean isLogin = GsonUtils.getJsonBool(data, "isLogin");
//                String employeeid = GsonUtils.getJsonStr(data, "employeeid");
//                PrefUtils.setPrefString(WebActivity.this, SPConstant.MOBILE, mobile);
//                PrefUtils.setPrefBoolean(WebActivity.this, SPConstant.IS_LOGIN, isLogin);
//                PrefUtils.setPrefString(WebActivity.this, SPConstant.EMPLOYEEID, employeeid);
//            } else if ("getDate".equals(type)) {
//                String data = GsonUtils.getJsonStr(json, "data");
//                final String time = GsonUtils.getJsonStr(data, "time");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        setTitle(time);
//                    }
//                });
//            } else if ("requestNetwork".equals(type)) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));// 进入无线网络配置界面
//                    }
//                });
//            }else if ("updateHeader".equals(type)){
//                Logger.d("tangzy", "updateHeader");
//                spinnerPop.showPopupWindow();
//            }else if ("leftIsShow".equals(type)){
//                Logger.d("tangzy", "leftIsShow");
//                String data = GsonUtils.getJsonStr(json, "data");
//                final String isHide = GsonUtils.getJsonStr(data, "isHide");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if ("0".equals(isHide)){
//                            setLeftIv(true);
//                        }else {
//                            setLeftIv(false);
//                        }
//                    }
//                });
//            }
        }
    }

    /**
     * 返回键返回上一个页面
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // TODO: 2017/10/26 是否登陆
            if (refreshWebView.canGoBack()&&!PrefUtils.getPrefString(WebActivity.this, Constant.MOBILE, "").equals("")) {
                refreshWebView.goBack();
                if (mRefreshWebView.getTitles().size() > 1) {
                    mRefreshWebView.getTitles().remove(mRefreshWebView.getTitles().size() - 1);
                    getTitleView().setText(mRefreshWebView.getTitles().get(mRefreshWebView.getTitles().size() - 1));
                }
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (refreshWebView != null) {
            refreshWebView.destroy();
            refreshWebView = null;
        }
    }


    private String originalPicPathOri;//原图像 路径
    private Uri originalPicUri;//原图像 URI
    /**
     * 启动相机
     */
    private void startCamera() {
        originalPicPathOri = null;
        originalPicUri = null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 打开相机
        File oriPhotoFile = null;
        try {
            oriPhotoFile = createOriImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (oriPhotoFile != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                originalPicUri = Uri.fromFile(oriPhotoFile);
            } else {
                originalPicUri = FileProvider.getUriForFile(this, this.getPackageName() + ".fileprovider", oriPhotoFile);
            }
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, originalPicUri);
            startActivityForResult(intent, Constant.MENU_CAMERA);
        }
    }


    /**
     * 创建原图像保存的文件
     */
    private File createOriImageFile() throws IOException {
        String imgNameOri = "IMAGE_" + new SimpleDateFormat("HHmmss").format(new Date());
        ///storage/emulated/0/Android/data/com.jieyue.loanhelper/files/Pictures/OriPicture/IMAGE_20170928_164848-1569741786.jpg
        File pictureDirOri = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/OriPicture");
        if (!pictureDirOri.exists()) {
            pictureDirOri.mkdirs();
        }
        File image = File.createTempFile(imgNameOri, ".jpg", pictureDirOri);
        originalPicPathOri = image.getAbsolutePath();
        Logger.d("","originalPicPathOri:" + originalPicPathOri);
        return image;
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        //相册-为了能获取到数据不能用ImageSelectorUtils.openPhoto(UploadPictureActivity.this, Constants.MENU_ALBUM, false, 9);如想使用请自行修改成lib模式再修改代码
//        Intent intent = new Intent(this, ImageSelectorActivity.class);
//        intent.putExtra(com.donkingliang.imageselector.constant.Constants.MAX_SELECT_COUNT, 1);
//        intent.putExtra(com.donkingliang.imageselector.constant.Constants.IS_SINGLE, false);
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constant.MENU_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.MENU_CAMERA://拍照
                if (resultCode == Activity.RESULT_OK){
                    Logger.d("tangzy", "originalPicPathOri = "+originalPicPathOri);
                }
                break;
            case Constant.MENU_ALBUM://相册
                Logger.d("tangzy", "ori ");
                //调用三方相册固定反回格式 data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
//                if (data != null) {
//                    ArrayList<String> localPicturePath = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
//                    if (localPicturePath != null && localPicturePath.size() > 0) {
//                        Logger.d("tangzy", "originalPicPathOri = "+localPicturePath.get(0));
////                        showFaces(localPicturePath.get(0));
//                        update(localPicturePath.get(0));
//                    }
//                }
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        Uri selectedImage = data.getData();
                        String[] filePathColumns = {MediaStore.Images.Media.DATA};
                        Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePathColumns[0]);
                        String imagePath = c.getString(columnIndex);
                    }
                }


                break;
        }
    }



}
