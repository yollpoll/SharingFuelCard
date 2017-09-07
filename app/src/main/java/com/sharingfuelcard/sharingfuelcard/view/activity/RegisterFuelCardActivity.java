package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class RegisterFuelCardActivity extends BaseActivity {
    private ImageView ivCNPC, ivSinopec;
    private Button btnSkip, btnApplyCard;

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
        ivCNPC = (ImageView) findViewById(R.id.iv_CNPC);
        ivSinopec = (ImageView) findViewById(R.id.iv_Sinopec);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnApplyCard = (Button) findViewById(R.id.btn_apply);

        ivSinopec.setOnClickListener(this);
        ivCNPC.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        btnApplyCard.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("申请油卡");
        setHeadRightText("跳过");
    }

    @Override
    protected void onRightTVClick() {
        super.onRightTVClick();
        MainActivity.gotoMainActivity(getContext());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_CNPC:
                BindFuelActivity.gotoBindFuelActivity(getContext());
                break;
            case R.id.iv_Sinopec:
                BindFuelActivity.gotoBindFuelActivity(getContext());
                break;
            case R.id.btn_skip:
                MainActivity.gotoMainActivity(getContext());
                break;
            case R.id.btn_apply:
                ApplyFuelCardActivity.gotoApplyFurlCard(getContext());
                break;
        }
    }
}
