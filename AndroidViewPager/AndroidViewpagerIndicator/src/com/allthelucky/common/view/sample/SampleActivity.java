package com.allthelucky.common.view.sample;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.allthelucky.common.view.R;
import com.allthelucky.common.view.ViewPagerIndicatorView;

/**
 * 使用示例
 * 
 * @author savant-pan
 * 
 */
public class SampleActivity extends Activity {
	private ViewPagerIndicatorView viewPagerIndicatorView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);
		
		//set ViewPagerIndicatorView
		this.viewPagerIndicatorView = (ViewPagerIndicatorView) findViewById(R.id.viewpager_indicator_view);
		final Map<String, View> map = new HashMap<String, View>();
		map.put("TAB1", LayoutInflater.from(this).inflate(R.layout.activity_sample_pager_0, null));
		map.put("TAB2", LayoutInflater.from(this).inflate(R.layout.activity_sample_pager_1, null));
		this.viewPagerIndicatorView.setupLayout(map);
	}

}
