package com.example.android.jni;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		JNIHelper.helloWorld();
		JNIHelper.printString("hello, world!\n");
		JNIHelper.init(MainActivity.this);
		System.out.println("enc:"+JNIHelper.encode("123456"));
	}
}
