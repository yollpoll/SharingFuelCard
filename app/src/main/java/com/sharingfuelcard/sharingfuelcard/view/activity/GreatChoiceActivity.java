package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;
import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;

/**
 * Created by 鹏祺 on 2017/9/19.
 */

public class GreatChoiceActivity extends BaseActivity {
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
}
