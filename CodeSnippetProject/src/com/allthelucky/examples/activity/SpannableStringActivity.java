package com.allthelucky.examples.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

/**
 * SpannableString使用
 * 
 * @author pxw
 * 
 */
public class SpannableStringActivity extends Activity {
	private static final String note = "请致电客服4000-111-095，或重新操作。";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final TextView noteTv = new TextView(this);
		SpannableString sp = new SpannableString(note);
		sp.setSpan(new ClickableSpan() {
			@Override
			public void onClick(View widget) {
				Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4000111095"));
				startActivity(callIntent);
			}
		}, 45, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(0xffff8800), 6, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		noteTv.setText(sp);
		noteTv.setMovementMethod(LinkMovementMethod.getInstance());

		this.setContentView(noteTv);
	}
}
