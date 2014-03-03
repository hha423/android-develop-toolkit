package com.allthelucky.touchmoint;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class TouchMonitLayout extends FrameLayout {
	private static final String TAG = "TouchMonitLayout";
	private TouchMonitor touchMoniter = null;

	public TouchMonitLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.init();
	}

	public TouchMonitLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init();
	}

	public TouchMonitLayout(Context context) {
		super(context);
		this.init();
	}

	protected void init() {
		touchMoniter = TouchMonitor.getInstance();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent");
		touchMoniter.update();

		return super.onInterceptTouchEvent(ev);
	}

}
