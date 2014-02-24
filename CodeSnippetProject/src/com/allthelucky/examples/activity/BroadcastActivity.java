package com.allthelucky.examples.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class BroadcastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }
    
    //send broadcast
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Intent intent=new Intent();
            intent.setAction("com.msg.come");
            intent.putExtra("msg", "hello,world!!!");
            sendBroadcast(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //register
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.msg.come");
        this.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        
        //unregister
        this.unregisterReceiver(broadcastReceiver);
    }
    
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null && intent.getAction().equals("com.msg.come")) {
                Toast.makeText(getApplicationContext(), intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
            }
        }
    };

}
