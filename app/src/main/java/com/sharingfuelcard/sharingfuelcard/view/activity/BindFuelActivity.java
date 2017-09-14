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
 * Created by 鹏祺 on 2017/9/7.
 */

public class BindFuelActivity extends BaseActivity {
    public static final int TYPE_CNPC = 1;
    public static final int TYPE_SINOPEC = 2;
    public static final int TYPE_OTHERS = 3;
    private Button btnSubmit;
    private int cardType;
    private String imgUrl;
    private ImageView ivCard;

    public static void gotoBindFuelActivityByCNPC(Context context) {
        Intent intent = new Intent(context, BindFuelActivity.class);
        intent.putExtra("type", TYPE_CNPC);
        context.startActivity(intent);
    }

    public static void gotoBindFuelActivityBySinopec(Context context) {
        Intent intent = new Intent(context, BindFuelActivity.class);
        intent.putExtra("type", TYPE_SINOPEC);
        context.startActivity(intent);
    }

    public static void gotoBindFuelActivityByOthers(Context context, String imgUrl) {
        Intent intent = new Intent(context, BindFuelActivity.class);
        intent.putExtra("type", TYPE_OTHERS);
        intent.putExtra("imgUrl", imgUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_fuel_card);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        ivCard = (ImageView) findViewById(R.id.iv_furl_card);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
        cardType = getIntent().getIntExtra("type", 3);
        switch (cardType) {
            case TYPE_CNPC:
                ivCard.setImageResource(R.mipmap.icon_cnpc);
                break;
            case TYPE_SINOPEC:
                ivCard.setImageResource(R.mipmap.icon_sinopec);
                break;
            case TYPE_OTHERS:
                //TODO glide
                imgUrl = getIntent().getStringExtra("imgUrl");
                break;
        }
        showBack();
        setHeadRightText("跳过");
        setTitle("登陆油卡");
    }

    @Override
    protected void onRightTVClick() {
        super.onRightTVClick();
        MainActivity.gotoMainActivity(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_submit:
                MainActivity.gotoMainActivity(getContext());
                break;
        }
    }
}
