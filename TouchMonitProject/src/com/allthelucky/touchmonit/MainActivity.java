package com.allthelucky.touchmonit;

import com.allthelucky.touchmoint.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TouchMonitLayout layout=new TouchMonitLayout(this);
		layout.addView(LayoutInflater.from(this).inflate(R.layout.activity_main, null));
		setContentView(layout);
	}
	
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}

}
