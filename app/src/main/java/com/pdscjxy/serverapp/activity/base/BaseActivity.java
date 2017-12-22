package com.pdscjxy.serverapp.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pdscjxy.serverapp.util.OsUtil;
import com.pdscjxy.serverapp.util.StatusBarUtil;

import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.permission.EasyPermissions;
import com.pdscjxy.serverapp.util.Logger;
import com.pdscjxy.serverapp.view.ProgressDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/10/26.
 */

public class BaseActivity extends AppCompatActivity implements IActivity, EasyPermissions.PermissionCallbacks{
    private static final String TAG = "UIBaseActivity";
    public String BACK_BTN_TEXT = "backBtnText";

// TODO: 2017/10/30
//    /**单个View控件的绑定*/
//    @BindView(R.id.btn_login)
///**多个控件的绑定可以写在List或者Array中*/
//    @BindViews({ R.id.first_name, R.id.middle_name, R.id.last_name })
//    List<EditText> nameViews;
//
//    @BindString(R.string.title) String title;
//    @BindDrawable(R.drawable.graphic) Drawable graphic;
//    @BindColor(R.color.red) int red; // int or ColorStateList field
//    @BindDimen(R.dimen.spacer) Float spacer; // int (for pixel size) or float (for exact value) field
//@OnClick(R.id.submit)
//public void submit(View view) {.
//}
//@OnClick(R.id.submit)
//public void sayHi(Button button) {
//    button.setText("Hello!");
//}
//@OnClick({R.id.submit,R.id.login})
//public void sayHi(Button button) {
//    button.setText("Hello!");
//}

    //    @Bind(R.id.sec_title_tv)
    private TextView mTitleView;
    // 标题栏左侧，右侧图标
//    @Bind(R.id.title_left_img)
    private ImageView mLeftBtn;

    //    @Bind(R.id.included_title)
    private LinearLayout titleLayout;

    public static final int REQUEST_CODE_CALLBACK = 0x1000;
    public static final String EXTRA_ACTIVITY_NAME = "_extra_activity_name";
    public static final String EXTRA_START_CALLBACK = "_extra_start_callback";

    public static final String ONE_LAYOUT = "OneLayout";
    public static final String TWO_LAYOUT = "TwoLayout";


    protected boolean isFirstLayout = true;

    private static final int DIALOG_WAIT = 102;
    private static final int MSG_WHAT_SHOW_WAIT_DIALOG = 104;
    private static final int MSG_WHAT_HIDE_WAIT_DIALOG = 105;
    private String showMessage = "";
    private int dialogId = -1;

