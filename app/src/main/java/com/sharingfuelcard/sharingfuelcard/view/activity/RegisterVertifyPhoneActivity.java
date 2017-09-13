package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class RegisterVertifyPhoneActivity extends BaseActivity {
    private Button btnNext;

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

        btnNext.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("注册");
        showBack();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_next:
                RegisterVertifyMsgActivity.gotoRegisterVertifiyMsgActivity(getContext());
                break;
        }
    }
}
