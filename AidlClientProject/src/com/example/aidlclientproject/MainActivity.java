package com.example.aidlclientproject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidl.ISecurityService;

public class MainActivity extends Activity {
	private TextView text;
	private ISecurityService securityService;

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
			securityService = ISecurityService.Stub.asInterface(service); // 取得调用接口
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			String result = "";
			try {
				result = securityService.encode("asdfasfsaf");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println(result);
			
			try {
				result=securityService.decode(result);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println(result);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent("com.whty.qd.encode.SecurityService");
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
		text = new TextView(this);
		text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
		setContentView(text);

		text.setText("You can change this activity and generate the unsigned apk file to ActivityLoader/assets/apks. The SampleActivity will be replaced.");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
	}
}
