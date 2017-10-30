package com.pdscjxy.serverapp.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pdscjxy.serverapp.MainActivity;
import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.activity.base.BaseActivity;
import com.pdscjxy.serverapp.manager.Constant;
import com.pdscjxy.serverapp.net.OkHttpManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangzy on 17/10/29.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private TextView tv_login, tv_register, tv_forget_psw;
    private CheckBox chbox_remember;
    private CheckedTextView cb_show_pw_img;
    private EditText et_phone_number, et_password;
    private ImageView iv_login_mobile_delete, iv_password_delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.context = this;
        setContentView(R.layout.activity_login);
        hideTitle();
        init();
    }

    private void init() {
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
        tv_forget_psw = (TextView) findViewById(R.id.tv_forget_psw);
        tv_forget_psw.setOnClickListener(this);
        iv_login_mobile_delete = (ImageView) findViewById(R.id.iv_login_mobile_delete);
        iv_login_mobile_delete.setOnClickListener(this);
        iv_password_delete = (ImageView) findViewById(R.id.iv_password_delete);
        iv_password_delete.setOnClickListener(this);


        cb_show_pw_img = (CheckedTextView) findViewById(R.id.cb_show_pw_img);
        cb_show_pw_img.setOnClickListener(this);

        chbox_remember = (CheckBox) findViewById(R.id.chbox_remember);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_phone_number.setFilters(Constant.FILTER_LIMIT_PHONE_INPUT);
        et_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)){
                    iv_login_mobile_delete.setVisibility(View.INVISIBLE);
                }else {
                    iv_login_mobile_delete.setVisibility(View.VISIBLE);
                }
            }
        });

        et_password = (EditText) findViewById(R.id.et_password);
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)){
                    iv_password_delete.setVisibility(View.INVISIBLE);
                }else {
                    iv_password_delete.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cb_show_pw_img:
                cb_show_pw_img.toggle();
                if (cb_show_pw_img.isChecked()) {
//                    //如果选中，显示密码
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password.setInputType((InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD));
                    et_password.setTypeface(Typeface.SANS_SERIF);
                } else {
//                    //否则隐藏密码
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    et_password.setTypeface(Typeface.SANS_SERIF);
                }
                break;
            case R.id.iv_login_mobile_delete:
                et_phone_number.setText("");

                break;
            case R.id.iv_password_delete:
                et_password.setText("");

                break;
            case R.id.tv_forget_psw:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra(RegisterActivity.MOBILE, "18501942558");
                startCallbackActivity(intent);

                break;
            case R.id.tv_login:
                checkPermission(new CheckPermListener() {
                    @Override
                    public void superPermission() {
                        login();
                    }
                }, R.string.ask_again, Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE);


                break;
            case R.id.tv_register:
                startCallbackActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    private void login() {

        startProgressDialog();
        final Map<String, String> httpParams =  new HashMap<>();
        httpParams.put("userName","1234567890");
        httpParams.put("userPassword","123456");//
        OkHttpManager.asyncRequest("test0.php", httpParams, new OkHttpManager.ResponseListener() {
            @Override
            public void onResp(JSONObject respons, String uri) {
                stopProgressDialog();
                startCallbackActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onErr(String respons, String uri) {
                stopProgressDialog();
                startCallbackActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }, true);
    }
}
