package com.allthelucky.examples.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * 单步滑动Gallery
 * 
 * @author pxw
 * 
 */
public class StepGallery extends Gallery {

    public StepGallery(Context context) {
        super(context);
    }

    public StepGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StepGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (velocityX > 0) {// 往左边滑动
            super.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
        } else {// 往右边滑动
            super.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
        }
        return false;
    }

}
