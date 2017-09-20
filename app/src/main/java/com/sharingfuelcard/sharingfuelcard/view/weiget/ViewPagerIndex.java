package com.sharingfuelcard.sharingfuelcard.view.weiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.sharingfuelcard.sharingfuelcard.utils.Utils;

/**
 * Created by 鹏祺 on 2017/9/13.
 */

public class ViewPagerIndex extends LinearLayout {
    private int indexCount;
    private int currentCount;
    private Paint mPaint;
    private float width, heigh;

    public ViewPagerIndex(Context context) {
        super(context);
        init();
    }

    public ViewPagerIndex(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewPagerIndex(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setIndexCount(int indexCount) {
        this.indexCount = indexCount;
        invalidate();
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
        invalidate();
    }

    private void init() {
        mPaint = new Paint();
//        mPaint.setStrokeWidth(ScreenUtils.calculateDpToPx(2, getContext()));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("spq", "zzzzzzzzzzz");
        width = getMeasuredWidth();
        heigh = getMeasuredHeight();
        for (int i = 0; i < indexCount; i++) {
            if (i == currentCount) {
                Log.d("spq", "xxxx");
                mPaint.setColor(Color.parseColor("#999999"));
            } else {
                mPaint.setColor(Color.parseColor("#eeeeee"));
                Log.d("spq", "yyyy");
            }
            canvas.drawCircle(i * (width / 3), 0, Utils.calculateDpToPx(2, getContext()), mPaint);
        }
    }
}
