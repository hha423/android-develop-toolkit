package com.allthelucky.examples.activity.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * 消息检测服务
 * 
 * @author pxw
 * 
 */
public class CheckService extends Service {
    private static final String TAG = "MessageCheckService";
    private ScheduledExecutorService schedualService = Executors.newScheduledThreadPool(1);
    private long initialDelay = 5 * 1000;
    private long delay = 30 * 60 * 1000;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MessageCheckService create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MessageCheckService destroy");
        if (schedualService != null) {
            try {
                schedualService.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MessageCheckService start");
        if (this.schedualService == null) {
            this.schedualService = Executors.newScheduledThreadPool(1);
        }

        this.schedualService.scheduleWithFixedDelay(command, initialDelay, delay, TimeUnit.MILLISECONDS);
        return START_STICKY;
    }

    /**
     * 执行Task
     */
    private Runnable command = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "check message");
                handler.sendEmptyMessage(0);
        }
    };

    /**
     * 请求开始
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            
        }
    };

}
