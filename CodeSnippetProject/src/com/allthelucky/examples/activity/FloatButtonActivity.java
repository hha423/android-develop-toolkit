package com.allthelucky.examples.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

import com.allthelucky.examples.R;

/**
 * 浮动布局(button)测试
 */
public class FloatButtonActivity extends Activity {
    private WindowManager windowManager;
    private Handler handler = new Handler();
    private View floatView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_main);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        // 反解View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        floatView = inflater.inflate(R.layout.float_button, null);
        Button button = (Button) floatView.findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatButtonActivity.this, "touched", Toast.LENGTH_SHORT).show();
            }
        });

        // 添加View到窗口浮动
        handler.post(new Runnable() {
            public void run() {
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
                lp.gravity = Gravity.RIGHT | Gravity.TOP;
                windowManager.addView(floatView, lp);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (windowManager != null && floatView != null) {
            windowManager.removeViewImmediate(floatView);
        }
    }
}
