package com.allthelucky.touchmonit;

import android.app.Activity;
import android.view.LayoutInflater;

public class BaseActivity extends Activity {
	private TouchMonitLayout touchMonitLayout;

	/**
	 * 是否忽略检测
	 * 
	 * @return
	 */
	protected boolean isIgnoreAttach() {
		return false;
	}

	public void setContentView(int layoutResID) {
		if (!isIgnoreAttach()) {
			touchMonitLayout = new TouchMonitLayout(BaseActivity.this);
			touchMonitLayout.addView(LayoutInflater.from(this).inflate(layoutResID, null));
			super.setContentView(touchMonitLayout);
		} else {
			super.setContentView(layoutResID);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (touchMonitLayout != null) {
			touchMonitLayout.setEnable(BaseActivity.this, isIgnoreAttach());
		}
	}
}
