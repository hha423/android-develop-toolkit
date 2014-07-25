package com.example.persistentservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	private Intent service=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		service=new Intent(this, PersistentService.class);
		this.startService(service);
	}

	protected void onDestroy() {
		super.onDestroy();
		this.stopService(service);
	}
	
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		System.exit(0);
	}
	
}
