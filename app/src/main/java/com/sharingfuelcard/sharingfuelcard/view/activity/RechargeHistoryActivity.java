package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;

/**
 * Created by 鹏祺 on 2017/9/19.
 */

public class RechargeHistoryActivity extends BaseActivity {
    public static void gotoRechargeHistoryActivity(Context context) {
        Intent intent = new Intent(context, RechargeHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_history);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        showBack();
        setTitle("历史充值记录");
    }
}
