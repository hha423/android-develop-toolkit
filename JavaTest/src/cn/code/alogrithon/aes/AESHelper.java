package cn.code.alogrithon.aes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {

	private static SecretKey secretKey = null;// key对象
	private static Cipher cipher = null; // 私鈅加密对象Cipher

	public static void main(String[] args) {
        System.out.println(desEncrypt("测试", "97f7ee8f057104c7fbb8d23ca2be194a"));
    }
	
	/**
	 * 加密
	 * 
	 * @param message
	 * @return
	 */
	public static String desEncrypt(String message, String passWord) {
		String result = ""; // DES加密字符串
		String newResult = "";// 去掉换行符后的加密字符串
		try {

			/* AES算法 */
			secretKey = new SecretKeySpec(passWord.getBytes(), "AES");// 获得密钥
			/* 获得一个私鈅加密类Cipher，DESede-》AES算法，ECB是加密模式，PKCS5Padding是填充方式 */
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8")); // 正式执行加密操作
			BASE64Encoder enc = new BASE64Encoder();
			result = enc.encode(resultBytes);// 进行BASE64编码
			newResult = filter(result); // 去掉加密串中的换行符
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return newResult;
	}

	/**
	 * 解密
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String desDecrypt(String message, String passWord) throws Exception {
		String result = "";
		try {
			/* AES算法 */
			secretKey = new SecretKeySpec(passWord.getBytes(), "AES");// 获得密钥
			BASE64Decoder dec = new BASE64Decoder();
			byte[] messageBytes = dec.decodeBuffer(message); // 进行BASE64编码
			cipher.init(Cipher.DECRYPT_MODE, secretKey); // 设置工作模式为解密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(messageBytes);// 正式执行解密操作
			result = new String(resultBytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 去掉加密字符串换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String filter(String str) {
		String output = "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int asc = str.charAt(i);
			if (asc != 10 && asc != 13) {
				sb.append(str.subSequence(i, i + 1));
			}
		}
		output = new String(sb);
		return output;
	}
}