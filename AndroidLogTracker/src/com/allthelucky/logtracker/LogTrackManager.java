package com.allthelucky.logtracker;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.os.Handler;
import android.os.Message;

import com.android.http.RequestManager;
import com.android.http.RequestManager.RequestListener;

class LogTrackManager extends Handler implements LogTracker, RequestListener {

	private static volatile LogTrackManager TRACKER = null;

	private static final int MAX_COMMAND_LEN = 200;

	private static final String LOG_URL = "http://abc.com/servlet";

	private BlockingQueue<LogInfo> queue = new ArrayBlockingQueue<LogInfo>(MAX_COMMAND_LEN);

	private int index = 0;

	private LogParams mParams = null;

	private LogTrackManager() {
		this.mParams = new LogParams();
	}

	public static LogTracker getInstance() {
		if (null == TRACKER) {
			synchronized (LogTrackManager.class) {
				if (null == TRACKER) {

					TRACKER = new LogTrackManager();

				}
			}
		}
		return TRACKER;
	}

	public void handleMessage(Message msg) {
		checkTask();
	}

	@Override
	public LogParams getParams() {
		return this.mParams;
	}

	@Override
	public void track(int type, String programExcepMsg) {
		queue.add(new LogInfo());
	}

	@Override
	public void restore() {
		this.sendEmptyMessage(0);
	}

	@Override
	public void save() {

	}

	private void checkTask() {
		if (queue.size() > 0) {
			uploadTask();
		}
	}

	private void uploadTask() {
		index++;
		LogInfo info;
		try {
			info = queue.take();
			RequestManager.getInstance().post(LOG_URL, info.toString(), this, index);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onError(String arg0, String arg1, int arg2) {

	}

	@Override
	public void onRequest() {

	}

	@Override
	public void onSuccess(String arg0, String arg1, int arg2) {
		this.sendEmptyMessage(0);
	}
	

}
