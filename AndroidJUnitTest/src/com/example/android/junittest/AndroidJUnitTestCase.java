package com.example.android.junittest;

import android.content.Context;
import android.test.AndroidTestCase;

/**
 * AndroidTestCase Usage
 * 
 * @author steven-pan
 * 
 */
public class AndroidJUnitTestCase extends AndroidTestCase {

	private static final String message = "Hello AndroidJUnitTestCase";

	public void testStaticMethod() {
		AndroidJUnitTest.testStaticPrintln(message);
	}

	public void testMethod() {
		AndroidJUnitTest test = new AndroidJUnitTest();
		test.testPrintln(message);
	}

	public void testContext() {
		AndroidJUnitTest test = new AndroidJUnitTest();
		Context context = this.getContext();
		test.testToast(context, message);
	}

}
