package com.allthelucky.examples.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

/** 
 * @ClassName TextViewActivity 
 * @Description 文字添加链接
 * @author xuxiang
 * @date 2013-3-5
 */ 
public class TextViewActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		method();
	}
	
    private void method() {
    	TextView textView = new TextView(this);
        SpannableString sp = new SpannableString("如需缴话费，信用卡还款，酒店预订，机票预订，点击这里");
        sp.setSpan(new ClickableSpan() {// 添加点击事件
                    @Override
                    public void onClick(View widget) {
                    	System.out.print("您点击了链接文字");
                    }
                }, 22, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.rgb(25, 136, 221)), 22, 26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);// 设置颜色
        textView.setText(sp);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
