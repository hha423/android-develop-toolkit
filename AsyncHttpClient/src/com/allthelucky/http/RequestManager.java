package com.allthelucky.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Request Manager based on AsyncHttpClient
 * 
 * @author savant-pan
 * 
 */
public class RequestManager {
	private final AsyncHttpClient asyncHttpClient;
	private static RequestManager INSTANCE = null;

	protected RequestManager() {
		this.asyncHttpClient = new AsyncHttpClient();
	}

	public static RequestManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RequestManager();
		}
		return INSTANCE;
	}

	/**
	 * 清空缓存文件
	 * 
	 * @param context
	 */
	public static void clearHttpCache(Context context) {
		final String fl[] = context.fileList();
		try {
			for (String f : fl) {
				context.deleteFile(f);
			}
			RequestChacheManager.getInstance(context).deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancel(Context context) {
		asyncHttpClient.cancelRequests(context, true);
	}

	/**
	 * 参数列表请求
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param requestListener
	 * @param actionId
	 */
	public void post(Context context, String url, RequestParams params, RequestListener requestListener, int actionId) {
		asyncHttpClient.post(context, url, params, new HttpResponseHandler(requestListener, actionId));
	}

	/**
	 * JSON　参数请求
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param requestListener
	 * @param actionId
	 */
	public void post(Context context, String url, JSONObject params, RequestListener requestListener, int actionId) {
		asyncHttpClient.post(context, url, rpcToEntity(params.toString(), "application/json"), "application/json",
				new HttpResponseHandler(requestListener, actionId));
	}

	/**
	 * JSON　参数请求
	 * 
	 * @param context
	 * @param url
	 * @param headers
	 * @param params
	 * @param requestListener
	 * @param actionId
	 */
	public void post(Context context, String url, Header[] headers, JSONObject params, RequestListener requestListener,
			int actionId) {
		asyncHttpClient.post(context, url, headers, rpcToEntity(params.toString(), "application/json"),
				"application/json", new HttpResponseHandler(requestListener, actionId));
	}

	/**
	 * XML　参数请求
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param requestListener
	 * @param actionId
	 */
	public void post(Context context, String url, String params, RequestListener requestListener, int actionId) {
		asyncHttpClient.post(context, url, rpcToEntity(params, "application/xml"), "application/xml",
				new HttpResponseHandler(requestListener, actionId));
	}

	/**
	 * XML　参数请求
	 * 
	 * @param context
	 * @param url
	 * @param headers
	 * @param params
	 * @param requestListener
	 * @param actionId
	 */
	public void post(Context context, String url, Header[] headers, String params, RequestListener requestListener,
			int actionId) {
		asyncHttpClient.post(context, url, headers, rpcToEntity(params, "application/xml"), "application/xml",
				new HttpResponseHandler(requestListener, actionId));
	}

	/**
	 * 将JSON/XML字符串转为HttpEntity(StringEntity)
	 * 
	 * @param params
	 * @param contentType
	 * @return
	 */
	private static HttpEntity rpcToEntity(String params, String contentType) {
		StringEntity entity = null;
		if (!TextUtils.isEmpty(params)) {
			try {
				entity = new StringEntity(params, HTTP.UTF_8);
				entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, contentType));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}

	/**
	 * get数据
	 * 
	 * @param context
	 * @param url
	 * @param requestListener
	 * @param actionId
	 */
	public void get(Context context, String url, RequestListener requestListener, int actionId) {
		get(context, urlEncode(url), null, requestListener, false, actionId);
	}

	/**
	 * get数据
	 * 
	 * @param context
	 * @param url
	 * @param requestListener
	 * @param isCache
	 * @param actionId
	 */
	public synchronized void get(final Context context, final String url, final RequestParams params,
			final RequestListener requestListener, final boolean isCache, final int actionId) {
		if (!isCache) {
			asyncHttpClient.get(context, url, params, new HttpResponseHandler(requestListener, actionId));
		} else {
			if (!hasNetwork(context)) {
				new AsyncTask<Void, Void, byte[]>() {
					protected void onPreExecute() {
						requestListener.onStart();
					}

					@Override
					protected byte[] doInBackground(Void... params) {
						return loadCacheResource(context, url);
					}

					protected void onPostExecute(byte[] result) {
						boolean flag = (result != null);
						requestListener.onCompleted((flag ? RequestListener.OK : RequestListener.ERR), result, -1l,
								flag ? "load cache ok" : "load cache error", actionId);
					}
				}.execute();
			} else {
				loadAndSaveResource(context, url, requestListener, actionId);
			}
		}
	}

	/**
	 * 加载并缓存网络数据
	 * 
	 * @param context
	 * @param url
	 * @param requestListener
	 * @param actionId
	 */
	private void loadAndSaveResource(final Context context, final String url, final RequestListener requestListener,
			final int actionId) {
		asyncHttpClient.get(context, url, new HttpResponseHandler(context, url, requestListener, actionId));
	}

	/**
	 * 网络请求处理
	 */
	private class HttpResponseHandler extends AsyncHttpResponseHandler {
		private RequestListener requestListener;
		private int actionId;
		private Context context;
		private String url;

		public HttpResponseHandler(RequestListener requestListener, int actionId) {
			super();
			this.requestListener = requestListener;
			this.actionId = actionId;
		}

		public HttpResponseHandler(Context context, String url, RequestListener requestListener, int actionId) {
			super();
			this.context = context;
			this.url = url;
			this.requestListener = requestListener;
			this.actionId = actionId;
		}

		@Override
		public void onStart() {
			super.onStart();
			requestListener.onStart();
		}

		@Override
		protected void onSuccess(int intValue, Header[] headers, final byte[] response, final long lastModified) {
			super.onSuccess(intValue, headers, response, lastModified);
			requestListener.onCompleted(RequestListener.OK, response, lastModified, "server response ok", actionId);
			if (context != null && url != null && isLastModified(lastModified)) {
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						saveCacheResource(context, url, response, lastModified);
						return null;
					}
				}.execute();
			}
		}

		@Override
		protected void onFailure(Throwable throwable, String response) {
			requestListener.onCompleted(RequestListener.ERR, null, -1l, response, actionId);
		}

		@Override
		protected boolean isLastModified(long lastModified) {
			if (context != null && url != null) {// Only "GET" call
				if (!hasCache(context, url)) {
					return true;
				} else {
					final long ret = RequestChacheManager.getInstance(context).getLastModified(url);
					return ret != -1 && ret != lastModified;
				}
			} else {
				return super.isLastModified(lastModified);
			}
		}

		@Override
		protected byte[] loadCache() {
			return loadCacheResource(context, url);
		}
	}

	/**
	 * 读缓存
	 * 
	 * @param context
	 * @param url
	 */
	private byte[] loadCacheResource(Context context, final String url) {
		FileInputStream ins = null;
		try {
			ins = context.openFileInput(convertFilename(url));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] bytes = new byte[4096];
			int len = 0;
			while ((len = ins.read(bytes)) > 0) {
				bos.write(bytes, 0, len);
			}
			bos.flush();
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void saveCacheResource(Context context, String url, byte[] response, long lastModified) {
		ByteArrayInputStream ins = null;
		FileOutputStream os = null;
		try {
			ins = new ByteArrayInputStream(response);
			os = context.openFileOutput(convertFilename(url), Context.MODE_PRIVATE);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = ins.read(buffer)) > 0) {
				os.write(buffer, 0, len);
			}
			os.flush();

			RequestChacheManager.getInstance(context).update(url, lastModified);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 检测缓存
	 */
	private boolean hasCache(Context context, String url) {
		FileInputStream ins = null;
		try {
			ins = context.openFileInput(convertFilename(url));
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 检验网络是否有连接，有则true，无则false
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 对字符串进行MD5加密。
	 */
	private static String convertFilename(String strInput) {
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
		return buf.toString().substring(8, 24);
	}

	/**
	 * 网址汉字编码
	 */
	private static String urlEncode(String str) {
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

/**
 * RequestDBHelper
 */
class RequestDBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "requestCache.db";
	private static final int DB_VER = 1;
	private static final String TABLE_CREATE = "create table request_cache(url varchar(32) primary key,  lastmodified varchar(16))";

	public RequestDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VER);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}
}

/**
 * RequestChacheManager for "GET" method if isCahce
 * 
 * @author savant-pan
 * 
 */
class RequestChacheManager {
	private static RequestChacheManager INSTANCE = null;
	private RequestDBHelper requestDBHelper = null;

	private RequestChacheManager(Context context) {
		this.requestDBHelper = new RequestDBHelper(context);
	}

	/**
	 * get instance of RequestChacheManager
	 * 
	 * @param context
	 *            Context value
	 * @return
	 */
	public static RequestChacheManager getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new RequestChacheManager(context);
		}
		return INSTANCE;
	}

	/**
	 * update record: add or update
	 * 
	 * @param item
	 */
	public void update(String url, long lastModified) {
		SQLiteDatabase db = requestDBHelper.getWritableDatabase();
		if (!find(url)) { // add if not exist
			db.execSQL("insert into request_cache(url, lastmodified) values(?,?)",
					new Object[] { url, String.valueOf(lastModified) });
		} else { // update is exist
			db.execSQL("update request_cache set lastmodified=? where url=?",
					new Object[] { String.valueOf(lastModified), url });
		}
	}

	/**
	 * get lastmotified value by url
	 * 
	 * @param filename
	 * @return
	 */
	public long getLastModified(String url) {
		SQLiteDatabase db = requestDBHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			long ret = 0l;
			cursor = db.rawQuery("select * from request_cache where url=?", new String[] { url });
			if (cursor.moveToFirst()) {
				final String last = cursor.getString(cursor.getColumnIndex("lastmodified"));
				ret = Long.valueOf(last);
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * check exists of url
	 * 
	 * @param url
	 * @return
	 */
	private boolean find(String url) {
		SQLiteDatabase db = requestDBHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			boolean flag = false;
			cursor = db.rawQuery("select * from request_cache where url=?", new String[] { url });
			if (cursor.moveToFirst()) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * delete all records
	 */
	public void deleteAll() {
		List<String> all = getUrls();
		for (String url : all) {
			SQLiteDatabase database = requestDBHelper.getWritableDatabase();
			database.execSQL("delete from request_cache where url=?", new Object[] { url });
		}
	}

	/**
	 * get all urls in database
	 * 
	 * @return
	 */
	private List<String> getUrls() {
		List<String> ret = new ArrayList<String>();
		SQLiteDatabase db = requestDBHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("select * from request_cache", null);
			if (cursor.moveToFirst()) {
				do {
					final String url = cursor.getString(0);
					ret.add(url);
				} while (cursor.moveToNext());
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return ret;
	}
}

