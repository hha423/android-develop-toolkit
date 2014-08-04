package com.example.android.junittest;

import android.content.Context;
import android.widget.Toast;

public class AndroidJUnitTest {

	private static final String TAG = "AndroidJUnitTest";

	public static void testStaticPrintln(String message) {
		android.util.Log.d(TAG, message);
	}

	public void testPrintln(String message) {
		android.util.Log.d(TAG, message);
	}

	public void testToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

}
