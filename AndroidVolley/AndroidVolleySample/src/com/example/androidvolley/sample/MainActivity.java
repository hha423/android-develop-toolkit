package com.example.androidvolley.sample;

import android.app.Activity;
import android.os.Bundle;

import com.android.http.LoadControler;
import com.android.http.RequestManager;
import com.android.http.RequestManager.RequestListener;
import com.android.volley.utils.R;

public class MainActivity extends Activity {
	private LoadControler loadControler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		testPost();
		testGet();
	}
	
	/**
	 * test POST method
	 */
	private void testPost() {
		loadControler = RequestManager.getInstance().post("http://allthelucky.ap01.aws.af.cm/memoServer", null, requestListener, 0);
	}
	
	/**
	 * test GET method
	 */
	private void testGet() {
		loadControler = RequestManager.getInstance().get("http://allthelucky.ap01.aws.af.cm/memoServer", requestListener, 1);
	}

	/**
	 * RequestListener for receiving result
	 */
	private RequestListener requestListener = new RequestListener() {

		@Override
		public void onSuccess(String response, String url, int actionId) {
			System.out.println("actionId:"+actionId+", OnSucess!\n"+response);
		}

		@Override
		public void onError(String errorMsg, String url, int actionId) {
			System.out.println("actionId:"+actionId+", onError!\n"+errorMsg);
		}

		@Override
		public void onRequest() {
			
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		loadControler.cancel();
	}

}
