package com.example.androidvolley.sample;

import com.android.http.RequestManager;

import android.app.Application;

public class VolleyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		RequestManager.getInstance().init(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
}
