package com.android.http;

import java.io.UnsupportedEncodingException;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

/**
 * 
 * @author steven pan
 * 
 */
public class RequestManager {
	private static final String CHARSET_UTF_8 = "UTF-8";
	private volatile static RequestManager instance = null;
	private RequestQueue mRequestQueue = null;

	public interface RequestListener {
		void onRequest();

		void onSuccess(String response, String url, int actionId);

		void onError(String errorMsg, String url, int actionId);
	}

	private RequestManager() {

	}

	public void init(Context context) {
		this.mRequestQueue = Volley.newRequestQueue(context);
	}

	public static RequestManager getInstance() {
		if (null == instance) {
			synchronized (RequestManager.class) {
				if (null == instance) {
					instance = new RequestManager();
				}
			}
		}
		return instance;
	}

	/**
	 * default get method
	 * @param url
	 * @param requestListener
	 * @param actionId
	 * @return
	 */
	public LoadControler get(String url, RequestListener requestListener, int actionId) {
		return this.get(url, requestListener, true, actionId);
	}
	
	public LoadControler get(String url, RequestListener requestListener, boolean shouldCache, int actionId) {
		return this.request(Method.GET, url, null, requestListener, shouldCache, 10 * 1000, 1, actionId);
	}

	/**
	 * default post method
	 * @param url
	 * @param data
	 * @param requestListener
	 * @param actionId
	 * @return
	 */
	public LoadControler post(final String url, String data, final RequestListener requestListener, int actionId) {
		return this.post(url, data, requestListener, false, 10 * 1000, 1, actionId);
	}

	public LoadControler post(final String url, String data, final RequestListener requestListener,
			boolean shouldCache, int timeoutCount, int retryTimes, int actionId) {
		return request(Method.POST, url, data, requestListener, shouldCache, timeoutCount, retryTimes, actionId);
	}

	public LoadControler request(int method, final String url, String data, final RequestListener requestListener,
			boolean shouldCache, int timeoutCount, int retryTimes, int actionId) {
		return this.sendRequest(method, url, data, new LoadListener() {
			@Override
			public void onStart() {
				requestListener.onRequest();
			}

			@Override
			public void onSuccess(byte[] data, String url, int actionId) {
				String parsed = null;
				try {
					parsed = new String(data, CHARSET_UTF_8);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				requestListener.onSuccess(parsed, url, actionId);
			}

			@Override
			public void onError(String errorMsg, String url, int actionId) {
				requestListener.onError(errorMsg, url, actionId);
			}
		}, shouldCache, timeoutCount, retryTimes, actionId);
	}

	public LoadControler sendRequest(int method, final String url, String data, final LoadListener requestListener,
			boolean shouldCache, int timeoutCount, int retryTimes, int actionId) {
		if (requestListener == null)
			throw new NullPointerException();

		final ByteArrayLoadControler loadControler = new ByteArrayLoadControler(requestListener, actionId);
		Request<?> request = new ByteArrayRequest(method, url, data, loadControler, loadControler);
		request.setShouldCache(shouldCache);

		RetryPolicy retryPolicy = new DefaultRetryPolicy(timeoutCount, retryTimes,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		request.setRetryPolicy(retryPolicy);

		loadControler.bindRequest(request);

		if (this.mRequestQueue == null)
			throw new NullPointerException();
		requestListener.onStart();
		this.mRequestQueue.add(request);

		return loadControler;
	}

}
