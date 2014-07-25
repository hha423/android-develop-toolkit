package com.example.handler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.example.persistentservice.R;

/**
 * UI Handler与子线程Handler操作测试
 * 
 * @author steven-pan
 * 
 */
public class MainActivity extends Activity {
	private Bitmap bm;
	private ImageView imgView;
	private Handler mMainHandler, mChildHandler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.imgView = (ImageView) this.findViewById(R.id.pic);

		// 主线程中的Handler，已绑定了UI线程的消息队列
		this.mMainHandler = new Handler() {
			public void handleMessage(Message msg) {//主线程中更新UI
				imgView.setImageBitmap(bm);
			}
		};

		this.findViewById(R.id.take).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mChildHandler.sendEmptyMessage(0);//发送消息给子线程Handler，通知其进行耗时操作
			}
		});

		new ChildThread().start();
	}

	class ChildThread extends Thread {

		public void run() {
			Looper.prepare();// 子线程中创建Handler，需要自行初化消息队列
			mChildHandler = new Handler() {
				public void handleMessage(Message msg) {
					bm = takeScreenShot(MainActivity.this);//子线程中的handleMessage进行耗时操作。
					mMainHandler.sendEmptyMessage(0);//发送给主线程Handler，通知其更新UI
				}
			};
			Looper.loop();//启动消息循环队列
		}
	}

	// 获取Activity的截屏
	protected static Bitmap takeScreenShot(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b = view.getDrawingCache();
		Bitmap bb = Bitmap.createBitmap(b);
		view.destroyDrawingCache();
		return bb;
	}

	public void onDestroy() {
		mChildHandler.getLooper().quit();// 用完结束消息队列循环
		super.onDestroy();
	}

	public void onBackPressed() {
		if (bm != null && !bm.isRecycled()) {
			bm.recycle();
		}
		super.onBackPressed();
	}

}
