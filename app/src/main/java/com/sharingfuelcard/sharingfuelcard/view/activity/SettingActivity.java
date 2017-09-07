package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;

/**
 * Created by 鹏祺 on 2017/9/7.
 */

public class SettingActivity extends BaseActivity {
    private TextView tvChangeUsername, tvChangeCarMsg, tvChangeAdress, tvChangePswd,
            tvContact;
    private Button btnLogout;
    private ImageView ivAvatar;

    public static void gotoSettingActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        tvChangeUsername = (TextView) findViewById(R.id.tv_change_username);
        tvChangeCarMsg = (TextView) findViewById(R.id.tv_change_carmsg);
        tvChangeAdress = (TextView) findViewById(R.id.tv_change_adress);
        tvChangePswd = (TextView) findViewById(R.id.tv_change_pswd);
        tvContact = (TextView) findViewById(R.id.tv_contact);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);

        tvChangeUsername.setOnClickListener(this);
        tvChangeCarMsg.setOnClickListener(this);
        tvChangeAdress.setOnClickListener(this);
        tvChangePswd.setOnClickListener(this);
        tvContact.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("设置");
    }
}
