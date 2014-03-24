package com.allthelucky.localprovider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	private WebView wb;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.wb= (WebView)findViewById(R.id.webView1);
		this.wb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		this.wb.requestFocus();
		WebSettings settings = wb.getSettings();
		settings.setJavaScriptEnabled(true); // 启用JS脚本
		settings.setJavaScriptCanOpenWindowsAutomatically(true);// 允许js弹出窗口
		
		this.wb.setWebViewClient(new WapWebViewClient());
		this.wb.loadUrl("file:///android_asset/test.html");
	}
	
	private class WapWebViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			Log.d("onPageStarted", url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			Log.d("onPageFinished", url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			Log.d("onReceivedError", failingUrl);
		}
		
		public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
			Log.d("shouldOverrideUrlLoading",  url);
			if (url.startsWith("abc.com")) {// 以浏览器打开
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);
				return true;
			} else {// 以当前Webview打开
				view.loadUrl(url);
				return true;
			}
		}
	};

}
