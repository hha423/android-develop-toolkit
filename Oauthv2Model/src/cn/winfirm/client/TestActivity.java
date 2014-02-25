package cn.winfirm.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import cn.winfirm.client.api.RequestApi;
import cn.winfirm.client.oauth.AccessToken;
import cn.winfirm.client.oauth.AuthWebViewActivity;
import cn.winfirm.client.oauth.OauthClient;

/**
 * 测试模拟OAUTH授权认证机制
 * 
 * @author savant-pan(2012/11/02)
 * 
 */
public class TestActivity extends Activity {
	public static String clientId = "1234567890";// appkey
	public static String redirectUrl = "http://www.winfirm.net/api/test.asp";// callback_url

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		check();
	}

	/**
	 * 初始化
	 */
	public void check() {
		if (!OauthKeeper.validated()) {// 首次认证
			OauthClient client = new OauthClient();
			client.setClientId(clientId);
			client.setRedirectUrl(redirectUrl);
			OauthKeeper.setClient(client);

			authorise();
		}
	}

	/**
	 * 调用API
	 */
	public void requestApi() {
		if (!OauthKeeper.validated()) {
			authorise();
		} else {
			RequestApi api = new RequestApi(OauthKeeper.getClient());
			api.getAccountInfo();
		}
	}

	/**
	 * 请求授权认证
	 */
	private void authorise() {
		Intent intent = new Intent(this, AuthWebViewActivity.class);
		intent.putExtra("url", OauthKeeper.getClient().getAuthoriseUrl());
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			final String url = data.getStringExtra("url");
			Bundle values = OauthClient.parseToken(url);

			AccessToken token = new AccessToken();
			token.setAccessToken(values.getString("access_token"));
			token.setExpireIn(values.getString("expire_in"));
			token.setOpenId(values.getString("open_id"));

			if (OauthKeeper.refreshToken(token)) {
				showToast("successful.");
			} else {
				showToast("failed.");
			}
		} else {
			showToast("failed.");
		}
	}

	private void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

}
