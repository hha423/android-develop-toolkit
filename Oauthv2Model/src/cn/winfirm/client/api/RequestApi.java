package cn.winfirm.client.api;

import cn.winfirm.client.oauth.OauthClient;

/**
 * 业务访问Api具体实现类
 * 
 * @author savant-pan(2012/11/02)
 * 
 */
public class RequestApi extends BaseApi {

	public RequestApi(OauthClient client) {
		super(client);
	}

	// 访问时带上accessToken及open_id
	public void getAccountInfo() {

	}

	public void getArticles() {

	}

	public void postArticle(String msg) {

	}

}