    private View mViewStatusBarPlace;


    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        super.setContentView(R.layout.activity_base);
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        ButterKnife.unbind(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        mViewStatusBarPlace = findViewById(R.id.view_status_bar_place);
        if (layoutResID < 0) {
            return;
        }
        setView(layoutResID);
        ButterKnife.bind(this);
        initTitleBar();
        // TODO: 2017/12/8 条装
        setImmersiveStatusBar(true, getResources().getColor(R.color.color_EAEAEA));
        // TODO: 2017/12/8 图片类型 
//        StatusBarUtil.setTranslucentForImageView(BaseActivity.this, 0);

//        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
////            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
    }   

//    @OnClick(R.id.title_left_img)
//    void onBack() {
//        finish();
//    }

    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    protected void setImmersiveStatusBar(boolean fontIconDark, int statusBarPlaceColor) {
        setTranslucentStatus();
        if (fontIconDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    || OsUtil.isMIUI()
                    || OsUtil.isFlyme()) {
                setStatusBarFontIconDark(true);
            } else {
                if (statusBarPlaceColor == Color.WHITE) {
                    statusBarPlaceColor = 0xffcccccc;
                }
            }
        }
        setStatusBarPlaceColor(statusBarPlaceColor);
    }


    private void setStatusBarPlaceColor(int statusColor) {
        if (mViewStatusBarPlace != null) {
            mViewStatusBarPlace.setBackgroundColor(statusColor);
        }
    }

    /**
     * 设置状态栏透明
     */
    private void setTranslucentStatus() {

        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体是否为深色
     */
    private void setStatusBarFontIconDark(boolean dark) {
        // 小米MIUI
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 魅族FlymeUI
        try {
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }


    /**
     * 设置页面最外层布局 FitsSystemWindows 属性
     * @param activity
     * @param value
     */
    public static void setFitsSystemWindows(Activity activity, boolean value) {
        ViewGroup contentFrameLayout = (ViewGroup) activity.findViewById(android.R.id.content);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(value);
        }
    }

    public TextView getTitleView() {
        return mTitleView;
    }

    public void setTitleText(String title) {
        if (mTitleView!=null)
            mTitleView.setText(title);
    }



    public ImageView getLeftBtn() {
        return mLeftBtn;
    }

    public LinearLayout getTitleLayout() {
        return titleLayout;
    }

    /**
     * 设置Activity的内容布局，取代setContentView（）方法
     */
    public void setView(int layoutResID) {
        LinearLayout content_linear = (LinearLayout) this.findViewById(R.id.content_view);
        content_linear.addView(View.inflate(this, layoutResID, null), new LinearLayout.LayoutParams(-1, -1));

        initAllViewForActivity(content_linear);

    }

    private void initAllViewForActivity(final ViewGroup vg) {
        vg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                vg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (activity != null && activity instanceof IActivity) {
                    ((IActivity) activity).onActivityFirstLayout();
                }
            }
        });
    }
    //
    public void initTitleBar() {
        mTitleView = (TextView) findViewById(R.id.sec_title_tv);
        mLeftBtn = (ImageView) findViewById(R.id.title_left_img);
        titleLayout = (LinearLayout) findViewById(R.id.included_title);
//
        mLeftBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    public void setTitleLayout(String layout) {
        if (titleLayout.getVisibility() != View.VISIBLE){
            titleLayout.setVisibility(View.VISIBLE);
        }
        if (ONE_LAYOUT.equals(layout)){
            titleLayout.setBackgroundColor(getResources().getColor(R.color.color_EAEAEA));
            setWindowStatusBarColor(this, R.color.color_EAEAEA);
        }else if (TWO_LAYOUT.equals(layout)){

        }
    }

    public void hideTitle() {
        titleLayout.setVisibility(View.GONE);
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLeftShow(boolean isShow) {
        if (mLeftBtn != null) {
            if (isShow){
                mLeftBtn.setVisibility(View.VISIBLE);
            }else {
                mLeftBtn.setVisibility(View.GONE);
            }
        }
    }
    @Override
    public void setCustomedLayout(boolean customed) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.i(getClass().getSimpleName(), "onActivityResult：" + getClass().getSimpleName() + "   intent is null： " + (data == null));
        if (!onActivityResultC(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 当activity第一次layout之后
     */
    @Override
    public void onActivityFirstLayout() {
        isFirstLayout = false;
        Logger.i(getClass().getSimpleName(), "onActivityFirstLayout"+"   isFirstLayout:"+isFirstLayout);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i(getClass().getSimpleName(), "onResume"+"   isFirstLayout:"+isFirstLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i(getClass().getSimpleName(), "onPause");
    }

    protected void startProgressDialog(String text) {
        ProgressDialog.getInstance().startProgressDialog(this, text);
    }
    protected void startProgressDialog() {
        ProgressDialog.getInstance().startProgressDialog(this);
    }

    protected void stopProgressDialog() {
        ProgressDialog.getInstance().stopProgressDialog();
    }



    protected static final int RC_PERM = 123;

    protected static int reSting = R.string.ask_again;//默认提示语句
    private com.pdscjxy.serverapp.activity.base.BaseActivity.CheckPermListener mListener;

    public interface CheckPermListener {
        //权限通过后的回调方法
        void superPermission();
    }
    public void checkPermission(com.pdscjxy.serverapp.activity.base.BaseActivity.CheckPermListener listener, int resString, String... mPerms) {
        mListener = listener;
        List<String> list = new ArrayList<>();
        for (String item: mPerms) {
            if (!EasyPermissions.hasPermissions(this, item)) {
                list.add(item);
            }
        }
        int length = list.size();
        if (length>0){
            String[] perms = new String[list.size()];
            for (int i = 0; i<length; i++){
                perms[i] = list.get(i);
            }
            EasyPermissions.requestPermissions(this, getString(resString),
                    RC_PERM, perms);
        }else {
            if (mListener != null)
                mListener.superPermission();
        }
//        if (EasyPermissions.hasPermissions(this, mPerms)) {
//            if (mListener != null)
//                mListener.superPermission();
//        } else {
//            EasyPermissions.requestPermissions(this, getString(resString),
//                    RC_PERM, mPerms);
//        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //同意了某些权限可能不是全部
    }

    @Override
    public void onPermissionsAllGranted() {
        if (mListener != null)
            mListener.superPermission();//同意了全部权限的回调
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.perm_tip),
                R.string.setting, R.string.cancel, null, perms);
    }
    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    /**
     * 进入过场动画，在startActivity[ForResult]之后调用才有效
     *
     * @param anims 可以为空，int enterAnim, int exitAnim<br>
     */
    public void enterAnimation(@AnimRes int... anims) {
        int enterAnim = R.anim.slide_in_right;
        int exitAnim = R.anim.slide_out_left;
        if (null != anims && anims.length == 2) {
            enterAnim = anims[0];
            exitAnim = anims[1];
        }
        overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 启动一个插入型Activity，自带转场
     * 例如，当你在A中启动B，如果你想在B中启动C，同时关闭A
     * 则在A中使用startCallbackActivity启动B
     * 在B中使用startResponseActivity启动C即可
     *
     * @param intent
     */
    public void startCallbackActivity(Intent intent) {
        Logger.e(TAG, "intent.getComponent()==null:" + (intent.getComponent() == null));
        Logger.i(TAG, getClass().getName() + " => " + intent.getComponent().getClassName());
//        intent.putExtra(BACK_BTN_TEXT, getTitle().getText().toString());//将自己的title传给打开的页面作为返回按钮的文本内容
//        Logger.i("xsw", "TITLE：  " + getTitle().getText().toString());
//        if (this instanceof FragmentActivity && fragment != null) {
//            fragment.startActivityForResult(intent, REQUEST_CODE_CALLBACK);
//        } else {
//            ((UIBaseActivity)activity).isSkip = true;
        startActivityForResult(intent, REQUEST_CODE_CALLBACK);
//        }
        enterAnimation();
    }
    /**
     * 启动一个Activity，自带转场，同时关闭自己
     * 例如，当你在A中启动B，如果你想在B中启动C，同时关闭B
     * 则在A中使用startCallbackActivity启动B
     * 在B中使用startResponseActivity，关闭B并启动C即可
     *
     * @param intent
     */
    public void startResponseActivity(Intent intent) {
        Logger.i(TAG, "startResponseActivity：" + "   intent is null： " + (intent == null));
        setResult(Activity.RESULT_FIRST_USER, intent);
        finish();
    }

    /**
     * 关闭从[自己到目标clazz]之间的所有Activity，并启动一个新的Activity
     * 例如，当你在A中启动B，B中启动C->D->E->F。此时想启动Z，同时关闭之前除A以外所有Activity
     * 则在从A到Y启动Activity都使用startCallbackActivity方法。而在Y->Z使用
     * startResponseActivityFromAssignedActivity，第一个参数选Z的intent，
     * 第二个参数选用想要关闭的最前面一个Activity也就是B。
     * <p>
     * 如果仅仅是想关闭从Z到A的所有Activity而不开新的Activity，则此处传入一个不带class和Action的干净的 Intent 即可
     *
     * @param clazz  打开新Activity时所要关闭的最后一个Activity
     * @param intent
     */
    public void startResponseActivityFromAssignedActivity(Intent intent, Class<? extends Activity> clazz) {
        Logger.i(TAG, "startResponseActivityFromAssignedActivity  intent:" + intent + " clazz:" + clazz.getName());
        if (getClass().getCanonicalName().equals(clazz.getCanonicalName())){
            startResponseActivity(intent);
        }else {
            setResult(Activity.RESULT_FIRST_USER, intent);
            intent.putExtra(EXTRA_ACTIVITY_NAME, clazz.getCanonicalName());
            finish();
        }
    }


    //只有ui.onActivityResult里面的代码都不执行才会交给外部调用者的super.onActivityResult处理
    /*package*/
    boolean onActivityResultC(int requestCode, int resultCode, Intent data) {
        Logger.i(TAG, "onActivityResult ------ Intent data is null： " + (data == null) + "  requestCode:" + requestCode + "   resultCode:" + resultCode);
        if (requestCode == REQUEST_CODE_CALLBACK) {
            if (resultCode == Activity.RESULT_FIRST_USER) {
                if (data != null) {
                    try {
                        String activityName = data.getStringExtra(EXTRA_ACTIVITY_NAME);
                        Logger.e(TAG, "onActivityResult  this Activity name:" + getClass().getCanonicalName() + "   target Activity name:" + activityName);
                        if (!TextUtils.isEmpty(activityName)) {
                            Logger.i(TAG, "UILayer的onActivityResult方法执行了，并且activityName不为空");
                            @SuppressWarnings("unchecked")
                            Class<? extends Activity> clazz = (Class<? extends Activity>) Class.forName(activityName);
                            if (getClass().getCanonicalName().equals(activityName)) {
                                Logger.i(TAG, "onActivityResult");
                                if (data.getBooleanExtra(EXTRA_START_CALLBACK, false)) {
                                    data.removeExtra(EXTRA_ACTIVITY_NAME);
                                    startResponseActivity(data);
                                    return true;
                                    /*EXTRA_START_CALLBACK 为 true 代表在名为 activityName 的activity结束之后，将 data 交给上一个activity去处理。
                                     反之，如果为 false 代表会在当前activity结束后立即在上个activity基础上用 data 再开启一个activity。*/
                                }
                                finish();
                                if (isIntentValid(data)) {
                                    startActivity(data);
                                }
                                return true;
                            }
                            startResponseActivityFromAssignedActivity(data, clazz);
                            return true;
                        }
                        //FIXME
                    } catch (Exception ignore) {
                    }
                    if (isIntentValid(data)) {
                        startCallbackActivity(data);//只要data不为空就会开启一个新的Activity
                    }
                    return true;
                }
            }
        }else if (requestCode == EasyPermissions.SETTINGS_REQ_CODE) {
            //设置返回
        }
        return false;
    }

    public boolean isIntentValid(Intent intent) {//判断intent中是否包含一个明确的意图，即是否有明确的Activity要跳转
        if (intent == null || intent.getComponent() == null)
            return false;
        return !TextUtils.isEmpty(intent.getComponent().getClassName() + intent.getAction());
    }

    public void showWaitDialog(String waitMessage) {
        sendShowWaitDialogMsg(waitMessage);
    }

    public void hideWaitDialog() {
//        fHandler.removeMessages(MSG_WHAT_HIDE_WAIT_DIALOG);
        sendHideWaitDialogMsg();
    }


    private void sendShowWaitDialogMsg(String waitMessage) {
//        fHandler.removeMessages(MSG_WHAT_SHOW_WAIT_DIALOG);
//        showMessage = waitMessage;
//        Message msg = new Message();
//        msg.what = MSG_WHAT_SHOW_WAIT_DIALOG;
//        fHandler.sendMessage(msg);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    if (dialogId == -1) {
                        dialogId = DIALOG_WAIT;
                        showDialog(DIALOG_WAIT);
                    }
                }
            }
        });
    }

    private void sendHideWaitDialogMsg() {
//        fHandler.removeMessages(MSG_WHAT_HIDE_WAIT_DIALOG);
//        Message msg = new Message();
//        msg.what = MSG_WHAT_HIDE_WAIT_DIALOG;
//        fHandler.sendMessage(msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    if (dialogId == DIALOG_WAIT) {
                        dialogId = -1;
                        try {
                            dismissDialog(DIALOG_WAIT);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        });
    }



}
