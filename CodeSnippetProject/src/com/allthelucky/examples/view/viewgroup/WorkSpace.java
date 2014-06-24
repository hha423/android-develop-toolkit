package com.allthelucky.examples.view.viewgroup;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        TextView bt = new TextView(mContext);
        bt.setText("hello,world");
        
        TextView bts = new TextView(mContext);
        bts.setText("hello");
        
		addView(bt);
		addView(bts);		
	}

//	@Override
//	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		int count = getChildCount();
//		for(int index = 0; index < count; index++) {
//			final View child = getChildAt(0);
//				LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//				child.setLayoutParams(params);
//				child.setVisibility(View.VISIBLE);
//				child.measure(r-l, b-t+100*index); //call before layout
//				child.layout(10, 10, child.getMeasuredWidth(), child.getMeasuredHeight());
//		}
//	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {//vertical
			int childTop = 0;
			final int childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View childView = getChildAt(i);
				final int childHeight = childView.getMeasuredHeight();
				childView.layout(0, childTop, childView.getMeasuredWidth(), childTop+childHeight);
				childTop += childHeight;
			}
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int count = getChildCount();

		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

}
