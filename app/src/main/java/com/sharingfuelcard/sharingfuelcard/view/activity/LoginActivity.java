package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.module.LoginBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class LoginActivity extends BaseActivity {
    private ImageView ivAvatar;
    private TextInputEditText edtPhone, edtPassword;
    private TextView tvForgetPswd, tvRegister, tvLoginViaWechat;
    private Button btnLogin;
    private boolean isPhoneClear = true, isPswdClear = true;
    private Retrofit retrofit;
    private LoginService loginService;
    private long phone;
    private String password;


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
        tvLoginViaWechat = (TextView) findViewById(R.id.tv_login_via_wechat);

        tvLoginViaWechat.setOnClickListener(this);
        tvForgetPswd.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isPswdClear = edtPassword.getText().toString().isEmpty();
                Log.d("spq", (isPhoneClear || isPswdClear) + " " + isPswdClear + " " + isPhoneClear);
                if (isPhoneClear || isPswdClear) {
                    btnLogin.setEnabled(false);
                } else {
                    btnLogin.setEnabled(true);
                }
            }
        });
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isPhoneClear = edtPhone.getText().toString().isEmpty();
                Log.d("spq", (isPhoneClear || isPswdClear) + " " + isPswdClear + " " + isPhoneClear);
                if (isPhoneClear || isPswdClear) {
                    btnLogin.setEnabled(false);
                } else {
                    btnLogin.setEnabled(true);
                }
            }
        });
    }

    private boolean check() {
        phone = Long.parseLong(edtPhone.getText().toString());
        password = edtPassword.getText().toString();
        return true;
    }

    private void login() {
        retrofit = Httptools.getInstance().getRetrofit();
        loginService = retrofit.create(LoginService.class);
        Call<ResponseData<LoginBean>> call = loginService.login(phone, password);
        call.enqueue(new Callback<ResponseData<LoginBean>>() {
            @Override
            public void onResponse(Call<ResponseData<LoginBean>> call, Response<ResponseData<LoginBean>> response) {
//                Log.d("spq", response.body().getData().getToken());
                LoginActivity.this.finish();
                RegisterFuelCardActivity.gotoApplyFurlCardActivity(getContext());
            }

            @Override
            public void onFailure(Call<ResponseData<LoginBean>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_register:
                RegisterVertifyPhoneActivity.gotoRegisterVertifyPhoneActivity(getContext());
                break;
            case R.id.btn_login:
                if (check())
                    login();
//                LoginActivity.this.finish();
//                RegisterFuelCardActivity.gotoApplyFurlCardActivity(getContext());
                break;
            case R.id.tv_login_via_wechat:
                break;
        }
    }
}
