package com.allthelucky.dianping;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.widget.TextView;

import com.allthelucky.http.RequestListener;
import com.allthelucky.http.RequestManager;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final String apiUrl = "http://api.dianping.com/v1/business/find_businesses";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("city", "上海");
		paramMap.put("latitude", "31.21524");
		paramMap.put("longitude", "121.420033");
		paramMap.put("category", "美食");
		paramMap.put("region", "长宁区");
		paramMap.put("limit", "20");
		paramMap.put("radius", "2000");
		paramMap.put("offset_type", "0");
		paramMap.put("has_coupon", "1");
		paramMap.put("has_deal", "1");
		paramMap.put("keyword", "̩泰国菜");
		paramMap.put("sort", "7");

		final RequestListener requestListener = new RequestListener() {
			@Override
			public void onStart() {
				System.out.println("send..........");
				showDialog();
			}

			@Override
			public void onCompleted(int statusCode, byte[] data, long lastModified, String description, int actionId) {
				System.out.println("receive..........");
				dismissDialog();
				final TextView tv = (TextView) findViewById(R.id.hello_text);
				if (statusCode != RequestListener.OK) {
					tv.setText("result:" + description);
				} else {
					final JSONObject result = StringUtils.bytesToJSONObject(data);
					tv.setText("result:" + result);
				}
			
			}
		};
		RequestManager.getInstance().get(MainActivity.this, DianpingApiTool.requestApi(apiUrl, paramMap),requestListener, 0);

	}

}
