package com.allthelucky.dianping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class StringUtils {
	/**
	 * 加载raw文本文件，转换成String类型
	 * 
	 * @param context
	 * @param rawId
	 * @return
	 * @throws IOException
	 */
	public static String loadAssetsText(Context context, int rawId) {
		InputStream inputStream = context.getResources().openRawResource(rawId);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte[] bytes = new byte[4096];
		int len = 0;
		try {
			while ((len = inputStream.read(bytes)) > 0) {
				byteStream.write(bytes, 0, len);
			}

			return new String(byteStream.toByteArray(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字节码转JSONObject
	 * 
	 * @param data
	 *            字节码（源为字符）
	 * @return
	 */
	public static JSONArray bytesToJSONArray(byte data[]) {
		if (data == null)
			return null;
		return stringToJSONArray(bytesToString(data));
	}

	/**
	 * 字节码转JSONObject
	 * 
	 * @param data
	 *            字节码（源为字符）
	 * @return
	 */
	public static JSONObject bytesToJSONObject(byte data[]) {
		if (data == null)
			return null;
		return stringToJSONObject(bytesToString(data));
	}

	/**
	 * 字节码数据 转 字符串工具
	 */
	public static String bytesToString(byte[] data) {
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

	/**
	 * 字符串转JSONArray工具
	 * 
	 * @param json
	 * @return
	 */
	public static JSONArray stringToJSONArray(String json) {
		if (json == null)
			return null;
		try {
			return new JSONArray(json);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 对字符串进行MD5加密。
	 */
	public static String encryptMD5(String strInput) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strInput.getBytes("UTF-8"));
			byte b[] = md.digest();
			buf = new StringBuffer(b.length * 2);
			for (int i = 0; i < b.length; i++) {
				if (((int) b[i] & 0xff) < 0x10) { /* & 0xff转换无符号整型 */
					buf.append("0");
				}
				buf.append(Long.toHexString((int) b[i] & 0xff)); /* 转换16进制,下方法同 */
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 网址汉字编码
	 */
	public static String urlEncode(String str) {
		StringBuffer buf = new StringBuffer();
		byte c;
		byte[] utfBuf;
		try {
			utfBuf = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("URLEncode: Failed to get UTF-8 bytes from string.");
			utfBuf = str.getBytes();
		}
		for (int i = 0; i < utfBuf.length; i++) {
			c = utfBuf[i];
			if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')
					|| (c == '.' || c == '-' || c == '*' || c == '_')
					|| (c == ':' || c == '/' || c == '=' || c == '?' || c == '&' || c == '%')) {
				buf.append((char) c);
			} else {
				buf.append("%").append(Integer.toHexString((0x000000FF & c)));
			}
		}
		return buf.toString();
	}

}
