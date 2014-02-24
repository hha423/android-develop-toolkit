package com.allthelucky.examples.activity.service;

import android.content.Context;
import android.content.Intent;

public class CheckUtils {
    /**
     * 开启或关闭服务
     * 
     * @param context
     */
    public static void updatePushService(Context context, boolean flag) {
        Intent service = new Intent(context, CheckService.class);
        try {
            if (!flag) {
                context.stopService(service);
            } else {
                context.startService(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
