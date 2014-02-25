package cn.winfirm.client.api;

import cn.winfirm.client.oauth.OauthClient;

/**
 * 业务访问API基类
 * 
 * @author savant-pan(2012/11/02)
 * 
 */
public abstract class BaseApi {

	protected OauthClient client;

	/**
	 * 业务访问API基类构造
	 * 
	 * @param client
	 *            OauthClient对象
	 */
	protected BaseApi(OauthClient client) {
		this.client = client;
	}
}
