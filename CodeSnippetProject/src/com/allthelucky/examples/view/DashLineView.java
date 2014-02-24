package com.allthelucky.examples.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 虚线控件
 * 
 * @author pxw
 * 
 */
public class DashLineView extends View {

    private static final int LINE_SPACE = 15;

    public DashLineView(Context context) {
        super(context);
    }

    public DashLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(0xffcccccc);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        PathEffect effects = new DashPathEffect(new float[] { 10, 10, 10, 10 }, 1);
        paint.setPathEffect(effects);

        int width = getWidth();
        Path path = new Path();

        for (int i = 0; i < (width / LINE_SPACE); i++) {
            int start = i * LINE_SPACE;
            path.moveTo(start + 2, 0);
            path.lineTo(start + 15, 0);
            canvas.drawPath(path, paint);
        }
        super.onDraw(canvas);
    }


}
