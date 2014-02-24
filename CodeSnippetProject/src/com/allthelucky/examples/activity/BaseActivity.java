package com.allthelucky.examples.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

/**
 * Activity的基类，带一个ProgressDialog，可双击取消其显示
 * 
 * @author savant
 * 
 */
public class BaseActivity extends Activity {

    private long lastClickTime = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * show loading progress dialog
     */
    public void showDialog() {
        if (null == progressDialog) {
            progressDialog = ProgressDialog.show(BaseActivity.this, "", "正在加载，请稍候...");
            progressDialog.setCancelable(false);
        } else {
            progressDialog.show();
        }
        progressDialog.setOnKeyListener(onKeyListener);
    }

    /**
     * add a keylistener for progress dialog
     */
    private OnKeyListener onKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                long current = System.currentTimeMillis();
                if (current - lastClickTime > 1000) {
                    lastClickTime = current;
                } else {// double click invoke
                    dismissDialog(); // finish();
                }
            }
            return false;
        }
    };

    /**
     * dismiss dialog
     */
    public void dismissDialog() {
        if (isFinishing()) {
            return;
        }
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * show toast message
     * 
     * @param message
     */
    public void showToast(final String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * cancel progress dialog if nesseary
     */
    @Override
    public void onBackPressed() {
        if (progressDialog != null && progressDialog.isShowing()) {
            dismissDialog();
        } else {
            super.onBackPressed();
        }
    }
}
