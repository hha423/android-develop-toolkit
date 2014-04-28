package com.allthelucky.touchmonit;

import android.os.Bundle;

import com.allthelucky.touchmoint.R;

public class TouchMonitActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}

}
