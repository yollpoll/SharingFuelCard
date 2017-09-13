package com.sharingfuelcard.sharingfuelcard.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 鹏祺 on 2017/9/13.
 */

public class Utils {
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
