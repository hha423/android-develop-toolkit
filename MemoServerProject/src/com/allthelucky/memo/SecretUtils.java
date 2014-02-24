package com.allthelucky.memo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * appkey及Secret对管理工具
 * 
 * @author pxw
 * 
 */
public class SecretUtils {
	private final static Map<String, String> KEY_MAP = new HashMap<String, String>();

	static {
		KEY_MAP.put("123456789", "213246512adf55131s5fd3s155");
	}

	public static boolean hasKey(String appkey) {
		return KEY_MAP != null && KEY_MAP.containsKey(appkey);
	}

	public static String getSecret(String appkey) {
		return KEY_MAP != null ? KEY_MAP.get(appkey) : "";
	}

	/**
	 * SHA1签名计算
	 * 
	 * @param appKey
	 *            应用key
	 * @param secret
	 *            应用secret
	 * @param action
	 *            访问action
	 * @param paramMap
	 *            参数
	 * @return
	 */
	public static String sign(String appKey, String secret, String action, Map<String, Object> paramMap) {
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
		String sign = new String(Hex.encodeHex(DigestUtils.sha(codes)));
		return sign;
	}
}
