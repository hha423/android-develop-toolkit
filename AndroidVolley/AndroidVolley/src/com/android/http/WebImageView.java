package com.android.http;

import android.content.Context;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;

public class WebImageView extends NetworkImageView {

	public WebImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public WebImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WebImageView(Context context) {
		super(context);
	}
}
