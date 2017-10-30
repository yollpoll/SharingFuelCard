package com.sharingfuelcard.sharingfuelcard.base;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.sharingfuelcard.sharingfuelcard.module.PayResultBean;

/**
 * Created by 鹏祺 on 2017/10/19.
 */

public abstract class AliPayBaseActivity extends Activity {
    private static final int SDK_PAY_FLAG = 1;
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PayResultBean result = new PayResultBean((String) msg.obj);
            String resultInfo = result.getResult();// 同步返回需要验证的信息

            String resultStatus = result.getResultStatus();
            // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
            if (TextUtils.equals(resultStatus, "9000")) {
                paySuccess();
            } else {
                // 判断resultStatus 为非"9000"则代表可能支付失败
                // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                if (TextUtils.equals(resultStatus, "8000")) {
                    payWaiting();
                } else {
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                    payFail();
                }
            }
        }
    };

    /**
     * 支付
     */
    protected void pay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask aliPay = new PayTask(AliPayBaseActivity.this);
                String result = aliPay.pay(orderInfo, true);
                Message message = new Message();
                message.what = SDK_PAY_FLAG;
                message.obj = result;
                handler.sendMessage(message);

            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    protected void paySuccess() {
    }

    protected void payWaiting() {
    }

    protected void payFail() {
    }
}
