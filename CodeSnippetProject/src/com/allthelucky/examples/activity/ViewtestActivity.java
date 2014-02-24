
package com.allthelucky.examples.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

public class ViewtestActivity extends Activity {
    private MyView view;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MyView(this);
        setContentView(view);
        
        //time delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }
    
    //refresh
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            view.reset("fuck,text changed.....");
        }
    };
    

   private static class MyView extends View {
        private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private String text;

        public MyView(Context context) {
            super(context);
            init();
        }

        public void init() {
            text = "default text";
        }

        //change text
        public void reset(String string) {
            text = string;
            postInvalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawColor(Color.WHITE);
            mPaint.setLinearText(true);
            for (int i = 0; i < 5; i++) {
                canvas.drawText(text, 10, 100 * (i + 1), mPaint);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                text = event.getX() + "," + event.getY();
            }  
            
            invalidate();
            return super.onTouchEvent(event);
        }

    }

}
