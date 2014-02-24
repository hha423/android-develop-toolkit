package com.example.android.jni;

import java.security.MessageDigest;

import android.content.Context;

public class JNIHelper {
	static {
		System.loadLibrary("jni_helper");
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
	
	public native static void helloWorld();
	public native static void init(Context context);
	public native static String encode(String data);
	public native static void printString(String message);
}
