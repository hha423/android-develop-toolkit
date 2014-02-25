/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.loopj.android.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Used to intercept and handle the responses from requests made using
 * {@link AsyncHttpClient}. The {@link #onSuccess(String)} method is designed to
 * be anonymously overridden with your own response handling code.
 * <p>
 * Additionally, you can override the {@link #onFailure(Throwable, String)},
 * {@link #onStart()}, and {@link #onFinish()} methods as required.
 * <p>
 * For example:
 * <p>
 * 
 * <pre>
 * AsyncHttpClient client = new AsyncHttpClient();
 * client.get(&quot;http://www.google.com&quot;, new AsyncHttpResponseHandler() {
 * 	&#064;Override
 * 	public void onStart() {
 * 		// Initiated the request
 * 	}
 * 
 * 	&#064;Override
 * 	public void onSuccess(String response) {
 * 		// Successfully got a response
 * 	}
 * 
 * 	&#064;Override
 * 	public void onFailure(Throwable e, String response) {
 * 		// Response failed :(
 * 	}
 * 
 * 	&#064;Override
 * 	public void onFinish() {
 * 		// Completed the request (either success or failure)
 * 	}
 * });
 * </pre>
 */
public class AsyncHttpResponseHandler {
	protected static final int SUCCESS_MESSAGE = 0;
	protected static final int FAILURE_MESSAGE = 1;
	protected static final int START_MESSAGE = 2;
	protected static final int FINISH_MESSAGE = 3;

	private Handler handler;

	/**
	 * Creates a new AsyncHttpResponseHandler
	 */
	public AsyncHttpResponseHandler() {
		// Set up a handler to post events back to the correct thread if
		// possible
		if (Looper.myLooper() != null) {
			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					AsyncHttpResponseHandler.this.handleMessage(msg);
				}
			};
		}
	}

	//
	// Callbacks to be overridden, typically anonymously
	//

	//
	// Pre-processing of messages (executes in background threadpool thread)
	//
	protected void sendStartMessage() {
		sendMessage(obtainMessage(START_MESSAGE, null));
	}

	protected void sendSuccessMessage(int statusCode, Header[] headers, byte[] responseBody, long lastModified) {
		sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[] { new Integer(statusCode), headers, responseBody,
				lastModified }));
	}

	protected void sendFailureMessage(Throwable e, String responseBody) {
		sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[] { e, responseBody }));
	}

	// Methods which emulate android's Handler and Message methods
	protected void handleMessage(Message msg) {
		Object[] response;
		switch (msg.what) {
		case START_MESSAGE:
			onStart();
			break;
		case SUCCESS_MESSAGE:
			response = (Object[]) msg.obj;
			onSuccess(((Integer) response[0]).intValue(), (Header[]) response[1], (byte[]) response[2],
					(Long) response[3]);
			break;
		case FAILURE_MESSAGE:
			response = (Object[]) msg.obj;
			onFailure((Throwable) response[0], (String) response[1]);
			break;
		}
	}

	/**
	 * Fired when the request is started, override to handle in your own code
	 */
	public void onStart() {
	}

	protected void onSuccess(int intValue, Header[] headers, byte[] response, long lastModified) {

	}

	protected void onFailure(Throwable throwable, String errorMsg) {

	}

	protected void sendMessage(Message msg) {
		if (handler != null) {
			handler.sendMessage(msg);
		} else {
			handleMessage(msg);
		}
	}

	protected Message obtainMessage(int responseMessage, Object response) {
		Message msg = null;
		if (handler != null) {
			msg = this.handler.obtainMessage(responseMessage, response);
		} else {
			msg = Message.obtain();
			msg.what = responseMessage;
			msg.obj = response;
		}
		return msg;
	}

	// Interface to AsyncHttpRequest
	void sendResponseMessage(HttpResponse response) {
		StatusLine status = response.getStatusLine();
		long lastModified = -1l;
		try {
			final String last = response.getLastHeader("last-modified").getValue();
			if (null != last && !"".equals(last)) {
				java.util.Date d = new java.util.Date(last);
				lastModified = d.getTime();
			}
		} catch (Exception e) {
		}

		boolean modified = isLastModified(lastModified);
		if (!modified) {
			sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), loadCache(), lastModified);
			return;
		}

		byte[] responseBody = null;
		try {
			HttpEntity entity = null;
			HttpEntity temp = response.getEntity();

			if (temp != null) {
				entity = new BufferedHttpEntity(temp);
				responseBody = EntityUtils.toByteArray(entity);
			}
		} catch (IOException e) {
			sendFailureMessage(e, null);
			return;
		}

		if (status.getStatusCode() >= 300) {
			sendFailureMessage(new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()),
					status.getReasonPhrase());
		} else {
			sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), responseBody, lastModified);
		}
	}

	protected boolean isLastModified(long lastModified) {
		return true;
	}

	protected byte[] loadCache() {
		return null;
	}
	
}
