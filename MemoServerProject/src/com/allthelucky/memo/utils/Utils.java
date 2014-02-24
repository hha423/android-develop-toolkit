package com.allthelucky.memo.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public class Utils {

	/**
	 * read input JSONObject
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static JSONObject readJSONObject(HttpServletRequest request) throws Exception {
		final StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		final String json = sb.toString();
		if (json != null && !"".equals(json)) {
			try {
				return new JSONObject(json.toString());
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		} else {
			return new JSONObject();
		}
	}

	public static String postData(final String url, final String postData) {
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
			os.write(postData.getBytes("UTF-8"));
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
			con.disconnect();
			return new String(data, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.toString();
		}
	}

	public static String currentTimeMillis() {
		return String.valueOf(System.currentTimeMillis());
	}

}
