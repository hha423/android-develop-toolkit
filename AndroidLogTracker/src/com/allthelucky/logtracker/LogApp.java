package com.allthelucky.logtracker;

import java.lang.Thread.UncaughtExceptionHandler;

import com.android.http.RequestManager;

import android.app.Application;

public class LogApp extends Application implements UncaughtExceptionHandler {

	public void onCreate() {
		super.onCreate();
		this.registe();
	}

	public void registe() {
		RequestManager.getInstance().init(LogApp.this);
		LogTrackManager.getInstance().restore();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		LogTrackManager.getInstance().track(LogTrackManager.PROGRAME_TYPE, getStackMessage(ex));
	}

	private String getStackMessage(Throwable ex) {
		StringBuffer expSb = new StringBuffer();
		expSb.append(ex.getLocalizedMessage() + "\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			expSb.append(elements[i].toString() + "\n");
		}
		return expSb.toString();
	}

	protected void destory() {
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(10);
	}

}
