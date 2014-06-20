package com.allthelucky.memo.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
	private static final String APP_KEY = "123456789";
	private static final String APP_SECRET = "213246512adf55131s5fd3s155";

	/**
	 * 字节码数据(字符)转JSONObject JSONObject
	 * 
	 * @param data
	 * @return
	 */
	public static JSONObject bytesToJSONObject(byte[] data) {
		if (data == null)
			return null;
		return stringToJSONObject(byteToString(data));
	}

	/**
	 * 字节码数据 转 字符串工具
	 */
	public static String byteToString(byte[] data) {
		if (data == null)
			return null;
		try {
			return new String(data, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转JSONObject工具
	 * 
	 * @param json
	 * @return
	 */
	public static JSONObject stringToJSONObject(String json) {
		if (json == null)
			return null;
		try {
			return new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JSONObject build(String action, Map<String, Object> map) {
		final JSONObject spec = new JSONObject();
		try {
			spec.put("appkey", APP_KEY);
			spec.put("sign", sign(APP_KEY, APP_SECRET, action, map));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		final JSONObject info = new JSONObject();
		if (map != null && !map.isEmpty()) {
			Iterator<Entry<String, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = (Entry<String, Object>) it.next();
				try {
					info.put(entry.getKey().trim(), entry.getValue());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		final JSONObject data = new JSONObject();
		try {
			data.put("action", action);
			data.put("spec", spec);
			data.put("data", info);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 签名
	 * 
	 * @param appKey
	 * @param secret
	 * @param action 
	 * @param paramMap
	 * @return
	 */
	private static String sign(String appKey, String secret, String action, Map<String, Object> paramMap) {
		// 参数名排序
		String[] keyArray = paramMap.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);

		// 拼接参数
		StringBuilder stringBuilder = new StringBuilder(action);
		stringBuilder.append(appKey);
		for (String key : keyArray) {
			stringBuilder.append(key).append(paramMap.get(key));
		}

		stringBuilder.append(secret);
		String codes = stringBuilder.toString();

		// SHA-1签名
		// For Android
		String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toLowerCase();
		return sign;
	}

}
