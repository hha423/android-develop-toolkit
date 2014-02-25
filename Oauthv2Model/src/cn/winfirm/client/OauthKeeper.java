package cn.winfirm.client;

import cn.winfirm.client.oauth.AccessToken;
import cn.winfirm.client.oauth.OauthClient;

/**
 * 认证管理工具类
 * 
 * @author savant-pan(2012/11/02)
 * 
 */
public class OauthKeeper {
	/**
	 * 缓存对象
	 */
	public static OauthClient oauthClient;

	public static void setClient(OauthClient client) {
		oauthClient = client;
	}

	/**
	 * 取认证实例
	 */
	public static OauthClient getClient() {
		return oauthClient;
	}

	/**
	 * 访问令牌是否过期
	 */
	public static boolean validated() {
		return oauthClient != null && oauthClient.validate();
	}

	/**
	 * 更新访问令牌
	 * 
	 * @param token
	 * @return
	 */
	public static boolean refreshToken(AccessToken token) {
		if (oauthClient != null) {
			oauthClient.setAccessToken(token);
			return true;
		}
		return false;
	}

}
