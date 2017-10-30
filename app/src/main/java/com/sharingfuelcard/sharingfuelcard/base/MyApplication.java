package com.sharingfuelcard.sharingfuelcard.base;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by 鹏祺 on 2017/9/14.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "uSGaLefrtkkjSTFeCgGE52jG-gzGzoHsz", "rqAg1uY27n25kUWRxQVPSDSp");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
        instance = this;
    }
}
