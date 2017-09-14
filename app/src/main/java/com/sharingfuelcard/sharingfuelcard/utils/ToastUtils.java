package com.sharingfuelcard.sharingfuelcard.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.sharingfuelcard.sharingfuelcard.base.MyApplication;

/**
 * Created by xlm on 2016/1/15.
 */
public class ToastUtils {
    public static void showShort(String content){
        Toast.makeText(MyApplication.getInstance().getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }
    public static void SnakeShowShort(View view,String content){
        Snackbar.make(view,content, Snackbar.LENGTH_SHORT).show();
    }
}
