package com.pdscjxy.serverapp.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pdscjxy.serverapp.R;
import com.pdscjxy.serverapp.activity.base.BaseActivity;
import com.pdscjxy.serverapp.manager.Constant;
import com.pdscjxy.serverapp.util.Logger;

/**
 * Created by tangzy on 17/10/29.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";

    public static String MOBILE = "mobile";
    private String mobile;

    MyCount myCount = new MyCount(60000, 1000);
    private TextView tv_send_code, tv_register, tv_agreeprotocol_url_link;
    private CheckedTextView cb_show_pw_img;
    private CheckBox agreeonprotocol;
    private EditText et_phone_number, et_code, et_password;
    private ImageView iv_regist_mobile_delete, iv_code_delete, iv_pd_delete;
    private LinearLayout ll_xy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitleLayout(ONE_LAYOUT);
//        hideTitle();
        mobile = getIntent().getStringExtra(MOBILE);
        if (!TextUtils.isEmpty(mobile)){
            setTitleText("忘记密码");
        }else {
            setTitleText("注册");
        }
        init();

        if (!TextUtils.isEmpty(mobile)){
            et_phone_number.setText(mobile);
            et_phone_number.setEnabled(false);
            iv_regist_mobile_delete.setVisibility(View.INVISIBLE);

            tv_send_code.setEnabled(false);
            myCount.start();
            tv_register.setText("确认");
            ll_xy.setVisibility(View.GONE);

        }

    }

    private void init() {
        ll_xy = (LinearLayout) findViewById(R.id.ll_xy);
        iv_regist_mobile_delete = (ImageView) findViewById(R.id.iv_regist_mobile_delete);
        iv_regist_mobile_delete.setOnClickListener(this);
        iv_code_delete = (ImageView) findViewById(R.id.iv_code_delete);
        iv_code_delete.setOnClickListener(this);
        iv_pd_delete = (ImageView) findViewById(R.id.iv_pd_delete);
        iv_pd_delete.setOnClickListener(this);

        tv_send_code = (TextView) findViewById(R.id.tv_send_code);
        tv_send_code.setOnClickListener(this);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
        agreeonprotocol = (CheckBox) findViewById(R.id.agreeonprotocol);
//        agreeonprotocol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    Logger.d(TAG, "onCheckedChanged b = "+b);
//                }else {
//                    Logger.d(TAG, "onCheckedChanged b = "+b);
//                }
//            }
//        });
        tv_agreeprotocol_url_link = (TextView) findViewById(R.id.tv_agreeprotocol_url_link);
        tv_agreeprotocol_url_link.setOnClickListener(this);
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
                    iv_regist_mobile_delete.setVisibility(View.INVISIBLE);
                }else {
                    iv_regist_mobile_delete.setVisibility(View.VISIBLE);
                }
            }
        });
        et_code = (EditText) findViewById(R.id.et_code);
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)){
                    iv_code_delete.setVisibility(View.INVISIBLE);
                }else {
                    iv_code_delete.setVisibility(View.VISIBLE);
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
                    iv_pd_delete.setVisibility(View.INVISIBLE);
                }else {
                    iv_pd_delete.setVisibility(View.VISIBLE);
                }
            }
        });
        cb_show_pw_img = (CheckedTextView) findViewById(R.id.cb_show_pw_img);
        cb_show_pw_img.setOnClickListener(this);

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
            case R.id.tv_send_code:
                tv_send_code.setEnabled(false);
                myCount.start();

                break;
            case R.id.tv_register:

                finish();

                break;
            case R.id.tv_agreeprotocol_url_link:

                break;
            case R.id.iv_regist_mobile_delete:
                et_phone_number.setText("");

                break;
            case R.id.iv_code_delete:
                et_code.setText("");

                break;
            case R.id.iv_pd_delete:
                et_password.setText("");

                break;
        }
    }

    public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tv_send_code.setText("重新发送");
            tv_send_code.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_send_code.setText(millisUntilFinished / 1000 + "S");
        }

    }
}
