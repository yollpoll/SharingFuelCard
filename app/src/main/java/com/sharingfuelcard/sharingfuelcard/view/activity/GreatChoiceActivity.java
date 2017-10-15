package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;

/**
 * Created by 鹏祺 on 2017/9/19.
 */

public class GreatChoiceActivity extends BaseActivity {
    private TextView tvAdd, tvReduce, tvMoney;
    private int money=10000;

    public static void gotoGreatChoiceActivity(Context context) {
        Intent intent = new Intent(context, GreatChoiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_great_choice);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        tvAdd = (TextView) findViewById(R.id.tv_add);
        tvReduce = (TextView) findViewById(R.id.tv_reduce);
        tvMoney = (TextView) findViewById(R.id.tv_money);

        tvAdd.setOnClickListener(this);
        tvReduce.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setToolBarColor(Color.parseColor("#00000000"));
        setTitle("优选套餐");
        tvtitle.setTextColor(getResources().getColor(R.color.white));
        ImmersionBar.with(this).fullScreen(true).init();
        int height = ImmersionBar.with(this).getStatusBarHeight(this);
        Utils.setMargins(rlTitle, 0, height, 0, 0);
        setHeadLeftImg(R.mipmap.icon_back_whie);

    }

    private void changeMoney(int money) {
        if (this.money + money <=0) {
            ToastUtils.showShort("至少购买一份");
            return;
        }
        this.money += money;
        tvMoney.setText(this.money + "");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_reduce:
                changeMoney(-10000);
                break;
            case R.id.tv_add:
                changeMoney(10000);
                break;
        }
    }
}
