package com.allthelucky.examples.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.allthelucky.examples.R;

public class ViewStubActivity extends Activity {
	private View view = null;
	private ViewStub vs = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewstub);

		vs = (ViewStub) findViewById(R.id.viewStub1);
		vs.setOnInflateListener(new ViewStub.OnInflateListener() {
			@Override
			public void onInflate(ViewStub arg0, View arg1) {
				view = arg1;
			}
		});

		findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (view == null) {// first execute
					vs.inflate();
				} else {//change Visibility between VISUAL and GONE
					view.setVisibility(view.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
				}
			}
		});
	}

}
