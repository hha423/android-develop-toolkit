An improved Android Asynchronous HTTP Library.
-------------------

基于Android Asynchronous HTTP Library改进版网络加载框架：

1.增加GET方式下可缓存加载功能。

2.便于JSON-RPC,XML-RPC方式调用。

Android Asynchronous HTTP Library (https://github.com/loopj/android-async-http)


用法：
--------------------

    private static final int REQUEST_GET_ID = 0;
	private static final int REQUEST_POST_ID = 1;
	private static final int REQUEST_POST_JSON_ID = 2;
	private static final int REQUEST_POST_XML_ID = 3;

	public void sample(Context context) {
		get(context);
		postParams(context);
		postJSONObject(context);
		postXML(context);
	}

	/**
	 * get by url
	 */
	private void get(Context context) {
		RequestManager.getInstance().get(context, "http://test.com/api.php", requestListener, REQUEST_GET_ID);
	}

	/**
	 * post by RequestParams
	 */
	private void postParams(Context context) {
		final RequestParams params = new RequestParams();
		params.put("key1", "value1");
		params.put("key2", "value2");
		RequestManager.getInstance().post(context, "http://test.com/api.php", params, requestListener, REQUEST_POST_ID);
	}

	/**
	 * post by JSONObject
	 */
	private void postJSONObject(Context context) {
		final JSONObject json = new JSONObject();
		try {
			json.put("key1", "value1");
			json.put("key2", "value2");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		RequestManager.getInstance().post(context, "http://test.com/api.php", json, requestListener,
				REQUEST_POST_JSON_ID);
	}

	/**
	 * post by xml
	 */
	private void postXML(Context context) {
		final String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><key1>value1</key1><key2>value2</key2>";
		RequestManager.getInstance()
				.post(context, "http://test.com/api.php", xml, requestListener, REQUEST_POST_XML_ID);
	}

	/**
	 * request listener
	 */
	private RequestListener requestListener = new RequestListener() {
		@Override
		public void onStart() {
			// showDialog();
		}

		@Override
		public void onCompleted(int statusCode, byte[] data, long lastModified, String description, int actionId) {
			// dismissDialog();
			if (REQUEST_GET_ID == actionId) {
				if (RequestListener.OK == statusCode) {
					// sucess
				} else {
					// handler error case
				}
			} else if (REQUEST_POST_ID == actionId) {

			} else if (REQUEST_POST_JSON_ID == actionId) {

			} else if (REQUEST_POST_XML_ID == actionId) {

			}
		}
	};
