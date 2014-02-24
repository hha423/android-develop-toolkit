package com.allthelucky.examples.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allthelucky.examples.R;

/***
 * 可编辑文字及图片的EditText，TextView类似。(运用SpannableString合成图片文字混合String)
 */
public class EditImageActivity extends Activity implements OnClickListener {

	private EditText mEditText;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editimage_layout);

		mEditText = (EditText) findViewById(R.id.editText1);
		((Button) findViewById(R.id.btn_image)).setOnClickListener(this);
		((Button) findViewById(R.id.btn_text)).setOnClickListener(this);
		mTextView = ((TextView) findViewById(R.id.text_label));
	}

	@Override
	public void onClick(View v) {
		String text = mEditText.getText().toString();
		switch (v.getId()) {
		case R.id.btn_image:
			Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
			d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());

			ImageSpan is = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);

			SpannableString ss = new SpannableString(text);
			ss.setSpan(is, 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			mEditText.append(ss);
			break;
		case R.id.btn_text:

			break;
		}
		mTextView.setText(mEditText.getText());
	}
}
