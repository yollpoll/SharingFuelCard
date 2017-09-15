package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;

/**
 * Created by 鹏祺 on 2017/9/15.
 */

public class MoreChoiceActivity extends BaseActivity {
    public static void gotoMoreChoiceActivity(Context context) {
        Intent intent = new Intent(context, MoreChoiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_choice);
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
        setTitle("更多套餐");
    }
}
