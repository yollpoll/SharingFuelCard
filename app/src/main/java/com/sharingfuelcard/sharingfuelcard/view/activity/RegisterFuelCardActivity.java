package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class RegisterFuelCardActivity extends BaseActivity {
    private ImageView ivCNPC, ivSinopec;
    private RelativeLayout rlApplyFuelCard;

    public static void gotoApplyFurlCardActivity(Context context) {
        Intent intent = new Intent(context, RegisterFuelCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fuel_card);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        rlApplyFuelCard = (RelativeLayout) findViewById(R.id.rl_apply_fuel_card);
        ivCNPC = (ImageView) findViewById(R.id.iv_CNPC);
        ivSinopec = (ImageView) findViewById(R.id.iv_Sinopec);

        ivSinopec.setOnClickListener(this);
        ivCNPC.setOnClickListener(this);
        rlApplyFuelCard.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setHeadRightText("跳过");
        showBack();
    }

    @Override
    protected void onRightTVClick() {
        super.onRightTVClick();
        this.finish();
        MainActivity.gotoMainActivity(getContext());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_CNPC:
                BindFuelActivity.gotoBindFuelActivityByCNPC(getContext());
                break;
            case R.id.iv_Sinopec:
                BindFuelActivity.gotoBindFuelActivityBySinopec(getContext());
                break;
            case R.id.rl_apply_fuel_card:
                ApplyFuelCardActivity.gotoApplyFurlCard(getContext());
                break;
        }
    }
}
