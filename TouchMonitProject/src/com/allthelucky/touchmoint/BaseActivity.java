package com.allthelucky.touchmoint;

import android.app.Activity;
import android.view.LayoutInflater;

public class BaseActivity extends Activity {
	public TouchMonitLayout touchMonitLayout;

	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		try {
			this.touchMonitLayout = (TouchMonitLayout) LayoutInflater.from(BaseActivity.this)
					.inflate(layoutResID, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			this.touchMonitLayout.pause();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			this.touchMonitLayout.resume();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
