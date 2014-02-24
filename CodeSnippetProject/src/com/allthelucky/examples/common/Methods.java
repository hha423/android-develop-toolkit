package com.allthelucky.examples.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.allthelucky.examples.R;

public class Methods {
	private static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
	private static final String hexString = "0123456789abcdef";

	private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
			5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26,
			27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
			-1, -1, -1 };

	/**
	 * 判断是否有中文
	 * @param str
	 * @return
	 */
	public static boolean isCN(String str){
	    try {
	        byte [] bytes = str.getBytes("UTF-8");
	        if(bytes.length == str.length()){
	            return false;
	        }else{
	            return true;
	        }
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * 检测网络类型
	 * @param mContext
	 * @return
	 */
	public static int checkNetworkType(Context mContext) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService("connectivity");
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if ((networkInfo == null) || (!networkInfo.isAvailable())) {
				Log.i("", "=====================>无网络");
				return 0;
			}

			int netType = networkInfo.getType();
			if (netType == 1) {
				Log.i("", "=====================>wifi网络");
				return 6;
			}
			if (netType == 0) {
				Cursor c = mContext.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
				if (c != null) {
					c.moveToFirst();
					String user = c.getString(c.getColumnIndex("user"));
					if (!TextUtils.isEmpty(user)) {
						Log.i("", "=====================>代理：" + c.getString(c.getColumnIndex("proxy")));
						if (user.startsWith("ctwap")) {
							Log.i("", "=====================>电信wap网络");
							return 5;
						}
					}
				}
				c.close();

				String netMode = networkInfo.getExtraInfo();
				Log.i("", "netMode ================== " + netMode);
				if (netMode != null) {
					netMode = netMode.toLowerCase();
					if ((netMode.equals("cmwap")) || (netMode.equals("3gwap")) || (netMode.equals("uniwap"))) {
						Log.i("", "=====================>移动联通wap网络");
						return 4;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 6;
		}
		return 6;
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

	/**
	 * 32位MD5编码
	 * 
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
				buf.append(Long.toHexString((int) b[i] & 0xff)); // 转换16进制,下方法同
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 判断SD卡是否存在
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasSDCard(Context context) {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

	}

	/**
	 * 获取SD卡路径(不带/)
	 */
	public static String getSDpath() {
		return Environment.getExternalStorageDirectory().toString();
	}

	/**
	 * 播放音乐
	 * 
	 * @param context
	 */
	public static void playMusic(Context context) {
		try {
			AssetFileDescriptor afd = context.getAssets().openFd("AudioFile.mp3");
			MediaPlayer player = new MediaPlayer();
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			player.prepare();
			player.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 系统分享功能，调用可进行分享的软件进行内容分享
	 * 
	 * @param context
	 *            上下文
	 */
	public static void shareByapp(Context context) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/png");// intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
		intent.putExtra(Intent.EXTRA_TEXT, "内容");
		Uri uri = Uri.parse("file://mnt/sdcard/51.png");
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		context.startActivity(Intent.createChooser(intent, "分享"));
	}

	/**
	 * 取系统时间
	 * 
	 * @return
	 */
	public static void getSubmitTime() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println(df.format(date));
	}

	/**
	 * 取系统时间
	 * 
	 * @return
	 */
	public static String getSubmitTime2() {
		Calendar c;
		try {
			c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		} catch (Exception e) {
			c = null;
		}
		Date date = new Date();
		if (c != null) {
			date = c.getTime();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(date);
	}

	/**
	 * 取随机8位数字
	 * 
	 * @return
	 */
	public static String getRandomNumber() {
		Random rand = new Random();
		String key = System.currentTimeMillis() + "" + rand.nextInt(100);
		key = key.substring(key.length() - 8);
		if (key.charAt(0) == '0') {
			key = key.replace('0', '1');
		}
		return key;
	}

	/**
	 * 解析时间json
	 * 
	 * @param date
	 *            日期JSONObject
	 * @return
	 */
	public static String parseDate(JSONObject date) {
		long mLongDate = date.optLong("time");
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(mLongDate);
		Date mDate = ca.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(mDate);
	}

	/**
	 * 加载Assert文本文件，转换成String类型
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String loadAssetsText(Context context, String fileName) throws IOException {
		InputStream inputStream = context.getAssets().open(fileName, Context.MODE_PRIVATE);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte[] bytes = new byte[4096];
		int len = 0;
		while ((len = inputStream.read(bytes)) > 0) {
			byteStream.write(bytes, 0, len);
		}

		return new String(byteStream.toByteArray(), "UTF-8");
	}
	
	public static String postApiData(String url, String postData) {
		String ret = null;
		try {
			URL dataUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(10000);// 连接超时 单位毫秒
			con.setReadTimeout(5000);// 读取超时 单位毫秒
			con.setRequestProperty("Proxy-Connection", "Keep-Alive");
			con.setDoOutput(true);
			con.setDoInput(true);

			OutputStream os = con.getOutputStream();
			os.write(postData.getBytes());
			os.flush();
			os.close();

			InputStream is = con.getInputStream();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			byte[] data = outStream.toByteArray();// 网页的二进制数据
			outStream.close();
			is.close();

			ret = new String(data, "UTF-8");
			con.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}


	/**
	 * 字节码转Hex字符串
	 * 
	 * @param src
	 *            字节码数组
	 * @param len
	 *            字节码长度
	 * @return
	 */
	public static String bytesToHexString(byte[] src, int len) {
		StringBuilder stringBuilder = new StringBuilder("");
		if ((src == null) || (src.length <= 0) || (len <= 0)) {
			return null;
		}
		for (int i = 0; i < len; ++i) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encodeHexString(String str) {

		byte[] bytes = str.getBytes();// 根据默认编码获取字节数组
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) { // 将字节数组中每个字节拆解成2位16进制整数
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * Hex String 编码成byte数组
	 * 
	 * @param hex字符串
	 * @return
	 */
	public static byte[] hexToBytes(String hex) {
		byte[] buffer = new byte[hex.length() / 2];
		String strByte;

		for (int i = 0; i < buffer.length; i++) {
			strByte = hex.substring(i * 2, i * 2 + 2);
			buffer[i] = (byte) Integer.parseInt(strByte, 16);
		}

		return buffer;
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
	 * 叠加图片合成（如地图图标）
	 * 
	 * @param context
	 * @return
	 */
	public static LayerDrawable test(Context context) {
		LayerDrawable layerDrawable = (LayerDrawable) context.getResources().getDrawable(R.drawable.layerlist);
		Drawable drawable = context.getResources().getDrawable(R.drawable.ic_launcher);
		layerDrawable.setDrawableByLayerId(R.id.userimage, drawable);
		return layerDrawable;
	}

	/**
	 * AnimationDrawable 实现GIF效果
	 * 
	 * @param activity
	 * @param imageView
	 */
	public static void show(Activity activity, ImageView imageView) {
		final AnimationDrawable drawable = new AnimationDrawable();
		drawable.addFrame(activity.getResources().getDrawable(R.drawable.ic_launcher), 750);// 添加图片帧到AnimationDrawable
		drawable.addFrame(activity.getResources().getDrawable(R.drawable.ic_launcher), 1250);
		drawable.setOneShot(false);// 设置为循环播放
		imageView.setImageDrawable(drawable);// AnimationDrawable对象给imageView
		drawable.start();// 动画播放
		drawable.stop();// 动画停止
	}

	/**
	 * 屏幕单位转换 dip2px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 屏幕单位转换px2dip
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 银行卡号除头和尾数外,以星号显示. 输入:6222002202882165,输出:6222 **** **** 2165
	 * 
	 * @param no
	 * @return
	 */
	public static String getBankNoWithStar(String no) {
		if (no == null)
			return null;
		int len = no.length();
		if (len < 8)
			return no;

		String tail = "";
		String head = "";
		String center = "";
		StringBuffer temp = new StringBuffer();
		char[] source = no.toCharArray();
		for (int i = 0; i < 4; i++) {// 取头
			temp.append(source[i]);
		}
		head = temp.toString();

		temp = new StringBuffer();
		for (int j = len - 4; j < len; j++) {// 取尾
			temp.append(source[j]);
		}
		tail = temp.toString();

		temp = new StringBuffer();
		for (int k = 0; k < len - 8; k++) {
			if (k > 0 && k % 4 == 0 && k < len - 8) {// 取中间
				temp.append(" ");
			}
			temp.append("*");
		}
		center = temp.toString();

		temp = null;
		temp = new StringBuffer();// 连接
		temp.append(head).append(" ").append(center).append(" ").append(tail);
		return temp.toString();
	}

	/**
	 * 退出结束程序
	 */
	public static void appExit() {
		Process.killProcess(Process.myPid());
	}

	/**
	 * 应用是否安装
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isApplicationInstalled(final Context context, final String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		if (null == context) {
			throw new IllegalArgumentException("context may not be null.");
		}
		try {
			context.getPackageManager().getPackageInfo(packageName, 0);
			return true;
		} catch (final NameNotFoundException e) {
		}
		return false;
	}

	/**
	 * 软件版本是否可更新
	 * 
	 * @param context
	 * @param serverVerCode
	 *            服务器端apk最新版本号
	 * @return
	 */
	public static boolean hasNewVersion(Context context, int serverVerCode) {
		if (null == context) {
			throw new IllegalArgumentException("context may not be null.");
		}
		try {
			int versionCode = getVersionCode(context);
			if (versionCode < serverVerCode) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 取apk包信息
	 * 
	 * @param context
	 * @return
	 */
	private static PackageInfo getPackageInfo(Context context) {
		PackageInfo packageInfo = null;
		try {
			PackageManager pm = context.getPackageManager();
			packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			packageInfo = new PackageInfo();
			packageInfo.versionCode = 1;
			packageInfo.versionName = "1.0";
		}
		return packageInfo;
	}

	/**
	 * 取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		return getPackageInfo(context).versionCode;
	}

	/**
	 * 取软件版名
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		return getPackageInfo(context).versionName;
	}

	/**
	 * 判断屏幕是大/小屏
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isLargeScreen(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display dp = windowManager.getDefaultDisplay();
		if (dp.getHeight() > 480) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断当前应用程序处于前台还是后台
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					Log.d("ApplicationUtils", "Foreground App:" + appProcess.processName);
					return true;
				} else {
					Log.d("ApplicationUtils", "Background App:" + appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}
	

	/**
	 * 加载应用file下文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public byte[] loadCacheFile(Context context, String fileName) {
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		try {
			is = context.openFileInput(fileName);
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int len = -1;
			while ((len = is.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bos.flush();
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				bos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 加载raw下资源
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static byte[] getOutputStream(Context context, int resId) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream inputStream = context.getResources().openRawResource(resId);
		int len = -1;
		byte buffer[] = new byte[1024];
		try {
			while ((len = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				inputStream.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将数字金额转换成大写金额
	 * 
	 * @param number
	 * @return
	 */
	public String changeNumberToCNY(String number) {
		// 如果传入""即为"空值"
		if ("".equals(number)) {
			return "空值，请重新输入！";
		}
		// 非数字型
		double dNumber;
		try {
			dNumber = Math.abs(Double.parseDouble(number));
		} catch (NumberFormatException e) {
			return "输入错误，请重新输入！";
		}

		// 将这个数转换成 double类型，并对其取绝对值后进行四舍五入操作

		dNumber = (dNumber * 100);
		dNumber = Math.round(dNumber);
		dNumber = dNumber / 100.0;
		// 将 dNumber进行格式化 否则会以科学计数型输出
		number = new java.text.DecimalFormat("##0.00").format(dNumber);

		// 规定数值的最大长度只能到万亿单位，否则返回 "溢出"
		int index = number.indexOf(".");
		if (number.substring(0, index).length() > 13) {
			return "数据溢出，请重新输入！";
		}
		return clearZero(splitNumber(number));
	}

	/**
	 * 将数字格式化成中文
	 * 
	 * @param flag
	 *            1表示整数部分 2表示小数部分
	 * @param number
	 * @return
	 */
	private String numberFormat(int flag, String number) {
		// 货币大写形式
		String bigLetter[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		// 货币单位
		String unit[] = { "元", "拾", "佰", "仟", "万",
				// 拾万位到仟万位
				"拾", "佰", "仟",
				// 亿位到万亿位
				"亿", "拾", "佰", "仟", "万" };
		String small[] = { "角", "分" };
		StringBuffer newNumber = new StringBuffer();
		for (int i = 0; i < number.length(); i++) {
			if (flag == 1) {
				newNumber
				// char型数字转成int型数字相要-48 char 3 相当于 int的51
						.append(bigLetter[number.charAt(i) - 48])
						// 单位
						.append(unit[number.length() - i - 1]);
			}
			if (flag == 2) {
				newNumber
				// char型数字转成int型数字相要-48 char 3 相当于 int的51
						.append(bigLetter[number.charAt(i) - 48])
						// 单位
						.append(small[i]);
			}
		}
		return newNumber.toString();
	}

	/**
	 * 以小数点为界分割开
	 * 
	 * @param number
	 * @return
	 */
	private String splitNumber(String number) {
		// 取得小数点前后2部分
		int index = number.indexOf(".");
		String intOnly = number.substring(0, index);
		String smallOnly = number.substring(index + 1);
		// 将前后2部分转换成中文金额
		String intNew = numberFormat(1, intOnly);
		String smallNew = numberFormat(2, smallOnly);
		return intNew + smallNew;
	}

	/**
	 * 清理字符串中多余的零
	 * 
	 * @param number
	 * @return
	 */
	private String clearZero(String number) {
		while (number.charAt(0) == '零') {
			// 将字符串中的 "零" 和它对应的单位去掉
			number = number.substring(2);
			// 如果用户当初输入的时候只输入了 0，则只返回一个 "零"
			if (number.length() == 0) {
				return "零元";
			}
		}
		// 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位
		String regex1[] = { "零仟", "零佰", "零拾" };
		String regex2[] = { "零亿", "零万", "零元" };
		String regex3[] = { "亿", "万", "元" };
		String regex4[] = { "零角", "零分" };
		// 第一轮转换把 "零仟", 零佰","零拾"等字符串替换成一个"零"
		for (int i = 0; i < 3; i++) {
			number = number.replaceAll(regex1[i], "零");
		}
		// 第二轮转换考虑 "零亿","零万","零元"等情况
		// "亿","万","元"这些单位有些情况是不能省的，需要保留下来
		for (int i = 0; i < 3; i++) {
			// 当第一轮转换过后有可能有很多个零叠在一起
			// 要把很多个重复的零变成一个零
			number = number.replaceAll("零零零", "零");
			number = number.replaceAll("零零", "零");
			number = number.replaceAll(regex2[i], regex3[i]);
		}
		// 第三轮转换把"零角","零分"字符串省略
		for (int i = 0; i < 2; i++) {
			number = number.replaceAll(regex4[i], "");
		}
		// 当"万"到"亿"之间全部是"零"的时候，忽略"亿万"单位，只保留一个"亿"
		number = number.replaceAll("亿万", "亿");
		if (number.charAt(number.length() - 1) == '元') {
			number = number + "整";
		}
		return number;
	}

	/**
	 * 图吧坐标转WGS84坐标
	 * 
	 * @param lat
	 *            纬度
	 * @param lng
	 *            经度
	 * @return lat=a[0], lng=a[1]
	 */
	public static String[] mapbar2WGS84(String lat, String lng) {
		double x = (new Double(lng) * 100000) % 36000000;
		double y = (new Double(lat) * 100000) % 36000000;

		int x1 = (int) (-(((Math.cos(y / 100000)) * (x / 18000)) + ((Math.sin(x / 100000)) * (y / 9000))) + x);
		int y1 = (int) (-(((Math.sin(y / 100000)) * (x / 18000)) + ((Math.cos(x / 100000)) * (y / 9000))) + y);

		int x2 = (int) (-(((Math.cos(y1 / 100000d)) * (x1 / 18000)) + ((Math.sin(x1 / 100000d)) * (y1 / 9000))) + x + ((x > 0) ? 1
				: -1));
		int y2 = (int) (-((((Math.sin(y1 / 100000d)) * (x1 / 18000)) + ((Math.cos(x1 / 100000d)) * (y1 / 9000)))) + y + ((y > 0) ? 1
				: -1));

		return new String[] { new Double(y2 / 100000d).toString(), new Double(x2 / 100000d).toString() };
	}

	/**
	 * 图吧数据编码
	 * 
	 * @param data
	 * @return
	 */
	public static String encode(byte[] data) {
		StringBuffer sb = new StringBuffer();
		int len = data.length;
		int i = 0;
		int b1, b2, b3;

		while (i < len) {
			b1 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
				sb.append("==");
				break;
			}
			b2 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
				sb.append("=");
				break;
			}
			b3 = data[i++] & 0xff;
			sb.append(base64EncodeChars[b1 >>> 2]);
			sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
			sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
			sb.append(base64EncodeChars[b3 & 0x3f]);
		}
		return sb.toString();
	}

	/**
	 * 百度地图数据解析
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeBase64(String str) {
		return new String(decode(str));
	}

	private static byte[] decode(String str) {
		byte[] data = str.getBytes();
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
		int i = 0;
		int b1, b2, b3, b4;

		while (i < len) {

			do {
				b1 = base64DecodeChars[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1) {
				break;
			}

			do {
				b2 = base64DecodeChars[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1) {
				break;
			}
			buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

			do {
				b3 = data[i++];
				if (b3 == 61) {
					return buf.toByteArray();
				}
				b3 = base64DecodeChars[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1) {
				break;
			}
			buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

			do {
				b4 = data[i++];
				if (b4 == 61) {
					return buf.toByteArray();
				}
				b4 = base64DecodeChars[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1) {
				break;
			}
			buf.write((int) (((b3 & 0x03) << 6) | b4));
		}
		return buf.toByteArray();
	}

}
