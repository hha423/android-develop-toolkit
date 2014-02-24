package com.allthelucky.examples.activity.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 调用
 */
public class AidlActivity extends Activity {
    ICommand cmd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind();

        Button bt = new Button(this);
        setContentView(bt);
        
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Data d = new Data();
                    d.vars = "init service...";
                    cmd.init(d);
                    cmd.request("hello, aidl test");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void bind() {
        Intent intent = new Intent("cn.winfirm.adiltest.CommandService");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void unbind() {
        unbindService(conn);
    }

    /**
     * 服务绑定通讯接口
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("disconnect");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("connect");
            cmd = ICommand.Stub.asInterface(service); // 取得调用接口
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind();
    }
}
