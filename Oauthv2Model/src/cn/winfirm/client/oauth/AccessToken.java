package cn.winfirm.client.oauth;

import java.io.Serializable;

/**
 * 访问令牌实体类
 * 
 * @author savant-pan(2012/11/02)
 * 
 */
public class AccessToken implements Serializable {
	private static final long serialVersionUID = 180181354769469211L;
	private String accessToken = null;// 访问口令
	private long expireIn = 0l;// 过期时间
	private String openId = null;// 授权成功后的API开放调用ID

	private long start = 0l;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 判断是否过期
	 */
	public boolean hasExpireIn() {
		return System.currentTimeMillis() - (start + expireIn + 15) > 0;
	}

	public void setExpireIn(String expireIn) {
		this.start = System.currentTimeMillis();
		this.expireIn = Long.parseLong(expireIn);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
