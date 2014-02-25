package cn.winfirm.client.oauth;

import java.io.Serializable;

import android.os.Bundle;

/**
 * Oauth认证实体
 * 
 * @author savant-pan(2012/11/02)
 * 
 */
public class OauthClient implements Serializable {
	private static final long serialVersionUID = 3340769831590298690L;

	public static final String OAUTH_VERSION = "OAUTH_V2";
	public final static String AUTH_HOST = "http://www.winfirm.net/api/authorise.asp";
	public final static String API_HOST = "http://www.winfirm.net/api/";

	private String clientId; // 申请应用时分配的app_key
	private String redirectUrl;// 授权回调地址

	private AccessToken accessToken = null;// 访问令牌

	public OauthClient() {

	}

	/**
	 * 构造认证实例
	 * 
	 * @param clientId
	 *            应用APP_ID
	 * @param redirectUrl
	 *            授权后回调地址
	 */
	public OauthClient(String clientId, String redirectUrl) {
		this.clientId = clientId;
		this.redirectUrl = redirectUrl;
	}

	/**
	 * 设置应用APP_ID
	 * 
	 * @param clientId
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * 设置应用授权后回调地址
	 * 
	 * @param redirectUrl
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * 设置访问令牌
	 * 
	 * @param accessToken
	 */
	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 取访问令牌
	 * 
	 * @return
	 */
	public AccessToken getAccessToken() {
		return accessToken;
	}

	/**
	 * 取得授权引导请求RUL地址
	 * 
	 * @return
	 */
	public String getAuthoriseUrl() {
		final StringBuffer sb = new StringBuffer();
		sb.append(AUTH_HOST).append("?client_id=").append(clientId)
				.append("&redirect_url=").append(redirectUrl);
		return sb.toString();
	}

	/**
	 * 解析授权后回调地址返回的参数
	 * 
	 * @param url
	 * @return
	 */
	public static Bundle parseToken(String url) {
		Bundle values = null;
		if (url != null && url.indexOf("#") != -1) {
			values = new Bundle();
			String tail = url.substring(url.indexOf("#") + 1, url.length());
			String map[] = tail.split("&");
			for (String item : map) {
				String m[] = item.split("=");
				values.putString(m[0], m[1]);
			}
		}
		return values;
	}

	/**
	 * 判断AccessToken是否过期
	 * 
	 * @return
	 */
	public boolean validate() {
		return accessToken != null && !accessToken.hasExpireIn();
	}
}
