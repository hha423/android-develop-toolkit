package com.allthelucky.touchmoint;

import android.util.Log;

public class TouchMonitor implements Runnable {
	private static TouchMonitor INSTANCE = null;

	private final static int CHECK_TOUCH_INTERVAL = 1 * 2 * 1000;
	private final static int NOT_TOUCH_INTERVAL = 1 * 10 * 1000;
	
	private long lastTouchTime = 0;
	private Thread timeTask = null;

	private TouchMonitor() {
		this.lastTouchTime = System.currentTimeMillis();
		this.timeTask = new Thread(this);
		this.timeTask.start();
	}

	public static TouchMonitor getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TouchMonitor();

		}
		return INSTANCE;
	}

	public void update() {
		synchronized (TouchMonitor.class) {
			lastTouchTime = System.currentTimeMillis();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(CHECK_TOUCH_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (TouchMonitor.class) {
				long time = System.currentTimeMillis();
				if ((time - lastTouchTime) > NOT_TOUCH_INTERVAL) {
					Log.d("TAG", "time out!!!!!!!!!!!!!!!!!!!");
				}
			}
		}
	}

}