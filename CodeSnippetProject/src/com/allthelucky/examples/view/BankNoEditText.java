package com.allthelucky.examples.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 银行卡隔4位空格控件
 * 
 * @author savant-pan
 * 
 */
public class BankNoEditText extends EditText {
	private String result = "";

	public BankNoEditText(Context context) {
		super(context);
		init();
	}

	public BankNoEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 取控件银行卡号(无空格)
	 * 
	 * @return
	 */
	public String getBankNumber() {
		if (!TextUtils.isEmpty(result)) {
			return result.replaceAll(" ", "");
		}
		return result;
	}

	/**
	 * 将字符串转换成隔4空格形式
	 * 
	 * @param input
	 * @return
	 */
	private String parseBankNumber(String input) {
		if (TextUtils.isEmpty(input)) {
			return "";
		}
		char[] a = input.toCharArray();
		int len = a.length;

		final StringBuffer buffer = new StringBuffer();
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				if (i < 19) {
					buffer.append(a[i]);
					if (i > 0 && (i + 1) % 4 == 0) {
						buffer.append(" ");
					}
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * 设置输入状态更新监听
	 */
	private void init() {
		this.addTextChangedListener(new TextChangedListener() {
			boolean setting = false;
			int lastlen = 0;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (setting == true) {
					setting = false;
					return;
				}

				final String text = getText().toString();
				int end = getSelectionEnd();
				if (lastlen == (end + 1)) {// delete
					lastlen = end;
					return;
				}

				final String source = text.replaceAll(" ", "");

				setting = true;
				result = parseBankNumber(source);
				lastlen = result.length();

				setText(result);
				setSelection(lastlen);
			}
		});
	}
}
