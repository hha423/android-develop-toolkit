package com.allthelucky.http;

/**
 * Request Listener for AsyncHttpResponseHandler
 * 
 * @author savant-pan
 * 
 */
public interface RequestListener {
	/**
	 * Status OK
	 */
	public final static int OK = 0;
	/**
	 * Status ERR
	 */
	public final static int ERR = 1;

	/**
	 * callback when Request start
	 */
	void onStart();

	/**
	 * callback when Request end
	 * 
	 * @param data
	 *            byte array if any
	 * @param statusCode
	 *            Request Status
	 * @param description
	 *            description
	 * @param actionId
	 *            request identifier
	 */
	void onCompleted(int statusCode, byte[] data, long lastModified, String description, int actionId);
}
