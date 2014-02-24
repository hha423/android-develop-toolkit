package com.allthelucky.examples.activity.service;

import android.app.Activity;
import android.os.Bundle;

/**
 * InfoService 测试
 */
public class TestbindActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("==================");

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2 * 1000);
                    InfoManager.setEnable(true);
                    InfoManager.showInfo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
