package com.allthelucky.examples.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 可嵌在ScrollView中的GridView，ListView类似
 * 
 * @author savant
 * 
 */
public class UnscrollableGridView extends GridView {

    public UnscrollableGridView(Context context) {
        super(context);
    }

    public UnscrollableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnscrollableGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 禁止滚动条出现
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    
    @Override
    public boolean isFocused() {
        return false;
    }

}
