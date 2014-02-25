package com.allthelucky.dianping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class BaseActivity extends Activity {
	private static final String EMPTY_TITLE = "";
	private static final String LADING_TEXT = "lading";
	private ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	protected void showToast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	protected void showDialog() {
		showDialog(true);
	}

	protected void showDialog(boolean cancel) {
		showDialog(LADING_TEXT, cancel);
	}

	protected void showDialog(String message, boolean cancel) {
		if (isFinishing()) {
			return;
		}
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this, EMPTY_TITLE, message);
			progressDialog.setCanceledOnTouchOutside(false);
		} else {
			progressDialog.setMessage(message);
			progressDialog.show();
		}
		progressDialog.setCancelable(cancel);
	}

	protected void updateMessage(String message) {
		if (isFinishing()) {
			return;
		}
		if (progressDialog != null) {
			progressDialog.setMessage(message);
		}
	}

	protected boolean isShowingDialog() {
		return (progressDialog != null && progressDialog.isShowing());
	}

	protected void dismissDialog() {
		if (isFinishing()) {
			return;
		}
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	@Override
	public void onBackPressed() {
		if (progressDialog != null && progressDialog.isShowing()) {
			dismissDialog();
		} else {
			super.onBackPressed();
		}
	}

}
