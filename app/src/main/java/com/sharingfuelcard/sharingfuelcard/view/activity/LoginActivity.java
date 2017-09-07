package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class LoginActivity extends BaseActivity {
    private ImageView ivAvatar;
    private TextInputEditText edtPhone, edtPassword;
    private TextView tvForgetPswd, tvRegister;
    private Button btnLogin;

    public static void gotoLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        edtPhone = (TextInputEditText) findViewById(R.id.edt_phone);
        edtPassword = (TextInputEditText) findViewById(R.id.edt_password);
        tvForgetPswd = (TextView) findViewById(R.id.tv_forgetpswd);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        btnLogin = (Button) findViewById(R.id.btn_login);

        tvForgetPswd.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("登陆");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_register:
                RegisterVertifyPhoneActivity.gotoRegisterVertifyPhoneActivity(getContext());
                break;
            case R.id.btn_login:
                LoginActivity.this.finish();
                RegisterFuelCardActivity.gotoApplyFurlCardActivity(getContext());
                break;
        }
    }
}
