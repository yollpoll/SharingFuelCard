package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class RegisterVertifyPhoneActivity extends BaseActivity {
    private Button btnNext;
    private TextInputEditText edtPhone, edtPassword, edtPasswordConfirm, edtVertifyCode;
    private String phone, password, passwordConfirm, vertifyCode;

    public static void gotoRegisterVertifyPhoneActivity(Context context) {
        Intent intent = new Intent(context, RegisterVertifyPhoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        btnNext = (Button) findViewById(R.id.btn_next);
        edtPhone = (TextInputEditText) findViewById(R.id.edt_phone);
        edtPassword = (TextInputEditText) findViewById(R.id.edt_password);
        edtPasswordConfirm = (TextInputEditText) findViewById(R.id.edt_confirm_password);
        edtVertifyCode = (TextInputEditText) findViewById(R.id.edt_vertify_code);

        btnNext.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("注册");
        showBack();
    }

    private boolean checkInput() {
        phone = edtPhone.getText().toString();
        password = edtPassword.getText().toString();
        passwordConfirm = edtPasswordConfirm.getText().toString();
        vertifyCode = edtVertifyCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort("请输入手机号");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入密码");
            return false;
        } else if (TextUtils.isEmpty(passwordConfirm)) {
            ToastUtils.showShort("请输入确认密码");
            return false;
        } else if (TextUtils.isEmpty(vertifyCode)) {
            ToastUtils.showShort("请输入验证码");
            return false;
        } else if (!(password.equals(passwordConfirm))) {
            ToastUtils.showShort("两次输入密码不同");
            return false;
        }
        return true;

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_next:
                if (checkInput())
                    RegisterVertifyMsgActivity.gotoRegisterVertifiyMsgActivity(getContext(), phone, password, vertifyCode);
                break;
        }
    }
}
