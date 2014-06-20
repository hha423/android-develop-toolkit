package com.android.http;

import java.io.UnsupportedEncodingException;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

class ByteArrayRequest extends Request<byte[]> {
	private final Listener<byte[]> mListener;
	private String mPostBody = null;

	public ByteArrayRequest(int method, String url, String postBody, Listener<byte[]> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mPostBody = postBody;
		this.mListener = listener;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		if (this.mPostBody != null && this.mPostBody.length() != 0) {
			try {
				return this.mPostBody.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return super.getBody();
	}

	@Override
	protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
		return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	protected void deliverResponse(byte[] response) {
		this.mListener.onResponse(response);
	}
}