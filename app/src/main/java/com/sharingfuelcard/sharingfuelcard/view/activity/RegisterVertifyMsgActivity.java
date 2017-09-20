package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.module.RegisterBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.RegisterService;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class RegisterVertifyMsgActivity extends BaseActivity {
    private String phone;
    private String password;
    private String vertifyCode;
    private Button btnRegister;
    private TextInputEditText edtUsername, edtGender, edtCarCode, edtCarMsg;
    private String username, gender, carCode = "", carMsg = "";
    private Retrofit retrofit;
    private RegisterService registerService;


    public static void gotoRegisterVertifiyMsgActivity(Context context, String phone, String password, String vertifyCode) {
        Intent intent = new Intent(context, RegisterVertifyMsgActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        intent.putExtra("vertifyCode", vertifyCode);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertify_msg);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        btnRegister = (Button) findViewById(R.id.btn_register);
        edtUsername = (TextInputEditText) findViewById(R.id.edt_username);
        edtGender = (TextInputEditText) findViewById(R.id.edt_gender);
        edtCarCode = (TextInputEditText) findViewById(R.id.edt_car_code);
        edtCarMsg = (TextInputEditText) findViewById(R.id.edt_car_msg);
        btnRegister.setOnClickListener(this);
    }

    private boolean check() {
        username = edtUsername.getText().toString();
        gender = edtGender.getText().toString();
        carCode = edtCarCode.getText().toString();
        carMsg = edtCarMsg.getText().toString();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入用户名");
            return false;
        } else if (TextUtils.isEmpty(gender)) {
            ToastUtils.showShort("请输入性别");
            return false;
        }
//        else if (TextUtils.isEmpty(carCode)) {
//            ToastUtils.showShort("请输入车牌号");
//            return false;
//        } else if (TextUtils.isEmpty(carMsg)) {
//            ToastUtils.showShort("请输入车辆型号");
//            return false;
//        }
        return true;
    }

    private void register() {
        Call<RegisterBean> callRegister = registerService.register(phone, password, username, gender, vertifyCode,
                carCode, carMsg);
        callRegister.enqueue(new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                Log.d("spq", response.body().message);
            }

            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {
                ToastUtils.showShort(t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_register:
                if (check())
                    register();
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        showBack();
        setTitle("基本信息");
        phone = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        vertifyCode = getIntent().getStringExtra("vertifyCode");

        retrofit = Httptools.getInstance().getRetrofit();
        registerService = retrofit.create(RegisterService.class);
    }

}
