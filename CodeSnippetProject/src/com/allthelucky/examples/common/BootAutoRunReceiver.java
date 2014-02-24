package com.allthelucky.examples.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 开机广播接收器
 * 
 * @author pxw
 * 
 */
public class BootAutoRunReceiver extends BroadcastReceiver {

    private static final String TAG = "BootAutoRunReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "run message check service on boot");
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
           //do something
        }
    }
}
