package com.sample.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * ÓÃFragmentActivity´úÌæTabActivity
 * 
 * @author pxw
 * 
 */
public class MainActivity extends FragmentActivity {
	private final String[] titles = new String[] { "a", "b", "c", "d" };

	private final Integer[] icons = new Integer[] { R.drawable.app_host_img0_selector,
			R.drawable.app_host_img1_selector, R.drawable.app_host_img2_selector, R.drawable.app_host_img3_selector };

	private final Integer[] fragments = new Integer[] { R.id.fragment0, R.id.fragment1, R.id.fragment2, R.id.fragment3 };

	private TabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();

		for (int i = 0; i < titles.length; i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
			((ImageView) view.findViewById(R.id.tab_image)).setBackgroundResource(icons[i]);
			((TextView) view.findViewById(R.id.tab_text)).setText(titles[i]);
			tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(view).setContent(fragments[i]));
		}

		tabHost.setCurrentTab(0);
	}

}