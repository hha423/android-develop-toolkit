package cn.code.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringUtil {

	private static String[] hexs = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	private static String[] bins = new String[] { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
			"1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111" };

	public StringUtil() {

	}

	/**
	 * 返回512长度之内的HEX字符串除�?2之后的长度，用HEX字符串表�?
	 * 例如传�?�的字符串长度为280，其16进制长度�?140�?0x8C�?,用字符串8c表示
	 * 
	 * @param strValue
	 * @return
	 */
	public static String getStringHexLenWithinByte(String strValue) {
		int len = strValue.length() / 2;
		String result = Integer.toHexString(len);
		if (result.length() == 1) {
			result = "0" + result;
		}

		return result;
	}

	public static String trimStringBlank(String strValue) {
		String result = strValue.trim(); // 去掉左右的空�?
		String space = " ";
		String tabCh = Character.toString((char) 9);

		// 去除中间的空格
		result = result.replaceAll(space, "");

		result = result.replaceAll(tabCh, "");

		return result;
	}

	public static int getHexDataLen(String data) {
		String strTemp = data.replaceAll(" ", "");

		return strTemp.length();
	}

	public static String formatHexDataWithSpace(String data) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < data.length(); i++) {
			if (i > 0 && (i % 2 == 0)) {
				buffer.append(" ");
			}
			buffer.append(data.charAt(i));
		}

		return buffer.toString();
	}

	public static String asciiToHex(String asciiString) {
		StringBuffer hexString = new StringBuffer();
		String hex;
		int iValue;
		byte[] buff = asciiString.getBytes();

		for (int i = 0; i < buff.length; i++) {
			iValue = buff[i];
			if (iValue < 0)
				iValue += 256;

			hex = Integer.toString(iValue, 16);
			if (hex.length() == 1)
				hexString.append("0" + hex);
			else
				hexString.append(hex);
		}

		return hexString.toString().toUpperCase();
	}

	public static String byteToHex(byte[] buffer, int offset, int len) {
		StringBuffer hexString = new StringBuffer();
		String hex;
		int iValue;

		for (int i = offset; i < offset + len; i++) {
			iValue = buffer[i];
			if (iValue < 0)
				iValue += 256;

			hex = Integer.toString(iValue, 16);
			if (hex.length() == 1)
				hexString.append("0" + hex);
			else
				hexString.append(hex);
		}

		return hexString.toString().toUpperCase();
	}

	public static String byteToHex(byte[] buffer) {
		StringBuffer hexString = new StringBuffer();
		String hex;
		int iValue;

		for (int i = 0; i < buffer.length; i++) {
			iValue = buffer[i];
			if (iValue < 0)
				iValue += 256;

			hex = Integer.toString(iValue, 16);
			if (hex.length() == 1)
				hexString.append("0" + hex);
			else
				hexString.append(hex);
		}

		return hexString.toString().toUpperCase();
	}

	public static String asciiToHex(byte[] hexBuffer, int iOffset, int iLen) {
		StringBuffer hexString = new StringBuffer();
		String hex;
		int byteValue;

		for (int i = iOffset; i < iOffset + iLen; i++) {
			byteValue = hexBuffer[i];
			if (byteValue < 0) {
				byteValue += 256;
			}

			hex = Integer.toString(byteValue, 16);
			if (hex.length() == 1)
				hexString.append("0" + hex);
			else
				hexString.append(hex);
		}

		return hexString.toString().toUpperCase();
	}

	public static String intToHex(int value) {
		String hex;

		hex = Integer.toString(value, 16);

		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}

		return hex.toUpperCase();
	}

	public static String hexToAscii(String hex) {
		byte[] buffer = new byte[hex.length() / 2];
		String strByte;

		for (int i = 0; i < buffer.length; i++) {
			strByte = hex.substring(i * 2, i * 2 + 2);
			buffer[i] = (byte) Integer.parseInt(strByte, 16);
		}

		return new String(buffer);
	}

	public static byte[] hexToBytes(String hex) {
		byte[] buffer = new byte[hex.length() / 2];
		String strByte;

		for (int i = 0; i < buffer.length; i++) {
			strByte = hex.substring(i * 2, i * 2 + 2);
			buffer[i] = (byte) Integer.parseInt(strByte, 16);
		}

		return buffer;
	}

	public static boolean hasChineseChar(String strValue) {
		boolean bResult = false;

		byte[] temp = strValue.getBytes();

		for (int i = 0; i < strValue.length(); i++) {
			if (temp[i] < 0) {
				bResult = true;
				break;
			}
		}

		return bResult;
	}

	public static boolean isNull(String value) {
		boolean bIsNull = false;

		if (value == null) {
			bIsNull = true;
			return bIsNull;
		}

		if (value.trim().length() == 0) {
			bIsNull = true;
		}

		return bIsNull;
	}

	public static String formatWithSpace(String value) {
		String formatedValue = value;
		String retValue;

		// 去掉所有的空格
		formatedValue = trimStringBlank(formatedValue);

		if (formatedValue.length() % 2 != 0) {
			formatedValue = formatedValue + "0";
		}

		retValue = "";
		for (int i = 0; i < formatedValue.length(); i += 2) {
			retValue += formatedValue.substring(i, i + 2) + " ";
		}

		return retValue;
	}

	public static String formatDateToString(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

		return sdf.format(date);
	}

	public static String formatDateToEngString(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(date);
	}

	public static String formatDateToNormalYMDString(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		return sdf.format(date);
	}

	public static String formatFullDateToEngString(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sdf.format(date);
	}

	public static String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉�?有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}

	/**
	 * 将字符串格式化为指定的长度整数�?�，不足填充对齐
	 * 
	 * @param data
	 * @param len
	 * @param fill
	 * @return
	 */
	public static String formatStringByLen(String data, int len, String fill) {
		String strData = trimStringBlank(data);

		int iFilledLen = strData.length() % len;
		if (iFilledLen > 0) {
			for (int i = 0; i < 8 - iFilledLen; i++) {
				strData += fill;
			}
		}

		return strData;
	}

	/**
	 * BCD字符串转换成byte数组
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] bcdToBytes(String data) {
		String strData = trimStringBlank(data).toUpperCase();

		int value;
		int j = 0;
		byte[] bData = new byte[strData.length() / 2];
		// 170957382130000F
		for (int i = 0; i < strData.length(); i++) {
			value = Character.digit(strData.charAt(i), 16);

			bData[j] = (byte) ((value << 4) & 0xF0);
			value = Character.digit(strData.charAt(i + 1), 16);
			bData[j] |= (byte) (value & 0x0F);

			i++;
			j++;
		}

		return bData;
	}

	public static String getSeperatedPathFromPackage(String packageName) {
		String seperatedPath = packageName;

		seperatedPath = seperatedPath.replaceAll("\\.", "\\" + File.separator);

		if (!seperatedPath.endsWith(File.separator)) {
			seperatedPath += File.separator;
		}

		return seperatedPath;
	}

	/**
	 * 返回BCD码表示的指定长度数字字符串
	 * 
	 * @param number
	 * @param width
	 * @return
	 */
	public static String getFormatedNumBcdString(int number, int width) {
		String bcdString = Integer.toString(number);

		int len = width - bcdString.length();

		for (int i = 0; i < len; i++) {
			bcdString = "0" + bcdString;
		}

		return bcdString;
	}

	/**
	 * 返回左补零的指定长度字符串字符串
	 * 
	 * @param number
	 * @param width
	 * @return
	 */
	public static String getFormatedZeroString(String str, int width) {

		int len = width - str.length();

		for (int i = 0; i < len; i++) {
			str = "0" + str;
		}

		return str;
	}

	/**
	 * 返回左补加空格的指导位数的字符串
	 * 
	 * @param str
	 * @param width
	 * @return
	 */
	public static String getLeftFormatedBlankString(String str, int width) {
		int len = width - str.length();

		for (int i = 0; i < len; i++) {
			str = " " + str;
		}

		return str;
	}

	/**
	 * 返回右补加空格的指导位数的字符串
	 * 
	 * @param str
	 * @param width
	 * @return
	 */
	public static String getRightFormatedBlankString(String str, int width) {
		int len = width - str.length();

		for (int i = 0; i < len; i++) {
			str = str + " ";
		}

		return str;
	}

	/**
	 * 返回指定位数的空字符串
	 * 
	 * @param str
	 * @param width
	 * @return
	 */
	public static String getFormatedBlankString(int width) {
		StringBuffer str = new StringBuffer("");

		for (int i = 0; i < width; i++) {
			str.append(" ");
		}

		return str.toString();
	}

	/**
	 * 返回BCD码表示的指定长度数字字符串
	 * 
	 * @param number
	 * @param width
	 * @return
	 */
	public static String getLeftFormatedNumBcdString(String number, int width) {

		int len = width - number.length();

		for (int i = 0; i < len; i++) {
			number = "0" + number;
		}

		return number;
	}

	/**
	 * 返回BCD码表示的指定长度数字字符串
	 * 
	 * @param number
	 * @param width
	 * @return
	 */
	public static String getLeftNumBcdString(String number, int width) {
		int len = width - number.length();

		for (int i = 0; i < len; i++) {
			number = number + "0";
		}

		return number;
	}

	/**
	 * 该函数具有两个功能： 1.产生一个具有length长度的随机数 2.将该随机数转化为ascii的形式
	 * 
	 * @param length
	 * @return ascii字符串
	 */
	public static String generalStringToAscii(int length) {
		int num = 1;
		String strRandom;

		for (int i = 0; i < length; i++) {
			num *= 10;
		}

		try {
			Thread.sleep(10);
			Random rand = new Random((new Date()).getTime());
			strRandom = Integer.toString(rand.nextInt(num));

			int len = strRandom.length();
			for (int i = 0; i < length - len; i++) {
				strRandom = "0" + strRandom;
			}

		} catch (InterruptedException e) {
			strRandom = "00000000";
		}

		StringBuffer sbu = new StringBuffer();
		char[] chars = strRandom.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			sbu.append((int) chars[i]);
		}
		return sbu.toString();
	}

	public static String longToStringBytes(Long l) {
		try {
			StringBuffer sbu = new StringBuffer();
			char[] chars = l.toString().toCharArray();
			for (int i = 0; i < chars.length; i++) {
				sbu.append((int) chars[i]);
			}
			return sbu.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HEX码 将形如0x12 0x2A 0x01 转�?�为122A01
	 * 
	 * @param data
	 * @return
	 */
	public static String hexEncode(byte[] data) {
		if (data == null || data.length <= 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String tmp = Integer.toHexString(data[i] & 0xff);
			if (tmp.length() < 2) {
				buffer.append('0');
			}
			buffer.append(tmp);
		}
		String retStr = buffer.toString().toUpperCase();
		return retStr;
	}

	/**
	 * HEX解码 将形如122A01 转换为0x12 0x2A 0x01
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] hexDecode(String data) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (int i = 0; i < data.length(); i += 2) {
			String onebyte = data.substring(i, i + 2);
			int b = Integer.parseInt(onebyte, 16) & 0xff;
			out.write(b);
		}
		return out.toByteArray();
	}

	/***
	 * 该方法主要用于格式化单个字节的16进制数转化为的10进制后字符串，如果字符串的位数不足两位，则补足两位
	 * 
	 * @param str
	 * @return
	 */
	public static String formatStringTo2length(String str) {
		if (null != str && str.length() < 2) {
			str = "0" + str;
		}
		return str;
	}

	public static Date stringToDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = sdf.parse(dateStr);
			System.out.println(d);
			return d;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算字节校验和
	 * 
	 * @param input
	 * @return
	 */
	public static byte calcLRC(byte[] input) {
		byte ret = 0;
		for (int i = 0; i < input.length; i++) {
			ret ^= input[i];
		}
		return ret;
	}

	/**
	 * 异或
	 * 
	 * @param op1
	 * @param op2
	 * @return
	 */
	public static byte[] xor(byte[] op1, byte[] op2) {
		int len = op1.length;
		byte[] out = new byte[len];
		for (int i = 0; i < len; i++) {
			out[i] = (byte) (op1[i] ^ op2[i]);
		}
		return out;
	}

	// 返回十六进制数的二进制形式
	public static String getBin(String hex) {
		int i;
		for (i = 0; i < hexs.length && !hex.toLowerCase().equals(hexs[i]); i++)
			;
		return bins[i];
	}

	// 返回二进制数的十六进制形式 //
	public static String getHex(String bin) {
		int i;
		for (i = 0; i < bins.length && !bin.equals(bins[i]); i++)
			;
		return hexs[i];
	}

	/**
	 * 计算String的Md5值
	 * @param strInput
	 * @return
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
	 * get IP address
	 * @return
	 */
	public static String getIp() {
		try {
			InetAddress ia = InetAddress.getLocalHost();//InetAddress.getByName("www.winfirm.net");
			String adress = ia.getHostAddress();//String name = ia.getHostName();
			return adress;
		} catch (java.net.UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String test = "0125A大人";
		byte[] testb = test.getBytes();
		String hex = hexEncode(testb);
		System.out.println(hex);

		byte[] bd = hexDecode(hex);
		System.out.println(new String(bd));
	}
	
	public static String randomNo() {
		StringBuffer sb=new StringBuffer();
		Random r =new Random();
		for(int i=0; i<8;i++) {
			sb.append(r.nextInt(10));
		}
		return sb.toString();
	}

}
