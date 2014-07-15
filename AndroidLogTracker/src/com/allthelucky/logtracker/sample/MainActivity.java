package com.allthelucky.logtracker.sample;

import android.app.Activity;
import android.os.Bundle;

import com.allthelucky.logtracker.LogFactory;
import com.allthelucky.logtracker.LogTracker;
import com.allthelucky.logtracker.R;

public class MainActivity extends Activity {
	LogTracker mLogTracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mLogTracker = LogFactory.getLogger();
		mLogTracker.getParams().setLevel(LogTracker.LOG_LEVEL_VERBOSE);

		mLogTracker.track(LogTracker.PROGRAME_TYPE, "asdfasdf");
	}

}
