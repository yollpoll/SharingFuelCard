package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.module.UserBean;
import com.sharingfuelcard.sharingfuelcard.utils.SPUtiles;

/**
 * Created by 鹏祺 on 2017/10/24.
 */

public class LuncherActivity extends BaseActivity {
    public static void gotoLuncherActivity(Context context) {
        Intent intent = new Intent(context, LuncherActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserBean userBean = SPUtiles.getUser();
        String token = SPUtiles.getToken();
        if (TextUtils.isEmpty(token) || null == userBean) {
            LoginActivity.gotoLoginActivity(this);
        } else {
            MainActivity.gotoMainActivity(this);
        }
        this.finish();
    }
}
