package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.UserBean;
import com.sharingfuelcard.sharingfuelcard.utils.SPUtiles;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;

/**
 * Created by 鹏祺 on 2017/9/7.
 * 个人中心
 */

public class SettingActivity extends BaseActivity {
    private TextView tvMyFuelCard, tvCurrentPlan, tvHistoryRecharge, tvChangePswd, tvChangeAdress,
            tvContact, tvUsername, tvTitle;
    private Button btnLogout;
    private RelativeLayout rlTitle;
    private TextView tvCarCode;
    private ImageView ivAvatar;
    private UserBean userBean;

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
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        tvMyFuelCard = (TextView) findViewById(R.id.tv_my_fuel_card);
        tvCurrentPlan = (TextView) findViewById(R.id.tv_current_plan);
        tvChangeAdress = (TextView) findViewById(R.id.tv_change_adress);
        tvHistoryRecharge = (TextView) findViewById(R.id.tv_history_recharge);
        tvChangePswd = (TextView) findViewById(R.id.tv_change_password);
        tvContact = (TextView) findViewById(R.id.tv_contact);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        tvtitle = (TextView) findViewById(R.id.tv_title);
        tvUsername = (TextView) findViewById(R.id.tv_username);
        tvCarCode = (TextView) findViewById(R.id.tv_car_code);

        tvMyFuelCard.setOnClickListener(this);
        tvCurrentPlan.setOnClickListener(this);
        tvHistoryRecharge.setOnClickListener(this);
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

        userBean = SPUtiles.getUser();
        tvUsername.setText(userBean.getUsername());
        tvCarCode.setText(userBean.getLicense_plate());
        Glide.with(this).load(Url.HEAD_URL + userBean.getAvatar()).into(ivAvatar);
    }

    @Override
    protected void onLeftIVClick() {
        super.onLeftIVClick();
        this.finish();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_my_fuel_card:
                MyFeulCardActivity.gotoMyFuelCardActivity(this);
                break;
            case R.id.tv_history_recharge:
                RechargeHistoryActivity.gotoRechargeHistoryActivity(this);
                break;
            case R.id.tv_current_plan:
                CurrentChoiceActivity.gotoCurrentChoiceActivity(this);
                break;
        }
    }
}
