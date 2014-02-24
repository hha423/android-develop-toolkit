package com.allthelucky.examples.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

/**
 * 屏幕测试工具
 * 
 * @author pxw
 * 
 */
public class TestDisplayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display dp = manager.getDefaultDisplay();
        StringBuffer sb = new StringBuffer();
        sb.append("W=" + dp.getWidth() + ",H=" + dp.getHeight()).append(
                "\ndip in width=" + DensityUtil.px2dip(this, dp.getWidth()));
        System.out.println(sb.toString());
    }

    public static class DensityUtil {
        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
    }

}
