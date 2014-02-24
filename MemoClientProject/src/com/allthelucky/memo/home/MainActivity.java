package com.allthelucky.memo.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.allthelucky.http.RequestListener;
import com.allthelucky.http.RequestManager;
import com.allthelucky.memo.AppConfig;
import com.allthelucky.memo.R;
import com.allthelucky.memo.model.MemoInfo;
import com.allthelucky.memo.utils.AccountUtils;
import com.allthelucky.memo.utils.Utils;

public class MainActivity extends BaseActivity implements OnScrollListener {
	private static final int PAGE_SITE = 15;

	private int pageIndex = 0;
	private int pageCount = 0;
	private ListView listView;
	private ArrayAdapter<MemoInfo> adapter;
	private boolean isLoading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.listView = (ListView) findViewById(R.id.listView);

		this.adapter = new MemoArrayAdapter(this, new ArrayList<MemoInfo>());
		this.listView.setAdapter(adapter);
		this.listView.setOnScrollListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		pageIndex = 0;
		refreshByUser();
	}

	private void refreshByUser() {
		pageIndex++;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageIndex);
		map.put("size", PAGE_SITE);
		map.put("userid", AccountUtils.getUserId());
		JSONObject value = new JSONObject();
		try {
			value.put("value1", "value1");
			value.put("value12", "value12");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		map.put("test", value);
		RequestManager.getInstance().post(this, AppConfig.API_URL, Utils.build("get_memos", map), requestListener, 0);
	}

	private RequestListener requestListener = new RequestListener() {
		@Override
		public void onStart() {
			setLoading(true);
			showDialog();
		}

		@Override
		public void onCompleted(int statusCode, byte[] data, long lastModified, String description, int actionId) {
			dismissDialog();
			setLoading(false);
			if (data != null) {
				setupListView(actionId, Utils.bytesToJSONObject(data));
			} else {
				showToast("网络请求失败");
			}
		}
	};

	synchronized boolean isLoading() {
		return isLoading;
	}

	synchronized void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

	protected void setupListView(int actionId, JSONObject result) {
		if (result != null) {
			if (actionId == 0) {
				adapter.clear();
			}
			JSONObject data = result.optJSONObject("data");
			int count = 0;
			if (data != null && (count = data.optInt("count")) > 0) {
				pageCount = (count % PAGE_SITE == 0) ? count / PAGE_SITE : (count / PAGE_SITE) + 1;
				JSONArray array = result.optJSONObject("data").optJSONArray("list");
				if (array != null) {
					int len = array.length();
					if (len > 0) {
						for (int i = 0; i < len; i++) {
							JSONObject obj = array.optJSONObject(i);
							MemoInfo item = new MemoInfo();
							item.setId(obj.optInt("id"));
							item.setUserid(obj.optInt("userid"));
							item.setTitle(obj.optString("title"));
							item.setContent(obj.optString("content"));
							item.setTag(obj.optString("teg"));
							item.setCreatet(obj.optString("createt"));
							item.setUpdatet(obj.optString("updatet"));
							adapter.add(item);
						}
					}
				}
			}
			adapter.notifyDataSetChanged();
		} else {
			showToast("无相关数据");
		}
	}

	class MemoArrayAdapter extends ArrayAdapter<MemoInfo> {

		public MemoArrayAdapter(MainActivity context, ArrayList<MemoInfo> arrayList) {
			super(context, 0, arrayList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final View layout = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_item, null);
			final TextView titleView = (TextView) layout.findViewById(R.id.title);
			final TextView updateView = (TextView) layout.findViewById(R.id.update);
			final TextView contentView = (TextView) layout.findViewById(R.id.content);
			
			final MemoInfo memoInfo=	getItem(position);
			titleView.setText(memoInfo.getTitle());
			updateView.setText(memoInfo.getUpdatet());
			contentView.setText(memoInfo.getContent());
			return layout;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem > 0 && (visibleItemCount + firstVisibleItem) == totalItemCount) {
			if (!isLoading() && pageIndex < pageCount) {
				refreshByUser();
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

}
