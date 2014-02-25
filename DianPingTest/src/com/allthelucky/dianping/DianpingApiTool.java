package com.allthelucky.dianping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 大众点评API工具
 */
public class DianpingApiTool {
	/**
	 * 大众点评应用App Key
	 */
	private final static String appKey = "xxxxxxxx";

	/**
	 * 大众点评App Secret
	 */
	private final static String secret = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

	/**
	 * * 请求API
	 * 
	 * @param apiUrl
	 *            接口地址
	 * @param paramMap
	 *            参数列表
	 * @return
	 */
	public static String requestApi(String apiUrl, Map<String, String> paramMap) {
		if(paramMap!=null) {
			paramMap.put("format", "json");
		}
		return apiUrl + "?" + getQueryString(appKey, secret, paramMap);
	}
	
	/**
	 * 获取请求字符串
	 * 
	 * @param appKey
	 * @param secret
	 * @param paramMap
	 * @return
	 */
	private static String getQueryString(String appKey, String secret, Map<String, String> paramMap) {
		return getUrlEncodedQueryString(appKey, secret, paramMap);
	}

	/**
	 * 获取请求字符串，参数值进行UTF-8处理
	 * 
	 * @param appKey
	 * @param secret
	 * @param paramMap
	 * @return
	 */
	private static String getUrlEncodedQueryString(String appKey, String secret, Map<String, String> paramMap) {
		String sign = sign(appKey, secret, paramMap);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
		for (Entry<String, String> entry : paramMap.entrySet()) {
			try {
				stringBuilder.append('&').append(entry.getKey()).append('=')
						.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
			}
		}
		String queryString = stringBuilder.toString();
		return queryString;
	}

	/**
	 * 签名
	 * 
	 * @param appKey
	 * @param secret
	 * @param paramMap
	 * @return
	 */
	private static String sign(String appKey, String secret, Map<String, String> paramMap) {
		// 参数名排序
		String[] keyArray = paramMap.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);

		// 拼接参数
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(appKey);
		for (String key : keyArray) {
			stringBuilder.append(key).append(paramMap.get(key));
		}

		stringBuilder.append(secret);
		String codes = stringBuilder.toString();

		// SHA-1签名
		// For Android
		String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toUpperCase();

		return sign;
	}
}