package com.pdscjxy.serverapp.manager;

import android.content.Context;
import android.os.Environment;
import android.text.InputFilter;

import com.pdscjxy.serverapp.util.EditInputFilter;

/**
 * Created by Administrator on 2017/10/26.
 */

public class Constant {

    public static boolean isProduction = false;

    public static String appName = "ServerApp";

    public static Context context;
    public static Context app;
//    public static final String url = "http://192.168.32.44:8080/MySpringWeb/mvc/";
//    public static final String url = "http://47.94.22.205/koudaitu_test/test/";
    public static final String url = "http://server/";
    public static int widthScreen;
    public static int heightScreen;

    public final static int ERROR_CODE = 9999;
    //
    public static String MESSSAGE = "网络错误";
    public static String MESSSAGEJSON = "数据解析错误";
    public static String path = Environment.getExternalStorageDirectory().toString()+"/ServerApp";

    public static final String logPath = path + "/log/";
    public static final String logException = "logException.txt";


    public static final String WEBVIEW_URL = "webView_url";

    public static final String MOBILE = "mobile";

    //进件图片上传
    public static final int REQUEST_IMAGE = 1002;
    public static final int MENU_CAMERA = 101;//相机
    public static final int MENU_ALBUM = 102;//相册
    public static final int MENU_CANCEL = 103;//取消


    public static final String PHONE = "^1\\d{10}$";//1开头后面十位数字

    public static final String LIMIT_PHONE_INPUT = "^1\\d{0,10}$";//EditView限制电话号码输入
    public static final String LIMIT_PWD_INPUT = "^[A-Za-z0-9\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\-\\_\\=\\+]{0,16}$";//限制密码输入

    public static final String OLD_PWD_MATCH = "^[A-Za-z0-9-_]{6,20}$";//6-16位字母数字

    public static final String PWD = "^((?=.*?\\d)(?=.*?[A-Za-z])|(?=.*?\\d)(?=.*?[!@#$%^&*()-_=+'~])|(?=.*?[A-Za-z])(?=.*?[!@#$%^&*()-_=+'~]))[\\dA-Za-z!@#$%^&*()-_=+'~]{6,16}$";//6-16位字母符号,不纯为字母数字符号

    public static final String AllLetters = "^[a-zA-Z]{6,16}$";//全字母
    public static final String AllNumble = "^[0-9]{6,16}$";//全数字
    public static final String AllSymbol = "^[` ~ ! @ # $ % ^ & * ( ) - _ = +]{6,16}$";//全字符

    public static final InputFilter[] FILTER_LIMIT_PHONE_INPUT = {new EditInputFilter(LIMIT_PHONE_INPUT)};
    public static final InputFilter[] FILTER_LIMIT_PWD_INPUT = {new EditInputFilter(LIMIT_PWD_INPUT)};


}
