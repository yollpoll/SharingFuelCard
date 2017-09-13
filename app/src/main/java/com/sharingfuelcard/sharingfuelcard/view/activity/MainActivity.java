package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarParams;
import com.gyf.barlibrary.ImmersionBar;
import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;

public class MainActivity extends BaseActivity {
    private Button btnSetting, btnRegisterFuelCard, btnMore;
    private TextView tvBalance, tvMonthTimes, tvMonthSharing;
    private RecyclerView rvPlan;
    private RelativeLayout rlTitle;

    public static void gotoMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        btnSetting = (Button) findViewById(R.id.btn_setting);
        btnRegisterFuelCard = (Button) findViewById(R.id.btn_register_fuel_card);
        btnMore = (Button) findViewById(R.id.btn_more);
        tvBalance = (TextView) findViewById(R.id.tv_balance);
        tvMonthSharing = (TextView) findViewById(R.id.tv_month_sharing);
        tvMonthTimes = (TextView) findViewById(R.id.tv_month_times);
        rvPlan = (RecyclerView) findViewById(R.id.rv_plan);

        btnSetting.setOnClickListener(this);
        btnRegisterFuelCard.setOnClickListener(this);
        btnMore.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        setToolBarColor(Color.parseColor("#00000000"));
        setHeadLeftText("设置");
        setHeadRightText("添加油卡");
        setTitle("主页");
        ImmersionBar.with(this).fullScreen(true).init();
        int height = ImmersionBar.with(this).getStatusBarHeight(this);
        Utils.setMargins(rlTitle, 0, height, 0, 0);
    }

    @Override
    protected void onLeftTVClick() {
        super.onLeftTVClick();
        SettingActivity.gotoSettingActivity(getContext());
    }

    @Override
    protected void onRightTVClick() {
        super.onRightTVClick();
        RegisterFuelCardActivity.gotoApplyFurlCardActivity(getContext());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_setting:
                SettingActivity.gotoSettingActivity(getContext());
                break;
            case R.id.btn_register_fuel_card:
                RegisterFuelCardActivity.gotoApplyFurlCardActivity(getContext());
                break;
            case R.id.btn_more:
                break;
        }
    }
}
