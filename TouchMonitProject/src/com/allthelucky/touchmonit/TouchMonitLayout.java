package com.allthelucky.touchmonit;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

public class TouchMonitLayout extends FrameLayout {
	private static final String TAG = "TouchMonitLayout";
	private final static int CHECK_TOUCH_INTERVAL = 1 * 500;
	private final static int NOT_TOUCH_INTERVAL = 1 * 60 * 1000;
	
	public TouchMonitLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TouchMonitLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TouchMonitLayout(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent");
		TouchMonitHandler.getInstance().uddateTouchStatus();
		return super.onInterceptTouchEvent(ev);
	}
	
	public void update() {
		TouchMonitHandler.getInstance().uddateTouchStatus();
	}
	
	public void setEnable(BaseActivity activity, boolean isIgnoreAttach) {
		TouchMonitHandler.getInstance().setEnable(activity, isIgnoreAttach);
	}
	
	static class TouchMonitHandler extends Handler implements Runnable {
		private static TouchMonitHandler INSTANCE = null;

		private long lastTouchTime = 0;
		private Thread timeTask = null;

		private BaseActivity currentActivity = null;
		private boolean isIgnoreAttach = false;

		private TouchMonitHandler() {
			this.lastTouchTime = System.currentTimeMillis();
			this.timeTask = new Thread(this);
			this.timeTask.start();
		}

		public static TouchMonitHandler getInstance() {
			if (INSTANCE == null) {
				INSTANCE = new TouchMonitHandler();

			}
			return INSTANCE;
		}

		public void setEnable(BaseActivity activity, boolean isIgnoreAttach) {
			this.currentActivity = activity;
			this.isIgnoreAttach = isIgnoreAttach;

			Log.d("TAG", "currentActivity:" + currentActivity + "," + isIgnoreAttach);
		}

		public void uddateTouchStatus() {
			synchronized (TouchMonitHandler.class) {
				lastTouchTime = System.currentTimeMillis();
			}
		}

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Toast.makeText(currentActivity, "time out", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(CHECK_TOUCH_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Log.d("TAG", "check timeout...");
				if (isIgnoreAttach) {
					Log.d("TAG", "Ignore touch Attach...");
				} else {
					synchronized (TouchMonitHandler.class) {
						long time = System.currentTimeMillis();
						if ((time - lastTouchTime) > NOT_TOUCH_INTERVAL) {
							Log.d("TAG", "timeout...");
							sendEmptyMessage(0);
						} else {
							Log.d("TAG", "not timeout...");
						}
					}
				}
			}
		}

	}

}
