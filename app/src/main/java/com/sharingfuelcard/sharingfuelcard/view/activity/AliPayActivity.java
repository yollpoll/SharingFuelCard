package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.AliPayBaseActivity;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;

/**
 * Created by 鹏祺 on 2017/10/19.
 */

public class AliPayActivity extends AliPayBaseActivity {
    private String payInfo;
    public static AliPayCallbackInterface callbackInterface;

    public static void gotoPay(Activity activity, String payInfo, AliPayCallbackInterface aliPayCallbackInterface) {
        Intent intent = new Intent(activity, AliPayActivity.class);
        intent.putExtra("payInfo", payInfo);
        activity.startActivity(intent);
        callbackInterface = aliPayCallbackInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        payInfo = getIntent().getStringExtra("payInfo");
        pay(payInfo);
    }

    @Override
    protected void paySuccess() {
        super.paySuccess();
        ToastUtils.showShort("支付成功");
        callbackInterface.paySuccess();
        this.finish();
    }

    @Override
    protected void payFail() {
        super.payFail();
        callbackInterface.payFail();
        this.finish();
    }

    @Override
    protected void payWaiting() {
        super.payWaiting();
        callbackInterface.payWaitting();
    }

    //支付回调
    public interface AliPayCallbackInterface {
        void paySuccess();

        void payFail();

        void payWaitting();
    }
}
