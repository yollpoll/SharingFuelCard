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
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.retrofitService.ForgetPasswordService;
import com.sharingfuelcard.sharingfuelcard.retrofitService.RegisterService;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/10/25.
 */

public class ForgetPasswordActivity extends BaseActivity {
    private TextInputEditText edtPhone, edtVertifyCod, edtPassword, edtConfirmPassword;
    private TextView tvGetSmsCode;
    private Button btnSubmit;
    private String tel, smsCode, password, confirmPassword, confirmSmsCode = "";
    private Retrofit retrofit;
    private RegisterService registerService;
    private ForgetPasswordService forgetPasswordService;


    public static void gotoForgetPasswordActivity(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        initView();
        initData();
    }

    @Override
    protected void initData() {
        super.initData();
        showBack();
        setTitle("忘记密码");
        retrofit = Httptools.getInstance().getRetrofit();
        registerService = retrofit.create(RegisterService.class);
        forgetPasswordService = retrofit.create(ForgetPasswordService.class);
    }

    @Override
    protected void initView() {
        super.initView();
        edtPhone = (TextInputEditText) findViewById(R.id.edt_phone);
        edtVertifyCod = (TextInputEditText) findViewById(R.id.edt_vertify_code);
        edtPassword = (TextInputEditText) findViewById(R.id.edt_password);
        edtConfirmPassword = (TextInputEditText) findViewById(R.id.edt_confirm_password);
        tvGetSmsCode = (TextView) findViewById(R.id.tv_get_vertify_code);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        tvGetSmsCode.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_get_vertify_code:
                getSmsCode();
                break;
            case R.id.btn_submit:
                if (checkInput())
                    forgetPassword();
                break;
        }
    }

    private void forgetPassword() {
        Call<ResponseData<String>> call = forgetPasswordService.forget(tel, password);
        call.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                ToastUtils.showShort(response.body().getMessage());
                if (1 == response.body().getCode()) {
                    ForgetPasswordActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable t) {

            }
        });
    }

    private void getSmsCode() {
        tel = edtPhone.getText().toString();
        if(TextUtils.isEmpty(tel)){
            ToastUtils.showShort("请输入手机号");
            return;
        }
        Call<ResponseData<String>> call = registerService.getSmsCode(tel);
        tvGetSmsCode.setClickable(false);
        call.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                tvGetSmsCode.setClickable(true);
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
                        confirmSmsCode = code[1];

                    } else {
                        ToastUtils.showShort("验证码出错");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable t) {
                tvGetSmsCode.setClickable(true);

            }
        });
    }

    private boolean checkInput() {
        tel = edtPhone.getText().toString();
        password = edtPassword.getText().toString();
        confirmPassword = edtConfirmPassword.getText().toString();
        smsCode = edtVertifyCod.getText().toString();
        if (TextUtils.isEmpty(tel)) {
            ToastUtils.showShort("手机号为空");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("密码为空");
            return false;
        } else if (TextUtils.isEmpty(confirmPassword)) {
            ToastUtils.showShort("重复密码为空");
            return false;
        } else if (TextUtils.isEmpty(smsCode)) {
            ToastUtils.showShort("验证码为空");
            return false;
        } else if (!password.equals(confirmPassword)) {
            ToastUtils.showShort("两次密码不同");
            return false;
        } else if (!confirmSmsCode.equals(smsCode)) {
            ToastUtils.showShort("验证码错误");
            return false;
        }
        return true;
    }
}
