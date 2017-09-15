package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;

/**
 * Created by 鹏祺 on 2017/9/7.
 * 个人中心
 */

public class SettingActivity extends BaseActivity {
    private TextView tvChangeUsername, tvChangeCarMsg, tvChangeAdress, tvChangePswd,
            tvContact, tvTitle;
    private Button btnLogout;
    private ImageView ivAvatar;
    private RelativeLayout rlTitle;

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
        tvtitle = (TextView) findViewById(R.id.tv_title);
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);

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
        setTitle("个人中心");
        tvtitle.setTextColor(Color.parseColor("#ffffff"));
        setToolBarColor(Color.parseColor("#00000000"));
        ImmersionBar.with(this).fullScreen(true).init();
        int height = ImmersionBar.with(this).getStatusBarHeight(this);
        Utils.setMargins(rlTitle, 0, height, 0, 0);
        setHeadLeftImg(R.mipmap.icon_back_whie);
    }

    @Override
    protected void onLeftIVClick() {
        super.onLeftIVClick();
        this.finish();
    }
}
