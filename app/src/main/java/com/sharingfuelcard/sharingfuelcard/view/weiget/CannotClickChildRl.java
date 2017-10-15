package com.sharingfuelcard.sharingfuelcard.view.weiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by 鹏祺 on 2017/10/13.
 */

public class CannotClickChildRl extends RelativeLayout {
    public CannotClickChildRl(Context context) {
        super(context);
    }

    public CannotClickChildRl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CannotClickChildRl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
