package com.sharingfuelcard.sharingfuelcard.base;

import android.app.Application;

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
}
