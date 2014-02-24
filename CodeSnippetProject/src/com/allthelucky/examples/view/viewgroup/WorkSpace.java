package com.allthelucky.examples.view.viewgroup;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WorkSpace extends ViewGroup {
	private Context mContext;
	
	public WorkSpace(Context context) {
		super(context);
		mContext = context;
		init();
	}

	private void init() {
		setBackgroundColor(Color.WHITE);
		setAlwaysDrawnWithCacheEnabled(true);
        Button bt = new Button(mContext);
        bt.setText("hello");
        
        Button bts = new Button(mContext);
        bts.setText("hello");
        
		addView(bt);
		addView(bts);		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		for(int index = 0; index < count; index++) {
			final View child = getChildAt(0);
				LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				child.setLayoutParams(params);
				child.setVisibility(View.VISIBLE);
				child.measure(r-l, b-t+100*index); //call before layout
				child.layout(10, 10, child.getMeasuredWidth(), child.getMeasuredHeight());
		}
	}

}
