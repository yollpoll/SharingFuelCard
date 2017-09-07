package com.sharingfuelcard.sharingfuelcard.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected TextView tvHeadLeft, tvtitle, tvHeadRight;
    protected ImageView ivHeadLeft, ivHeadRight;

    protected void initView() {
        tvHeadLeft = (TextView) findViewById(R.id.tv_head_left);
        tvHeadRight = (TextView) findViewById(R.id.tv_head_right);
        tvtitle = (TextView) findViewById(R.id.tv_title);
        ivHeadLeft = (ImageView) findViewById(R.id.iv_head_left);
        ivHeadRight = (ImageView) findViewById(R.id.iv_head_right);

        try {
            tvHeadLeft.setOnClickListener(this);
            tvHeadRight.setOnClickListener(this);
            ivHeadRight.setOnClickListener(this);
            ivHeadLeft.setOnClickListener(this);
        } catch (NullPointerException e) {
            return;
        }
    }

    protected void setTitle(String title) {
        if (null != tvtitle) {
            tvtitle.setText(title);
        }
    }

    protected void setHeadLeftText(String text) {
        if (null != tvHeadLeft) {
            tvHeadLeft.setText(text);
        }
    }

    protected void setHeadRightText(String text) {
        if (null != tvHeadRight) {
            tvHeadRight.setText(text);
        }
    }

    protected void setHeadLeftImg(int resourceId) {
        if (null != ivHeadLeft) {
            ivHeadLeft.setImageResource(resourceId);
        }
    }

    protected void setHeadRightImg(int resourceId) {
        if (null != ivHeadRight) {
            ivHeadRight.setImageResource(resourceId);
        }
    }

    protected void onLeftTVClick() {

    }

    protected void onLeftIVClick() {

    }

    protected void onRightTVClick() {

    }

    protected void onRightIVClick() {

    }

    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_head_left:
                onLeftTVClick();
                break;
            case R.id.tv_head_right:
                onRightTVClick();
                break;
            case R.id.iv_head_left:
                onLeftIVClick();
                break;
            case R.id.iv_head_right:
                onRightIVClick();
                break;
        }
    }

    protected Context getContext() {
        return this;
    }
}
