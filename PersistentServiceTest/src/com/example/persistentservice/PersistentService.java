package com.example.persistentservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 单独开一个进程来运行服务。<br/>
 * 1.START_TICKY方式，<br/>
 * 2.配置成android:process=":singleProcess",<br/>
 * 3.app设置android:persistent="true",<br/>
 * 4.setForeground(true)
 * 
 * @author stevent-pan
 * 
 */
public class PersistentService extends Service {

	private static final String TAG = "PersistentService";

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "-------------onStartCommand-----------------");
		return Service.START_STICKY;// When killed, auto restart
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "-------------onCreate-----------------");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "-------------onDestroy-----------------");
	}

}
