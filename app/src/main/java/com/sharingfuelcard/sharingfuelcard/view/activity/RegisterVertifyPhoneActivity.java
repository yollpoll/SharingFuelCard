package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.retrofitService.RegisterService;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class RegisterVertifyPhoneActivity extends BaseActivity {
    private Button btnNext;
    private TextInputEditText edtPhone, edtPassword, edtPasswordConfirm, edtVertifyCode;
    private String phone, password, passwordConfirm, vertifyCode;
    private RegisterService mRegisterService;
    private Retrofit mRetrofit;
    private TextView tvVertifyCode;
    private String smsCode;


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
        tvVertifyCode = (TextView) findViewById(R.id.tv_vertify_code);

        btnNext.setOnClickListener(this);
        tvVertifyCode.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("注册");
        showBack();
        mRetrofit = Httptools.getInstance().getRetrofit();
        mRegisterService = mRetrofit.create(RegisterService.class);
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
        } else if (!vertifyCode.equals(smsCode)) {
            ToastUtils.showShort("验证码有误");
            return false;
        }
        return true;

    }

    private void getSmsCode() {
        String tel = edtPhone.getText().toString();
        if (TextUtils.isEmpty(tel)) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
        Call<ResponseData<String>> call = mRegisterService.getSmsCode(tel);
        call.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                String[] code = response.body().getData().split(":");
                if (response.body().getCode() == 0) {
                    ToastUtils.showShort("访问失败");
                    return;
                } else {
                    if (code.length == 2) {
                        String tel = edtPhone.getText().toString();
                        if (!tel.equals(code[0])) {
                            ToastUtils.showShort("手机号码不同");
                            return;
                        }
                        smsCode = code[1];

                    } else {
                        ToastUtils.showShort("验证码出错");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_next:
                if (checkInput())
                    RegisterVertifyMsgActivity.gotoRegisterVertifiyMsgActivity(getContext(), phone, password, vertifyCode);
                break;
            case R.id.tv_vertify_code:
                getSmsCode();
                break;
        }
    }
}
