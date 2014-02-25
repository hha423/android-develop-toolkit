package cn.winfirm.client.oauth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 网页授权认证
 * 
 * @author savant-pan(2012/11/02)
 * 
 */
public class AuthWebViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		WebView webView = new WebView(this);
		setContentView(webView);
		setVebView(webView);
	}

	/**
	 * WebView配置设置
	 */
	private void setVebView(WebView webView) {
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setCacheMode(WebSettings.LOAD_NORMAL);

		webView.requestFocus();
		webView.setWebViewClient(webViewClient);
		webView.loadUrl(getIntent().getStringExtra("url"));
	}

	/**
	 * 回调监听
	 */
	private WebViewClient webViewClient = new WebViewClient() {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			System.out.println("URL = " + url);
			if (!(url = url.replace("%3D", "=")).equals("")
					&& url.indexOf("access_token=") != -1) {
				Intent intent = new Intent();
				intent.putExtra("url", url);
				setResult(0, intent);

				view.destroyDrawingCache();
				view.destroy();
				finish();
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
	};

}
