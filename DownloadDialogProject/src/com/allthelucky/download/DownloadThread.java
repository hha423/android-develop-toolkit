package com.allthelucky.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

/**
 * 程序下载线程
 * 
 * @author pxw
 * 
 */
public class DownloadThread extends Thread {
	private static final int DEFAULT_FILE_LEN = 2000000; // ~2M
	private final static int LOADING = 0;
	private final static int DONE = 1;
	private final static int ERROR = 2;

	private String downladUrl = "";
	private String fileName = "";
	private volatile boolean shutdownFlag = false;
	private DownloadListener listener = null;
	private Context context;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			final String result = (String) msg.obj;
			if (msg.what == LOADING) {
				if (result != null) {
					listener.onUpdate(Integer.valueOf(result));
				}
			} else if (msg.what == DONE) {
				listener.onCompleted(DownloadListener.OK, result);
			} else {
				listener.onCompleted(DownloadListener.ERROR, result);
			}
		}
	};

	public DownloadThread(Context context) {
		this.context = context;
	}

	public void setParams(String url, String fileName, DownloadListener donwloadListener) {
		this.downladUrl = url;
		this.fileName = fileName;
		this.listener = donwloadListener;
	}

	@Override
	public void run() {
		try {
			download();
		} catch (Exception e) {
			e.printStackTrace();
			handler.sendMessage(handler.obtainMessage(ERROR, "下载失败"));
		}
	}

	/**
	 * 下载程序
	 * 
	 * @throws Exception
	 */
	private void download() throws Exception {
		File dir = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			dir = context.getExternalCacheDir();
		}
		if (dir == null) {
			dir = context.getCacheDir();
		}

		final URL url = new URL(downladUrl);
		final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(30 * 1000);
		conn.setRequestProperty("User-agent", "Mozilla/4.0");
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/vnd.android.package-archive");

		final int code = conn.getResponseCode();
		if (code != 200) {
			handler.sendMessage(handler.obtainMessage(2));
			return;
		}

		final String filePath = dir.getAbsolutePath() + "/" + fileName;
		final File file = new File(filePath);
		if (file.exists()) { // delete the old one
			file.delete();
		}
		InputStream fin = conn.getInputStream();
		FileOutputStream fos = new FileOutputStream(file);
		int total = conn.getContentLength();
		if (total == -1) {
			total = DEFAULT_FILE_LEN;
		}
		int len = -1;
		int init = 0;
		byte buffer[] = new byte[1024];
		while ((len = fin.read(buffer)) != -1) {
			fos.write(buffer, 0, len);
			init += len;
			String percent = (float) init / (float) total * 100 + "";
			if (!shutdownFlag) {
				handler.sendMessage(handler.obtainMessage(LOADING, percent.substring(0, percent.indexOf("."))));
			} else {// stop loop when interruped
				break;
			}
		}
		fos.flush();
		fin.close();
		fos.close();
		if (!shutdownFlag) {
			handler.sendMessage(handler.obtainMessage(DONE, filePath));
		} else {
			System.out.println("canceled");
		}
	}

	/**
	 * 取消下载
	 */
	public void cancel() {
		shutdownFlag = true;
		handler.sendMessage(handler.obtainMessage(ERROR, "下载已取消"));
		interrupt();
	}

	/**
	 * DownloadThread下载监听接口
	 */
	public interface DownloadListener {
		public final static int OK = 0;
		public final static int ERROR = 1;

		/**
		 * 更新进度
		 */
		public void onUpdate(int percent);

		/**
		 * 下载成功
		 */
		public void onCompleted(int code, String reason);
	}

}
