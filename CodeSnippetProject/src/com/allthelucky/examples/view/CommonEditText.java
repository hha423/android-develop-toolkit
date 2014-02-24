package com.allthelucky.examples.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allthelucky.examples.R;

/** 
 * @ClassName CommonEditText 
 * @Description 自定义属性
 * @author xuxiang
 * @date 2013-1-11
 */ 
public class CommonEditText extends LinearLayout{
	
	public CommonEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs){
		View parentView = LayoutInflater.from(context).inflate(R.layout.com_edittext_item, null);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		addView(parentView, params);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.com_edittext);
		String title = typedArray.getString(R.styleable.com_edittext_title);
		String hint = typedArray.getString(R.styleable.com_edittext_hint);
		TextView titleTv = (TextView)parentView.findViewById(R.id.title);
		EditText contentEt = (EditText)parentView.findViewById(R.id.content);
		
		typedArray.recycle();
		titleTv.setText(title);
		contentEt.setHint(hint);
		System.out.println();
	}

}
